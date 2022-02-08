package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerTwoSend extends Thread{
    RoadLogic roadLogic;
    DataInputStream dis;
    DataOutputStream dos;
    String pName;
    int[] tmp;

    public PlayerTwoSend(RoadLogic RL, DataInputStream diStream, DataOutputStream doStream, String name){
        roadLogic = RL;
        dis = diStream;
        dos = doStream;
        pName = name;
    }

    private void send() throws IOException {
        tmp = roadLogic.player2.GiveChanges();
        for(int i = 0; i < 3;i++){
            dos.writeInt(tmp[i]);
        }
        dos.writeBoolean(roadLogic.player2.carWasSpammed);
        roadLogic.player2.carWasSpammed = false;
    }

    public void run(){
        RaceLayout RL = new RaceLayout();
        RL.StartLayout(2,roadLogic, pName);
        boolean isGo = true;
        while (isGo){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                send();
            } catch (IOException e) {
                isGo = false;
            }
        }
    }
}