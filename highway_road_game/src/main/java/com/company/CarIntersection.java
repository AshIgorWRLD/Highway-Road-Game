package com.company;

import java.awt.*;

public class CarIntersection{
    public boolean CarIntersectionQ(Car car1, Car car2,boolean is_btb){

        float xmin1 = car1.x + ((float)car1.width)/40;
        float xmax1 = car1.x + car1.width;
        float ymin1 = car1.y + car1.free_space_h;
        float ymax1 = car1.y + car1.height - ((float)car1.height)/40;
        float xmin2 = car2.x + ((float)car2.width)/40;
        float xmax2 = car2.x + car2.width;
        float ymin2 = car2.y + car2.free_space_h;
        float ymax2 = car2.y + car2.height - ((float)car2.height)/40;
        if(is_btb){
            xmin1 = car1.x;
            xmin2 = car2.x;
            xmax1 = car1.x + car1.box_to_box_width;
            xmax2 = car2.x + car2.box_to_box_width;
            ymax1 = car1.y + car1.height;
            ymax2 = car2.y + car2.height;
        }
        boolean y_check = (ymin1<=ymin2) && (ymin2 <= ymax1) || (ymin1<=ymax2) && (ymax2 <= ymax1) ||
                (ymin2<=ymin1) && (ymin1 <= ymax2) || (ymin2<=ymax1) && (ymax1 <= ymax2);
        boolean x_check = (xmin1 <= xmin2) && (xmin2 <= xmax1) || (xmin1 <= xmax2) && (xmax2 <= xmax1) ||
                (xmin2 <= xmin1) && (xmin1 <= xmax2) || (xmin2 <= xmax1) && (xmax1 <= xmax2);

        return y_check && x_check;
    }

}
