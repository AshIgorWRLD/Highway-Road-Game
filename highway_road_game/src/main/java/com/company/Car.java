package com.company;

import java.awt.*;

public class Car {
    CarName.Names name;
    int height;
    int width;
    int box_to_box_width;
    Image image;
    Image image_central;
    Image image_up;
    Image image_down;
    int MAX_TOP;
    int MAX_BOTTOM;
    int MAX_SPEED;
    int MIN_SPEED;
    int MAX_X;
    int MIN_X;

    int x;
    int y;
    int speed;
    int acceleration;
    int way_length;
    int random_number_top;
    int random_number;
    int free_space_h;
    int priority;

    public Car(CarName.Names str, int h, int w, int btb_w, Image img_c, Image img_u, Image img_d,
               int max_top, int max_bot, int x_c, int y_c,int max_speed, int min_speed, int max_x,
               int min_x, int rand_num_top, int rand_num, int fsh){
        name = str;
        height = h;
        width = w;
        box_to_box_width = btb_w;
        image_central = img_c;
        image_up = img_u;
        image_down = img_d;
        image = image_central;
        MAX_TOP = max_top;
        MAX_BOTTOM = max_bot;
        speed = 0;
        acceleration = 0;
        way_length = 0;
        x = x_c;
        y = y_c;
        MAX_SPEED = max_speed;
        MIN_SPEED = min_speed;
        MIN_X = min_x;
        MAX_X = max_x;
        random_number_top = rand_num_top;
        random_number = rand_num;
        free_space_h = fsh;
        priority = 0;
    }
     public Integer getPriority(){
        return priority;
     }
}
