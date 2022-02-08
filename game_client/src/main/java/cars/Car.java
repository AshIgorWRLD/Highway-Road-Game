package cars;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class Car {
    protected CarName.Names name;
    protected int height;
    protected int width;
    protected int boxToBoxWidth;
    protected Image image;
    protected Image imageCentral;
    protected Image imageUp;
    protected Image imageDown;
    protected int maxTop;
    protected int maxBottom;
    protected int maxSpeed;
    protected int minSpeed;
    protected int maxX;
    protected int minX;

    protected int x;
    protected int y;
    protected int speed;
    protected int acceleration;
    protected int wayLength;
    protected int randomNumberTop;
    protected int randomNumber;
    protected int freeSpaceH;
    protected int priority;

    public Car(CarName.Names name, int height, int width, int boxToBoxWidth, Image imageCentral,
               Image imageUp, Image imageDown, int maxTop, int maxBottom, int x, int y, int maxSpeed,
               int minSpeed, int maxX, int minX, int randomNumberTop, int randomNumber, int freeSpaceH) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.boxToBoxWidth = boxToBoxWidth;
        this.imageCentral = imageCentral;
        this.imageUp = imageUp;
        this.imageDown = imageDown;
        this.image = this.imageCentral;
        this.maxTop = maxTop;
        this.maxBottom = maxBottom;
        this.speed = 0;
        this.acceleration = 0;
        this.wayLength = 0;
        this.x = x;
        this.y = y;
        this.maxSpeed = maxSpeed;
        this.minSpeed = minSpeed;
        this.minX = minX;
        this.maxX = maxX;
        this.randomNumberTop = randomNumberTop;
        this.randomNumber = randomNumber;
        this.freeSpaceH = freeSpaceH;
        this.priority = 0;
    }

    public Integer getPriority() {
        return priority;
    }
}
