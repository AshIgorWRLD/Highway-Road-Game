package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class PlayerOneTask implements Runnable {

    DataInputStream dis;
    DataOutputStream dos;
    ArrayList<Boolean> readyList;
    int ownNumber;
    int[] tmp = new int[6];

    public PlayerOneTask(DataInputStream diStream, DataOutputStream doStream, ArrayList<Boolean> rlst,int number){
        dis = diStream;
        dos = doStream;
        readyList = rlst;
        ownNumber = number;
    }

    private void sendReceive() throws IOException {
        boolean done = false;
        for(int i = 0; i < 6;i++){
            tmp[i] = dis.readInt();
            if(tmp[i] != 0) {
                done = true;
            }
        }
        if(done) {
            dos.writeBoolean(false);
            for (int i = 0; i < 6; i++) {
                dos.writeInt(tmp[i]);
            }
        }
    }

    public void run(){
        boolean isGo = true;
        while (isGo){
            try {
                sendReceive();
            }catch (IOException e){
                isGo = false;
            }
        }
        readyList.set(ownNumber, true);
        try {
            dos.writeBoolean(true);
        } catch (IOException e) {
        }
    }
}
