package com.company;

import java.net.Socket;

public class MySocket {
    int type;
    Socket socket;
    public MySocket(int t, Socket s){
        type = t;
        socket = s;
    }
}
