package com.company;

import java.awt.*;

public class Enemy extends Car{
    public boolean forward_block;
    public int forward_block_fixer;
    public int wait_for_boost_time;
    public int MAX_WANTED_SPEED;

    public Enemy(Car car, int x_coord, int y_coord, int speed_value, RoadLogic r) {
        super(car.name,car.height, car.width, car.box_to_box_width, car.image_central, car.image_up,
                car.image_down, car.MAX_TOP, car.MAX_BOTTOM, car.x, car.y, car.MAX_SPEED, car.MIN_SPEED,
                car.MAX_X, car.MIN_X, car.random_number_top, car.random_number, car.free_space_h);
        x = x_coord;
        y = y_coord;
        speed = speed_value;
        road = r;
        forward_block = false;
        forward_block_fixer = 0;
        wait_for_boost_time = 200;
        MAX_WANTED_SPEED = (MAX_SPEED*4)/5;
    }

    RoadLogic road;

    public Rectangle getRect(){
        return new Rectangle(x, y, width, height);
    }
    public Rectangle getBTBRect(){
        return new Rectangle(x, y, box_to_box_width, height);
    }

    public void move(){
        x = x - road.player.speed + speed;
    }
}
