package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerOneReceive extends Thread{
    RoadLogic roadLogic;
    DataInputStream dis;
    DataOutputStream dos;
    int[] tmp = new int[3];

    public PlayerOneReceive(RoadLogic RL, DataInputStream diStream, DataOutputStream doStream){
        roadLogic = RL;
        dis = diStream;
        dos = doStream;
    }

    private void recieve() throws IOException {
        boolean isOver = dis.readBoolean();
        if(isOver){
            roadLogic.deactivateFabric = false;
        }
        for (int i = 0; i < 3; i++){
            tmp[i] = dis.readInt();
        }
        boolean spammed = dis.readBoolean();
        roadLogic.index = tmp[0];
        roadLogic.speed = tmp[1];
        roadLogic.enY = tmp[2];
        roadLogic.spammer = spammed;

    }
    public void run(){
        boolean isGo = true;
        while (isGo){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                recieve();
            } catch (IOException e) {
                roadLogic.deactivateFabric = false;
                roadLogic.isP2Active = false;
                isGo = false;
            }
        }
    }
}
