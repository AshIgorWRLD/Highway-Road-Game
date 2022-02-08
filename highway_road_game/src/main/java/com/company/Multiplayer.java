package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Multiplayer extends Thread{
    private String ip = "localhost";
    private int port = 8083;
    private Socket socket;
    private ServerSocket serverSocket;
    private RoadLogic roadLogic = new RoadLogic(true);
    MyExecutor myExecutor;
    DataInputStream dis;
    DataOutputStream dos;
    PlayerOneSend playerOneSend;
    PlayerTwoSend playerTwoSend;
    PlayerOneReceive playerOneReceive;
    PlayerTwoReceive playerTwoReceive;

    public void StartServer(String new_ip, int new_port){
        ip = new_ip;
        port = new_port;
        try{
            serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
            myExecutor = new MyExecutor(serverSocket);
            myExecutor.runExecutor();
            System.out.println("Server started");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void StopServer(){
        myExecutor.Stop();
        System.out.println("Server is inactive now");
        System.exit(1);
    }
    public void connectSpamer(String new_ip, int new_port, String name){
        ip = new_ip;
        port = new_port;
        try{
            boolean isReady = false;
            socket=new Socket(ip,port);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            dos.writeInt(2);
            while(!isReady){
                boolean tmp = dis.readBoolean();
                if(tmp){
                    isReady = tmp;
                }
            }
            playerTwoSend = new PlayerTwoSend(roadLogic,dis,dos,name);
            playerTwoSend.start();
            playerTwoReceive = new PlayerTwoReceive(roadLogic,dis,dos);
            playerTwoReceive.start();
            System.out.println("Successfully connected to the server");
        } catch (IOException e) {
            System.out.println("Unable to connect to the server");
        }
    }

    public void connectDriver(String new_ip, int new_port, String name){
        ip = new_ip;
        port = new_port;
        try{
            boolean isReady = false;
            socket = new Socket(ip,port);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            dos.writeInt(1);
            while(!isReady){
                boolean tmp = dis.readBoolean();
                if(tmp){
                    isReady = tmp;
                }
            }
            playerOneSend = new PlayerOneSend(roadLogic,dis,dos,name);
            playerOneSend.start();
            playerOneReceive = new PlayerOneReceive(roadLogic,dis,dos);
            playerOneReceive.start();
            System.out.println("Server started");
            System.out.println("Successfully connected to the server");
        } catch (IOException e) {
            System.out.println("Unable to connect to the server");
        }
    }

}
