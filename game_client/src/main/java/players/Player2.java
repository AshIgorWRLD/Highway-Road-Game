package players;

import cars.Car;
import cars.CarsDictionary;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@Getter
@Setter
public class Player2 {
    private static final int CHANGES_SIZE = 3;
    private static final int CAR_NAME_INDEX_POSITION_IN_CHANGES = 0;
    private static final int SPEED_VALUE_POSITION_IN_CHANGES = 1;
    private static final int ENEMY_Y_POSITION_IN_CHANGES = 2;
    private static final int TIMER_DELAY_AND_PERIOD_VALUE = 1000;
    private static final int MAX_CAR_IDX = 8;

    private static final int SPAWN_CAR_SPEED_VALUE = 20;

    private final Timer timer = new Timer();
    private final MyTask task = new MyTask();
    private final CarsDictionary carsDictionary;

    private String carName;
    private int carNameIdx;
    private int lineNumber;
    private boolean spamCar;
    private boolean carWasSpammed;
    private Car createdCar;
    private int speedValue;
    private int enemyY;
    private String playerCarName;

    public Player2(CarsDictionary carsDictionary, String playerCarName) {
        this.carsDictionary = carsDictionary;
        this.playerCarName = playerCarName;
        this.carName = "FORD_FOCUS";
        this.carNameIdx = 0;
        this.lineNumber = 1;
        this.spamCar = false;
        this.carWasSpammed = false;
        this.speedValue = 0;
        this.enemyY = 0;
    }

    public static class MyTask extends TimerTask implements Runnable {
        boolean isReady = true;

        public void run() {
            isReady = true;
        }
    }

    public void getCarNameByIdx() {
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

    public void increaseCarNameIdx() {
        carNameIdx++;
        if (carNameIdx > MAX_CAR_IDX) {
            carNameIdx = 0;
        }
        getCarNameByIdx();
    }

    public void decreaseCarNameIdx() {
        carNameIdx--;
        if (carNameIdx < 0) {
            carNameIdx = MAX_CAR_IDX;
        }
        getCarNameByIdx();
    }

    public void increaseLineNumber() {
        lineNumber++;
        if (lineNumber > 3) {
            lineNumber = 1;
        }
    }

    public void decreaseLineNumber() {
        lineNumber--;
        if (lineNumber < 1) {
            lineNumber = 3;
        }
    }

    public void pressKey(KeyEvent event) {
        keyPressed(event);
    }

    private void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.VK_D) {
            increaseCarNameIdx();
        }
        if (keyCode == KeyEvent.VK_A) {
            decreaseCarNameIdx();
        }
        if (keyCode == KeyEvent.VK_S) {
            increaseLineNumber();
        }
        if (keyCode == KeyEvent.VK_W) {
            decreaseLineNumber();
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            Random random = new Random();
            createdCar = carsDictionary.getCar(carsDictionary.
                    getEnemyCar(playerCarName, carNameIdx));
            speedValue = random.nextInt(SPAWN_CAR_SPEED_VALUE) + SPAWN_CAR_SPEED_VALUE;
            enemyY = carsDictionary.getEnemyY(createdCar, lineNumber);
            if (task.isReady) {
                spamCar = true;
                carWasSpammed = true;
                task.isReady = false;
                try {
                    timer.schedule(task, TIMER_DELAY_AND_PERIOD_VALUE, TIMER_DELAY_AND_PERIOD_VALUE);
                } catch (IllegalStateException ignored) {
                }
            }
        }
    }

    public int[] giveChanges() {
        int[] changes = new int[CHANGES_SIZE];
        changes[CAR_NAME_INDEX_POSITION_IN_CHANGES] = carNameIdx;
        changes[SPEED_VALUE_POSITION_IN_CHANGES] = speedValue;
        changes[ENEMY_Y_POSITION_IN_CHANGES] = enemyY;
        return changes;
    }
}
