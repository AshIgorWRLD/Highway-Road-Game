package com.company;

import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Player2 {

    Timer timer = new Timer();
    MyTask task = new MyTask();
    String carName = "FORD_FOCUS";
    int carNameIdx = 0;
    int lineNumber = 1;
    boolean spamCar = false;
    boolean carWasSpammed = false;
    Car created_car;
    int speed_v = 0;
    int enemyY = 0;
    CarsDictionary CD;
    String playerCarName;

    Player2(CarsDictionary cd, String pcn) {
        CD = cd;
        playerCarName = pcn;
    }

    public static class MyTask extends TimerTask implements Runnable {
        boolean isReady = true;

        public void run() {
            isReady = true;
        }
    }

    public void getCNbyCNI() {
        switch (carNameIdx) {
            case 0 -> {
                carName = "SUBARU";
            }
            case 1 -> {
                carName = "KIA";
            }
            case 2 -> {
                carName = "LAND_CRUISER";
            }
            case 3 -> {
                carName = "BLACK_LAND_CRUISER";
            }
            case 4 -> {
                carName = "FORD_FOCUS";
            }
            case 5 -> {
                carName = "BENZ_TRUCK";
            }
            case 6 -> {
                carName = "BUS";
            }
            case 7 -> {
                carName = "ROOT_TAXI";
            }
            case 8 -> {
                carName = "TRUCK";
            }
        }
    }

    public void increaseCNI() {
        carNameIdx++;
        if (carNameIdx > 8) {
            carNameIdx = 0;
        }
        getCNbyCNI();
    }

    public void decreaseCNI() {
        carNameIdx--;
        if (carNameIdx < 0) {
            carNameIdx = 8;
        }
        getCNbyCNI();
    }

    public void increaseLN() {
        lineNumber++;
        if (lineNumber > 3) {
            lineNumber = 1;
        }
    }

    public void decreaseLN() {
        lineNumber--;
        if (lineNumber < 1) {
            lineNumber = 3;
        }
    }


    public void keyPressed(KeyEvent event) {
        int key_code = event.getKeyCode();
        if (key_code == KeyEvent.VK_D) {
            increaseCNI();
        }
        if (key_code == KeyEvent.VK_A) {
            decreaseCNI();
        }
        if (key_code == KeyEvent.VK_S) {
            increaseLN();
        }
        if (key_code == KeyEvent.VK_W) {
            decreaseLN();
        }
        if (key_code == KeyEvent.VK_SPACE) {
            Random random = new Random();
            created_car = CD.dictionary.get(CD.getEnemyCar(playerCarName, carNameIdx));
            speed_v = random.nextInt(20) + 20;
            enemyY = CD.GetEnemyY(created_car, lineNumber);
            if (task.isReady) {
                spamCar = true;
                carWasSpammed = true;
                task.isReady = false;
                try {
                    timer.schedule(task, 1000, 1000);
                } catch (IllegalStateException e) {

                }
            }
        }
    }


    public int[] GiveChanges() {
        int[] tmp = new int[3];
        tmp[0] = carNameIdx;
        tmp[1] = speed_v;
        tmp[2] = enemyY;
        return tmp;
    }
}
