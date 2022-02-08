package road;

import cars.Car;
import cars.CarIntersection;
import cars.CarsDictionary;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import players.Player;
import players.Player2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

@Getter
@Setter
@Slf4j
public class RoadLogic implements Runnable {
    private static final int NORMAL_SPEED = 30;
    private static final int FORWARD_BLOCK_FIX_VALUE = 40;
    private static final int CENTRAL_IMG_IDX = 0;
    private static final int UP_IMG_IDX = 1;
    private static final int DOWN_IMG_IDX = 2;
    private static final int TIME_BEFORE_BOOST = 100;
    private static final int LONG_TIME_BEFORE_BOOST = 300;
    private static final int MAX_X_COORDINATE_VALUE = 2400;
    private static final int ENEMY_SPAWN_X = 2000;
    private static final int MIN_SLEEP_VALUE = 1000;
    private static final int RANDOM_SLEEP_VALUE = 4000;
    private static final int AVERAGE_ENEMY_SPEED = 20;
    private static final int MIN_ENEMY_SPEED = 18;
    private static final int FIRST_LINE_IDX = 0;
    private static final int SPEED_ERROR_VALUE = 5;

    private final CarIntersection carIntersection;
    private final CarsDictionary cars;
    private final Thread enemiesFactory;
    private final ArrayList<Enemy> enemyList;

    private Player player;
    private Image playerImgCentral;
    private Image playerImgUp;
    private Image playerImgDown;
    private Player2 player2;
    private boolean isPlayer2Active;
    private boolean isFabricDeactivated;
    private int index;
    private int speed;
    private int enemySpawnY;
    private boolean isSpammerActive;
    private String carName;

    public RoadLogic(boolean isOnline, String carName) {
        this.carIntersection = new CarIntersection();
        this.cars = new CarsDictionary();
        this.enemiesFactory = new Thread(this);
        this.enemyList = new ArrayList<Enemy>();
        this.isPlayer2Active = false;
        this.isFabricDeactivated = false;
        this.index = 0;
        this.speed = 0;
        this.enemySpawnY = 0;
        this.isSpammerActive = false;
        this.carName = carName;
        this.enemiesFactory.start();
        if (isOnline) {
            isFabricDeactivated = true;
        }
    }

    public void setPlayerX(int value) {
        player.setX(value);
    }

    public void setPlayerY(int value) {
        player.setY(value);
    }

    public void setPlayerDX(int value) {
        player.setDX(value);
    }

    public void setPlayerDY(int value) {
        player.setDY(value);
    }

    public void setPlayerConditionIdx(int value) {
        player.setConditionIdx(value);
    }

    public void setPlayerAcceleration(int value) {
        player.setAcceleration(value);
    }

    public void setDeactivateFabric(boolean value) {
        isFabricDeactivated = value;
    }

    public int getPlayerConditionIdx() {
        return player.getConditionIdx();
    }

    public int[] getPlayerChanges() {
        return player.giveChanges();
    }

    public int getPlayerLayer1Position() {
        return player.getLayer1Position();
    }

    public int getPlayerLayer2Position() {
        return player.getLayer2Position();
    }

    public int getPlayerMaxSpeed() {
        return player.getMaxSpeed();
    }

    public int getPlayerSpeed() {
        return player.getSpeed();
    }

    public int getPlayerKmRes() {
        return player.getKmRes();
    }

    public int getPlayerPriority() {
        return player.getPriority();
    }

    public Image getPlayerImg() {
        return player.getImage();
    }

    public int getPlayerX() {
        return player.getX();
    }

    public int getPlayerY() {
        return player.getY();
    }

    public void pressPlayerKey(KeyEvent event) {
        player.pressKey(event);
    }

    public void releasePlayerKey(KeyEvent event) {
        player.releaseKey(event);
    }

    public void setPlayer2CarSpamInfo(boolean value) {
        player2.setCarWasSpammed(value);
    }

    public int getPlayer2LineNumber() {
        return player2.getLineNumber();
    }

    public String getPlayer2CarName() {
        return player2.getCarName();
    }

    public boolean getPlayer2CarSpamInfo() {
        return player2.isCarWasSpammed();
    }

    public int[] getPlayer2Changes() {
        return player2.giveChanges();
    }

    public void pressPlayer2Key(KeyEvent event) {
        player2.pressKey(event);
    }

    public void addPlayer() {
        Car tmp = cars.getCar(carName);
        player = new Player(tmp, carName);
        playerImgCentral = player.getImageCentral();
        playerImgUp = player.getImageUp();
        playerImgDown = player.getImageDown();
    }

    public void addPlayer2() {
        player2 = new Player2(cars, player.getCarName());
        isPlayer2Active = true;
    }

    public void changePlayerImg(int number) {
        switch (number) {
            case CENTRAL_IMG_IDX -> {
                player.setImage(playerImgCentral);
            }
            case UP_IMG_IDX -> {
                player.setImage(playerImgUp);
            }
            case DOWN_IMG_IDX -> {
                player.setImage(playerImgDown);
            }
        }
    }

