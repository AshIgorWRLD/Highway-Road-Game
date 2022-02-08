package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class RoadLogic implements Runnable {
    CarIntersection CI = new CarIntersection();
    public final int NORMAL_SPEED = 30;
    CarsDictionary cars = new CarsDictionary();
    Thread EnemiesFactory = new Thread(this);
    Player player;
    Image pCentral;
    Image pUp;
    Image pDown;
    Player2 player2;
    boolean isP2Active = false;
    boolean deactivateFabric = false;
    int index = 0;
    int speed = 0;
    int enY = 0;
    boolean spammer = false;

    ArrayList<Enemy> enemyList = new ArrayList<Enemy>();



    public void AddPlayer(String carName){
        Car tmp = cars.dictionary.get(carName);
        player = new Player(tmp, carName);
        pCentral = player.image_central;
        pUp = player.image_up;
        pDown = player.image_down;
    }
    public void AddPlayer2(){
        player2 = new Player2(cars,player.carName);
        isP2Active = true;
    }
    public RoadLogic(boolean isOnline){
        AddPlayer("SUBARU");
        EnemiesFactory.start();
        if(isOnline){
            deactivateFabric = true;
        }
    }

    public void changePlayerImg(int number){
        switch (number){
            case 0->{
                player.image = pCentral;
            }
            case 1->{
                player.image = pUp;
            }
            case 2-> {
                player.image = pDown;
            }
        }
    }

    public void FixEnemyCollisions(){
        Random random = new Random();
        for (Enemy enemy:enemyList){
            boolean check = true;
            for(Enemy enemy1:enemyList){
                if(enemy != enemy1){
                    if(CI.CarIntersectionQ(enemy,enemy1,true)){
                        if(enemy.x < enemy1.x){
                            if(enemy.speed - enemy1.speed > 0){
                                enemy.speed = enemy1.speed-1;
                                if(enemy.speed < enemy.MIN_SPEED){
                                    enemy.speed = enemy.MIN_SPEED;
                                }
                                enemy.forward_block = true;
                                enemy.forward_block_fixer = 40;
                            }
                        }else{
                            if(enemy1.speed - enemy.speed > 0){
                                enemy1.speed = enemy.speed-1;
                                if(enemy1.speed < enemy1.MIN_SPEED){
                                    enemy1.speed = enemy1.MIN_SPEED;
                                }
                                enemy1.forward_block  = true;
                                enemy1.forward_block_fixer = 40;
                            }
                        }
                        check = false;
                    }
                }
            }


            if(CI.CarIntersectionQ(player,enemy,true)){
                if(enemy.x < player.x){
                    if(enemy.speed - player.speed > 0){
                        if((enemy.speed/6) < player.speed || enemy.speed < 18){
                            enemy.speed = player.speed - 1;
                        }
                        if(enemy.speed < enemy.MIN_SPEED){
                            enemy.speed = enemy.MIN_SPEED;
                        }
                        enemy.forward_block = true;
                        enemy.forward_block_fixer = 40;
                    }
                    check = false;
                }
            }

            if(check) {
                if (enemy.forward_block_fixer > 0 && enemy.forward_block) {
                    enemy.forward_block_fixer--;
                    enemy.wait_for_boost_time = 100;
                } else {
                    enemy.forward_block = false;
                    if (enemy.speed < 5) {
                        enemy.speed = NORMAL_SPEED + random.nextInt(5);
                    } else {
                        if (enemy.wait_for_boost_time > 0) {
                            enemy.wait_for_boost_time--;
                        }
                        if (enemy.wait_for_boost_time == 0) {
                            enemy.speed += 1;
                            if (enemy.speed > enemy.MAX_WANTED_SPEED) {
                                enemy.speed = enemy.MAX_WANTED_SPEED;
                            }
                            enemy.wait_for_boost_time = 300;
                        }
                    }
                }
            }
        }

    }

    public void TmpRoadChanges(){

        GetPriority(player);
        Iterator<Enemy> iterator = enemyList.iterator();
        while (iterator.hasNext()){
            Enemy enemy = iterator.next();
            if(enemy.x >= 2400 || enemy.x <= -2400){
                iterator.remove();
            }else {
                GetPriority(enemy);
            }
        }
    }

    public void MoveEnemies(){
        for (Enemy enemy : enemyList) {
            enemy.move();
        }
    }


    public boolean TestCollisionsWithEnemies(){
        Iterator<Enemy> iterator = enemyList.iterator();
        while (iterator.hasNext()){
            Enemy enemy = iterator.next();
            if (CI.CarIntersectionQ(player, enemy, false)) {
                return true;
            }
        }
        return false;
    }


    public void GetPriority(Car car){
        car.priority = car.y + car.height;
    }

    public void SortEnemies(){
        Collections.sort(enemyList,new PriorityComparator());
    }

    public void RoadLive(){
        player.move();
        MoveEnemies();
        FixEnemyCollisions();
    }

    public void addEnemy(){
        if(spammer) {
            Car enemy_car = cars.dictionary.get(cars.getEnemyCar(player.carName, index));
            enemyList.add(new Enemy(enemy_car, 2000, enY, speed, this));
            spammer = false;
        }
    }

    public void run(){
        while (true){
            Random random = new Random();
            try {
                if(isP2Active){
                    if(player2.spamCar) {
                        enemyList.add(new Enemy(player2.created_car, 2000,
                                player2.enemyY, player2.speed_v, this));
                        player2.spamCar = false;
                    }
                }else {
                    if(!deactivateFabric) {
                        Thread.sleep(random.nextInt(4000) + 1000);
                        Car enemy_car = cars.dictionary.get(cars.getEnemyCar(player.carName, -1));
                        System.out.println(cars.GetEnemyY(enemy_car, 0));
                        enemyList.add(new Enemy(enemy_car, 2000, cars.GetEnemyY(enemy_car, 0),
                                random.nextInt(20) + 20, this));
                    }else{
                        addEnemy();
                    }
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}