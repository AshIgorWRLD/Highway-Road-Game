package players;

import cars.Car;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.KeyEvent;

@Getter
@Setter
public class Player extends Car {
    private static final int SHARING_INFO_SIZE = 6;
    private static final int X_POSITION_IN_MESSAGE = 0;
    private static final int Y_POSITION_IN_MESSAGE = 1;
    private static final int D_X_POSITION_IN_MESSAGE = 2;
    private static final int D_Y_POSITION_IN_MESSAGE = 3;
    private static final int CONDITION_INDEX_POSITION_IN_MESSAGE = 4;
    private static final int ACCELERATION_POSITION_IN_MESSAGE = 5;

    private static final int FIRST_GEAR_VALUE = 40;
    private static final int FIRST_LAYER_START_POSITION_VALUE = 0;
    private static final int SECOND_LAYER_START_POSITION_VALUE = 1200;
    private static final int CONDITION_IDX_CENTRAL_VALUE = 0;
    private static final int CONDITION_IDX_UP_VALUE = 1;
    private static final int CONDITION_IDX_DOWN_VALUE = 2;
    private static final int D_X_VALUE = 10;
    private static final int D_Y_FIRST_GEAR_VALUE = 5;
    private static final int D_Y_SECOND_GEAR_VALUE = 10;
    private static final int D_Y_THIRD_GEAR_VALUE = 15;
    private static final int ACCELERATION_VALUE = 1;

    private int kmRes = 0;
    private int dX = 0;
    private int dY = 0;
    private int conditionIdx = 0;
    private int layer1Position = 0;
    private int layer2Position = 1200;
    private String carName;

    public Player(Car car, String name) {
        super(car.getName(), car.getHeight(), car.getWidth(), car.getBoxToBoxWidth(),
                car.getImageCentral(), car.getImageUp(), car.getImageDown(), car.getMaxTop(),
                car.getMaxBottom(), car.getX(), car.getY(), car.getMaxSpeed(), car.getMinSpeed(),
                car.getMaxX(), car.getMinX(), car.getRandomNumberTop(), car.getRandomNumber(),
                car.getFreeSpaceH());
        carName = name;
    }

    public int[] giveChanges() {
        int[] sharingInfo = new int[SHARING_INFO_SIZE];
        sharingInfo[X_POSITION_IN_MESSAGE] = x;
        sharingInfo[Y_POSITION_IN_MESSAGE] = y;
        sharingInfo[D_X_POSITION_IN_MESSAGE] = dX;
        sharingInfo[D_Y_POSITION_IN_MESSAGE] = dY;
        sharingInfo[CONDITION_INDEX_POSITION_IN_MESSAGE] = conditionIdx;
        sharingInfo[ACCELERATION_POSITION_IN_MESSAGE] = acceleration;
        return sharingInfo;
    }

    public void move() {
        wayLength += speed;
        speed += acceleration;
        if (speed <= minSpeed) {
            speed = minSpeed;
        }
        if (speed >= maxSpeed) {
            speed = maxSpeed;
        }
        if (speed > minSpeed) {
            y += dY;
            if (y <= maxTop) {
                y = maxTop;
            }
            if (y >= maxBottom) {
                y = maxBottom;
            }
        }
        if (speed >= FIRST_GEAR_VALUE) {
            x += dX;
            if (dX < 0) {
                speed -= ACCELERATION_VALUE;
            }
            if (x <= minX) {
                x = minX;
            }
            if (x >= maxX) {
                x = maxX;
            }
        } else {
            x -= D_X_VALUE;
            if (x <= minX) {
                x = minX;
            }
        }
        if (layer2Position - speed <= 0) {
            layer1Position = FIRST_LAYER_START_POSITION_VALUE;
            layer2Position = SECOND_LAYER_START_POSITION_VALUE;
            kmRes++;
        }
        layer1Position -= speed;
        layer2Position -= speed;
    }

    public void pressKey(KeyEvent event) {
        keyPressed(event);
    }

    public void releaseKey(KeyEvent event) {
        keyReleased(event);
    }

    private void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.VK_D) {
            dX = D_X_VALUE;
        }
        if (keyCode == KeyEvent.VK_A) {
            dX = -D_X_VALUE;
        }
        if (keyCode == KeyEvent.VK_S) {
            if (y < maxBottom && speed != 0) {
                image = imageDown;
                conditionIdx = CONDITION_IDX_DOWN_VALUE;
            }
            if (speed < maxSpeed / 4) {
                dY = D_Y_FIRST_GEAR_VALUE;
            } else if (speed < (maxSpeed / 2 + maxSpeed / 4)) {
                dY = D_Y_SECOND_GEAR_VALUE;
            } else {
                dY = D_Y_THIRD_GEAR_VALUE;
            }
        }
        if (keyCode == KeyEvent.VK_W) {
            if (y > maxTop && speed != 0) {
                image = imageUp;
                conditionIdx = CONDITION_IDX_UP_VALUE;
            }
            if (speed < maxSpeed / 4) {
                dY = -D_Y_FIRST_GEAR_VALUE;
            } else if (speed < (maxSpeed / 2 + maxSpeed / 4)) {
                dY = -D_Y_SECOND_GEAR_VALUE;
            } else {
                dY = -D_Y_THIRD_GEAR_VALUE;
            }
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            acceleration = ACCELERATION_VALUE;
        }
        if (keyCode == KeyEvent.VK_P) {
            acceleration = -ACCELERATION_VALUE;
        }
    }

    private void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_A) {
            dX = 0;
        }
        if ((keyCode == KeyEvent.VK_W) || (keyCode == KeyEvent.VK_S)) {
            image = imageCentral;
            conditionIdx = CONDITION_IDX_CENTRAL_VALUE;
            dY = 0;
        }
        if (keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_P) {
            acceleration = 0;
        }
    }
}
