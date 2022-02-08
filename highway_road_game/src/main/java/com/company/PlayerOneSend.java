package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerOneSend extends Thread{

    RoadLogic roadLogic;
    DataInputStream dis;
    DataOutputStream dos;
    String pName;
    int[] tmp;

    public PlayerOneSend(RoadLogic RL, DataInputStream diStream, DataOutputStream doStream, String name){
        roadLogic = RL;
        dis = diStream;
        dos = doStream;
        pName = name;
    }

    private void send() throws IOException {
        tmp = roadLogic.player.GiveChanges();
        for(int i = 0; i < 6;i++){
            dos.writeInt(tmp[i]);
        }

    }
    public void run(){
        boolean isGo = true;
        RaceLayout RL = new RaceLayout();
        RL.StartLayout(1, roadLogic, pName);
        while (isGo){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                send();
            }catch (IOException e){
                roadLogic.deactivateFabric = false;
                roadLogic.isP2Active = false;
                isGo = false;
            }
        }
    }
}