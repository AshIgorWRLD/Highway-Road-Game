package server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

@Slf4j
public class Server extends Thread {
    private static final int BACKLOG_VALUE = 100;

    private final String ip;
    private final int port;

    private ServerExecutor myExecutor;

    public Server(String new_ip, int new_port) {
        this.ip = new_ip;
        this.port = new_port;
    }

    public void startServer() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            log.error("WRONG SERVER IP NAME", e);
        }
        try {
            ServerSocket serverSocket = new ServerSocket(port, BACKLOG_VALUE, inetAddress);
            myExecutor = new ServerExecutor(serverSocket);
            myExecutor.runExecutor();
            log.info("SERVER STARTED");
        } catch (IOException e) {
            log.error("CAN'T CREATE SERVER SOCKET");
        }
    }

    public void stopServer() {
        myExecutor.stop();
        log.info("SERVER DISABLED");
        System.exit(0);
    }
}
