package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerTwoTask implements Runnable {
    DataInputStream dis;
    DataOutputStream dos;
    int[] tmp = new int[3];

    public PlayerTwoTask(DataInputStream diStream, DataOutputStream doStream){
        dis = diStream;
        dos = doStream;
    }
    private void sendReceive() throws IOException {
        boolean done = false;
        for (int i = 0; i < 3; i++){
            tmp[i] = dis.readInt();
            if(tmp[i] != 0){
                done = true;
            }
        }
        boolean spammed = dis.readBoolean();
        if(done) {
            dos.writeBoolean(false);
            for (int i = 0; i < 3; i++) {
                dos.writeInt(tmp[i]);
            }
            dos.writeBoolean(spammed);
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
        try {
            dos.writeBoolean(true);
        } catch (IOException e) {
        }
    }
}
