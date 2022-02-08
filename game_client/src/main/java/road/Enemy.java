package road;

import cars.Car;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class Enemy extends Car {
    private final RoadLogic road;

    private boolean isBlockedForward;
    private int forwardBlockFix;
    private int waitForBoostTime;
    private int maxWantedSpeed;

    public Enemy(Car car, int x, int y, int speed, RoadLogic road) {
        super(car.getName(), car.getHeight(), car.getWidth(), car.getBoxToBoxWidth(),
                car.getImageCentral(), car.getImageUp(), car.getImageDown(), car.getMaxTop(),
                car.getMaxBottom(), car.getX(), car.getY(), car.getMaxSpeed(), car.getMinSpeed(),
                car.getMaxX(), car.getMinX(), car.getRandomNumberTop(), car.getRandomNumber(),
                car.getFreeSpaceH());
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.road = road;
        this.isBlockedForward = false;
        this.forwardBlockFix = 0;
        this.waitForBoostTime = 200;
        this.maxWantedSpeed = (maxSpeed * 4) / 5;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public Rectangle getBoxToBoxRect() {
        return new Rectangle(x, y, boxToBoxWidth, height);
    }

    public void move() {
        x = x - road.getPlayerSpeed() + speed;
    }
}
