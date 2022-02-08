package server;

import lombok.extern.slf4j.Slf4j;
import server.MySocket;
import tasks.PlayerOneTask;
import tasks.PlayerTwoTask;
import threadpool.MyThreadPool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

@Slf4j
public class ServerExecutor {
    private static final String MAX_THREADS_NUMBER_PROPERTY_KEY = "MAX_THREADS_NUMBER";
    private static final int UNKNOWN = 0;
    private static final int DRIVER = 1;
    private static final int SPAMMER = 2;
    private static final int MAX_QUEUED_PLAYERS = 25;

    private final MyThreadPool threadPool;
    private final ServerSocket serverSocket;
    private final ArrayList<MySocket> sockets;
    private final ArrayList<DataInputStream> dataInputStreamList;
    private final ArrayList<DataOutputStream> dataOutputStreamList;
    private final Queue<Integer> pOneQueue;
    private final Queue<Integer> pTwoQueue;
    private final ArrayList<Boolean> readyList;
    //private final ArrayList<Integer> POneNumbers;
    //private final ArrayList<Integer> PTwoNumbers;

    private int connectedPlayers;
    private int connectedPOnes;
    private int connectedPTwos;
    private int queuedPOnes;
    private int queuedPTwos;

    public ServerExecutor(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;

        connectedPlayers = 0;
        connectedPOnes = 0;
        connectedPTwos = 0;
        queuedPOnes = 0;
        queuedPTwos = 0;

        sockets = new ArrayList<MySocket>();
        dataInputStreamList = new ArrayList<DataInputStream>();
        dataOutputStreamList = new ArrayList<DataOutputStream>();
        //POneNumbers = new ArrayList<Integer>();
        //PTwoNumbers = new ArrayList<Integer>();
        pOneQueue = new LinkedList<Integer>();
        pTwoQueue = new LinkedList<Integer>();
        readyList = new ArrayList<Boolean>();

        int maxNumberOfThreads = getMaxNumberOfThreads();

        threadPool = new MyThreadPool(2, maxNumberOfThreads);

        if (maxNumberOfThreads < 2) {
            log.error("CHECK YOUR PROPERTIES FILE, YOU NEED AT LEAST 2 ACTIVE THREADS");
            System.exit(0);
        }
    }

    private int getMaxNumberOfThreads() {
        InputStream fis = null;
        Properties properties;
        try {
            properties = new Properties();
            fis = new FileInputStream("src\\main\\resources\\server.properties");
            properties.load(fis);
            Set<Object> keys = properties.keySet();
            return Integer.parseInt(properties.getProperty(MAX_THREADS_NUMBER_PROPERTY_KEY));
        } catch (FileNotFoundException e) {
            log.error("CAN'T FIND SERVER PROPERTIES FILE");
        } catch (IOException e) {
            log.error("CAN'T LOAD INFO FROM FILE INPUT STREAM");
        }
        return 0;
    }

    private void whichIsReady() {
        Iterator<Boolean> iterator = readyList.iterator();
        while (iterator.hasNext()) {
            Boolean value = iterator.next();
            if (value) {
                iterator.remove();
                return;
            }
        }
    }

    public void runExecutor() {
        RequestManager requestManager = new RequestManager(serverSocket);
        requestManager.start();
        TaskManager taskManager = new TaskManager();
        taskManager.start();
    }

    public void stop() {
        threadPool.stop();
    }

    private class RequestManager extends Thread {
        ServerSocket serverSocket;

        private RequestManager(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        public void tryToConnect() {
            try {
                if (connectedPOnes == MAX_QUEUED_PLAYERS) {
                    whichIsReady();
                    connectedPOnes--;
                }
                Socket tmpSocket = serverSocket.accept();
                sockets.add(new MySocket(UNKNOWN, tmpSocket));
                DataInputStream disTmp = new DataInputStream(sockets.get(connectedPlayers)
                        .getInputStream());
                DataOutputStream dosTmp = new DataOutputStream(sockets.get(connectedPlayers)
                        .getOutputStream());
                int isDriver = disTmp.readInt();
                boolean isAdded = false;
                if (isDriver == DRIVER) {
                    sockets.set(connectedPlayers, new MySocket(DRIVER, sockets.get(connectedPlayers)
                            .getSocket()));
                    //POneNumbers.add(connectedPlayers);
                    pOneQueue.add(connectedPlayers);
                    readyList.add(false);
                    connectedPOnes++;
                    queuedPOnes++;
                    isAdded = true;
                    log.info("PLAYER 1 CONNECTED");
                } else if (isDriver == SPAMMER) {
                    sockets.set(connectedPlayers, new MySocket(SPAMMER, sockets.get(connectedPlayers)
                            .getSocket()));
                    //PTwoNumbers.add(connectedPlayers);
                    pTwoQueue.add(connectedPlayers);
                    connectedPTwos++;
                    queuedPTwos++;
                    isAdded = true;
                    log.info("PLAYER 2 CONNECTED");
                }
                if (isAdded) {
                    dataInputStreamList.add(disTmp);
                    dataOutputStreamList.add(dosTmp);
                    connectedPlayers++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            while (true) {
                tryToConnect();
            }
        }
    }

    private class TaskManager extends Thread {

        public void run() {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    log.error("UNABLE TO SLEEP THREAD");
                }
                if (queuedPOnes > 0 && queuedPTwos > 0) {
                    queuedPOnes--;
                    queuedPTwos--;
                    int pOneIdx = pOneQueue.remove();
                    int pTwoIdx = pTwoQueue.remove();
                    PlayerOneTask playerOneTask = new PlayerOneTask(dataInputStreamList.get(pOneIdx),
                            dataOutputStreamList.get(pTwoIdx), readyList, pOneIdx);
                    PlayerTwoTask playerTwoTask = new PlayerTwoTask(dataInputStreamList.get(pTwoIdx),
                            dataOutputStreamList.get(pOneIdx));
                    try {
                        threadPool.execute(playerOneTask);
                        threadPool.execute(playerTwoTask);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        dataOutputStreamList.get(pOneIdx).writeBoolean(true);
                        dataOutputStreamList.get(pTwoIdx).writeBoolean(true);
                    } catch (IOException ignored) {
                    }
                }
            }
        }
    }
}
