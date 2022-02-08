package com.company;

import java.awt.event.KeyEvent;

public class Player extends Car {

    public Player(Car car, String name) {
        super(car.name,car.height, car.width, car.box_to_box_width, car.image_central, car.image_up,
                car.image_down, car.MAX_TOP, car.MAX_BOTTOM, car.x, car.y, car.MAX_SPEED, car.MIN_SPEED,
                car.MAX_X, car.MIN_X, car.random_number_top, car.random_number, car.free_space_h);
        carName = name;
    }

    int KmRes = 0;
    int dx = 0;
    int dy = 0;
    int conditionIdx = 0;

    int layer1 = 0;
    int layer2 = 1200;
    String carName;

    public int[] GiveChanges(){
        int[] tmp = new int[6];
        tmp[0] = x;
        tmp[1] = y;
        tmp[2] = dx;
        tmp[3] = dy;
        tmp[4] = conditionIdx;
        tmp[5] = acceleration;
        return tmp;
    }

    public void move(){
        way_length += speed;
        speed += acceleration;
        if(speed <= MIN_SPEED){
            speed = MIN_SPEED;
        }
        if(speed >= MAX_SPEED){
            speed = MAX_SPEED;
        }
        if(speed > MIN_SPEED) {
            y += dy;
            if (y <= MAX_TOP) {
                y = MAX_TOP;
            }
            if (y >= MAX_BOTTOM) {
                y = MAX_BOTTOM;
            }
        }
        if(speed >= 40) {
            x += dx;
            if(dx < 0){
                speed -= 1;
            }
            if (x <= MIN_X) {
                x = MIN_X;
            }
            if (x >= MAX_X) {
                x = MAX_X;
            }
        }else{
            x -= 10;
            if (x <= MIN_X) {
                x = MIN_X;
            }
        }
        if(layer2-speed <= 0){
            layer1 = 0;
            layer2 = 1200;
            KmRes++;
        }
        layer1 -= speed;
        layer2 -= speed;
    }

    public void keyPressed(KeyEvent event){
        int key_code = event.getKeyCode();
        if(key_code == KeyEvent.VK_D){
            dx = +10;
        }
        if(key_code == KeyEvent.VK_A){
            dx = -10;
        }
        if(key_code == KeyEvent.VK_S){
            if(y < MAX_BOTTOM && speed != 0) {
                image = image_down;
                conditionIdx = 2;
            }
            if(speed < MAX_SPEED/4){
                dy = +5;
            }else if (speed < (MAX_SPEED/2+MAX_SPEED/4)){
                dy = +10;
            }else{
                dy = +15;
            }
        }
        if(key_code == KeyEvent.VK_W){
            if(y > MAX_TOP && speed != 0) {
                image = image_up;
                conditionIdx = 1;
            }
            if(speed < MAX_SPEED/4){
                dy = -5;
            }else if (speed < (MAX_SPEED/2+MAX_SPEED/4)){
                dy = -10;
            }else{
                dy = -15;
            }
        }
        if(key_code == KeyEvent.VK_SPACE){
            acceleration = 1;
        }
        if(key_code == KeyEvent.VK_P){
            acceleration = -1;
        }
    }
    public void keyReleased(KeyEvent event){
        int key_code = event.getKeyCode();
        if (key_code == KeyEvent.VK_D || key_code == KeyEvent.VK_A){
            dx = 0;
        }
        if((key_code == KeyEvent.VK_W) || (key_code == KeyEvent.VK_S)){
            image = image_central;
            conditionIdx = 0;
            dy = 0;
        }
        if(key_code == KeyEvent.VK_SPACE || key_code == KeyEvent.VK_P){
            acceleration = 0;
        }
    }
}