    private void fixEnemyCollisions() {
        Random random = new Random();
        for (Enemy enemy : enemyList) {
            boolean check = true;
            for (Enemy tmpEnemy : enemyList) {
                if (enemy != tmpEnemy) {
                    if (carIntersection.carIntersectionQuestion(enemy, tmpEnemy, true)) {
                        if (enemy.getX() < tmpEnemy.getX()) {
                            if (enemy.getSpeed() - tmpEnemy.getSpeed() > 0) {
                                enemy.setSpeed(tmpEnemy.getSpeed() - 1);
                                if (enemy.getSpeed() < enemy.getMinSpeed()) {
                                    enemy.setSpeed(enemy.getMinSpeed());
                                }
                                enemy.setBlockedForward(true);
                                enemy.setForwardBlockFix(FORWARD_BLOCK_FIX_VALUE);
                            }
                        } else {
                            if (tmpEnemy.getSpeed() - enemy.getSpeed() > 0) {
                                tmpEnemy.setSpeed(enemy.getSpeed() - 1);
                                if (tmpEnemy.getSpeed() < tmpEnemy.getMinSpeed()) {
                                    tmpEnemy.setSpeed(tmpEnemy.getMinSpeed());
                                }
                                tmpEnemy.setBlockedForward(true);
                                tmpEnemy.setForwardBlockFix(FORWARD_BLOCK_FIX_VALUE);
                            }
                        }
                        check = false;
                    }
                }
            }


            if (carIntersection.carIntersectionQuestion(player, enemy, true)) {
                if (enemy.getX() < player.getX()) {
                    if (enemy.getSpeed() - player.getSpeed() > 0) {
                        if ((enemy.getSpeed() / 6) < player.getSpeed() ||
                                enemy.getSpeed() < MIN_ENEMY_SPEED) {
                            enemy.setSpeed(player.getSpeed() - 1);
                        }
                        if (enemy.getSpeed() < enemy.getMinSpeed()) {
                            enemy.setSpeed(enemy.getMinSpeed());
                        }
                        enemy.setBlockedForward(true);
                        enemy.setForwardBlockFix(FORWARD_BLOCK_FIX_VALUE);
                    }
                    check = false;
                }
            }

            if (check) {
                if (enemy.getForwardBlockFix() > 0 && enemy.isBlockedForward()) {
                    enemy.setForwardBlockFix(enemy.getForwardBlockFix() - 1);
                    enemy.setWaitForBoostTime(TIME_BEFORE_BOOST);
                } else {
                    enemy.setBlockedForward(false);
                    if (enemy.getSpeed() < SPEED_ERROR_VALUE) {
                        enemy.setSpeed(NORMAL_SPEED + random.nextInt(SPEED_ERROR_VALUE));
                    } else {
                        if (enemy.getWaitForBoostTime() > 0) {
                            enemy.setWaitForBoostTime(enemy.getWaitForBoostTime() - 1);
                        }
                        if (enemy.getWaitForBoostTime() == 0) {
                            enemy.setSpeed(enemy.getSpeed() + 1);
                            if (enemy.getSpeed() > enemy.getMaxWantedSpeed()) {
                                enemy.setSpeed(enemy.getMaxWantedSpeed());
                            }
                            enemy.setWaitForBoostTime(LONG_TIME_BEFORE_BOOST);
                        }
                    }
                }
            }
        }

    }

    public void takeRoadChanges() {
        getPriority(player);
        Iterator<Enemy> iterator = enemyList.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (enemy.getX() >= MAX_X_COORDINATE_VALUE || enemy.getX() <= -MAX_X_COORDINATE_VALUE) {
                iterator.remove();
            } else {
                getPriority(enemy);
            }
        }
    }

    private void moveEnemies() {
        for (Enemy enemy : enemyList) {
            enemy.move();
        }
    }


    public boolean testCollisionsWithEnemies() {
        for (Enemy enemy : enemyList) {
            if (carIntersection.carIntersectionQuestion(player, enemy, false)) {
                return true;
            }
        }
        return false;
    }


    private void getPriority(Car car) {
        car.setPriority(car.getY() + car.getHeight());
    }

    public void sortEnemies() {
        enemyList.sort(new PriorityComparator());
    }

    public void roadIteration() {
        player.move();
        moveEnemies();
        fixEnemyCollisions();
    }

    private void addEnemy() {
        if (isSpammerActive) {
            Car enemyCar = cars.getCar(cars.getEnemyCar(player.getCarName(), index));
            enemyList.add(new Enemy(enemyCar, ENEMY_SPAWN_X, enemySpawnY, speed, this));
            isSpammerActive = false;
        }
    }

    public void run() {
        while (true) {
            Random random = new Random();
            try {
                if (isPlayer2Active) {
                    if (player2.isSpamCar()) {
                        enemyList.add(new Enemy(player2.getCreatedCar(), ENEMY_SPAWN_X,
                                player2.getEnemyY(), player2.getSpeedValue(), this));

                        player2.setSpamCar(false);
                    }
                } else {
                    if (!isFabricDeactivated) {
                        Thread.sleep(random.nextInt(RANDOM_SLEEP_VALUE) + MIN_SLEEP_VALUE);
                        Car enemyCar = cars.getCar(cars.getEnemyCar(player.getCarName(), -1));
                        enemyList.add(new Enemy(enemyCar, ENEMY_SPAWN_X,
                                cars.getEnemyY(enemyCar, FIRST_LINE_IDX),
                                random.nextInt(AVERAGE_ENEMY_SPEED) + AVERAGE_ENEMY_SPEED,
                                this));
                    } else {
                        addEnemy();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}