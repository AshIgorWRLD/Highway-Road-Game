package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

public class MyExecutor {
    MyThreadPool threadPool = new MyThreadPool(2, 10);
    private static final int UNKNOWN = 0;
    private static final int DRIVER = 1;
    private static final int SPAMER = 2;

    ServerSocket serverSocket;
    ArrayList<MySocket> sockets = new ArrayList<MySocket>();
    ArrayList<DataInputStream> disS = new ArrayList<DataInputStream>();
    ArrayList<DataOutputStream> dosS = new ArrayList<DataOutputStream>();
    int connectedPlayers = 0;
    ArrayList<Integer> POneNumbers = new ArrayList<Integer>();
    ArrayList<Integer> PTwoNumbers = new ArrayList<Integer>();
    int connectedPOnes = 0;
    int connectedPTwos = 0;
    Queue<Integer> POneQueue = new LinkedList<Integer>();
    Queue<Integer> PTwoQueue = new LinkedList<Integer>();
    ArrayList<Boolean> readyList = new ArrayList<Boolean>();
    int queuedPOnes = 0;
    int queuedPTwos = 0;
    public void WhichIsReady(){
        Iterator<Boolean> iterator = readyList.iterator();
        while(iterator.hasNext()){
            Boolean value = iterator.next();
            if(value){
                iterator.remove();
                return;
            }
        }
    }

    public MyExecutor(ServerSocket SS) throws InterruptedException {
        serverSocket = SS;
    }
    public void runExecutor() throws IOException, ExecutionException, InterruptedException {

        RequestManager requestManager = new RequestManager(serverSocket);
        requestManager.start();
        TaskManager taskManager = new TaskManager();
        taskManager.start();

    }

    public void Stop(){
        threadPool.stop();
    }
    private class RequestManager extends Thread{
        ServerSocket serverSocket;
        private RequestManager(ServerSocket sS){
            serverSocket = sS;
        }

        public void trying(){
            try {
                if(connectedPOnes == 25){
                    WhichIsReady();
                    connectedPOnes--;
                }
                Socket tmpSocket = serverSocket.accept();
                sockets.add(new MySocket(UNKNOWN,tmpSocket));
                DataInputStream disTmp = new DataInputStream(sockets.get(connectedPlayers).socket.getInputStream());
                DataOutputStream dosTmp = new DataOutputStream(sockets.get(connectedPlayers).socket.getOutputStream());
                int isDriver = disTmp.readInt();
                boolean isAdded = false;
                if(isDriver == 1){
                    System.out.println("1 connected");
                    sockets.set(connectedPlayers,new MySocket(DRIVER,sockets.get(connectedPlayers).socket));
                    POneNumbers.add(connectedPlayers);
                    POneQueue.add(connectedPlayers);
                    readyList.add(false);
                    connectedPOnes++;
                    queuedPOnes++;
                    isAdded = true;
                }else if (isDriver == 2){
                    System.out.println("2 connected");
                    sockets.set(connectedPlayers,new MySocket(SPAMER,sockets.get(connectedPlayers).socket));
                    PTwoNumbers.add(connectedPlayers);
                    PTwoQueue.add(connectedPlayers);
                    connectedPTwos++;
                    queuedPTwos++;
                    isAdded = true;
                }
                if(isAdded) {
                    disS.add(disTmp);
                    dosS.add(dosTmp);
                    connectedPlayers++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void run(){
            while (true){
                trying();
            }
        }
    }

    private class TaskManager extends Thread{
        public void run(){
            while(true){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(queuedPOnes > 0 && queuedPTwos > 0) {
                    queuedPOnes--;
                    queuedPTwos--;
                    int pOneIdx = POneQueue.remove();
                    int pTwoIdx = PTwoQueue.remove();
                    PlayerOneTask playerOneTask = new PlayerOneTask(disS.get(pOneIdx),
                            dosS.get(pTwoIdx), readyList, pOneIdx);
                    PlayerTwoTask playerTwoTask = new PlayerTwoTask(disS.get(pTwoIdx),
                            dosS.get(pOneIdx));
                    try {
                        threadPool.execute(playerOneTask);
                        threadPool.execute(playerTwoTask);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        dosS.get(pOneIdx).writeBoolean(true);
                        dosS.get(pTwoIdx).writeBoolean(true);
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

}
