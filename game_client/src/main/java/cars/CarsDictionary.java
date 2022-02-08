package cars;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Getter
@Setter
public class CarsDictionary {
    private static final String PARSE_SYMBOL = "=";
    private static final String PARSE_STATS_SYMBOL = ",";
    private static final String END_OF_FILE = "$";
    private static final int CAR_NAME_POSITION = 0;
    private static final int CAR_STATS_POSITION = 1;
    private static final int CAR_HEIGHT_STAT_POSITION = 0;
    private static final int CAR_WIDTH_STAT_POSITION = 1;
    private static final int CAR_BOX_2_BOX_WIDTH_STAT_POSITION = 2;
    private static final int CAR_CENTRAL_IMAGE_STAT_POSITION = 3;
    private static final int CAR_UP_IMAGE_STAT_POSITION = 4;
    private static final int CAR_DOWN_IMAGE_STAT_POSITION = 5;
    private static final int CAR_MAX_TOP_STAT_POSITION = 6;
    private static final int CAR_MAX_BOTTOM_STAT_POSITION = 7;
    private static final int CAR_X_STAT_POSITION = 8;
    private static final int CAR_Y_STAT_POSITION = 9;
    private static final int CAR_MAX_SPEED_STAT_POSITION = 10;
    private static final int CAR_MIN_SPEED_STAT_POSITION = 11;
    private static final int CAR_MAX_X_STAT_POSITION = 12;
    private static final int CAR_MIN_X_STAT_POSITION = 13;
    private static final int CAR_RANDOM_NUMBER_TOP_STAT_POSITION = 14;
    private static final int CAR_RANDOM_NUMBER_STAT_POSITION = 15;
    private static final int CAR_FREE_SPACE_H_STAT_POSITION = 16;
    private static final int SECOND_LINE_OFFSET = 120;
    private static final int THIRD_LINE_OFFSET = 270;
    private static final int FIRST_LINE_IDX = 0;
    private static final int SECOND_LINE_IDX = 1;
    private static final int THIRD_LINE_IDX = 2;
    private static final int CARS_NUMBER = 9;
    private static final int LINES_NUMBER = 3;

    private final Map<String, Car> dictionary;
    private final File file;
    private final CarName carName;

    public CarsDictionary() {
        carName = new CarName();
        file = new File("src\\main\\resources", "ALL_CARS_PATH.txt");
        dictionary = new HashMap<String, Car>();
        parseCarsStats();
    }

    public Car getCar(String carName) {
        return dictionary.get(carName);
    }

    private void parseCarsStats() {
        try (FileReader fileReader = new FileReader(file)) {
            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String value = bufferedReader.readLine();
                while (!value.equals(END_OF_FILE)) {
                    String[] subStr = value.split(PARSE_SYMBOL);
                    String[] subStatStr = subStr[CAR_STATS_POSITION].split(PARSE_STATS_SYMBOL);
                    Car newCar = new Car(carName.GetName(subStr[CAR_NAME_POSITION]),
                            Integer.parseInt(subStatStr[CAR_HEIGHT_STAT_POSITION]),
                            Integer.parseInt(subStatStr[CAR_WIDTH_STAT_POSITION]),
                            Integer.parseInt(subStatStr[CAR_BOX_2_BOX_WIDTH_STAT_POSITION]),
                            new ImageIcon(subStatStr[CAR_CENTRAL_IMAGE_STAT_POSITION]).getImage(),
                            new ImageIcon(subStatStr[CAR_UP_IMAGE_STAT_POSITION]).getImage(),
                            new ImageIcon(subStatStr[CAR_DOWN_IMAGE_STAT_POSITION]).getImage(),
                            Integer.parseInt(subStatStr[CAR_MAX_TOP_STAT_POSITION]),
                            Integer.parseInt(subStatStr[CAR_MAX_BOTTOM_STAT_POSITION]),
                            Integer.parseInt(subStatStr[CAR_X_STAT_POSITION]),
                            Integer.parseInt(subStatStr[CAR_Y_STAT_POSITION]),
                            Integer.parseInt(subStatStr[CAR_MAX_SPEED_STAT_POSITION]),
                            Integer.parseInt(subStatStr[CAR_MIN_SPEED_STAT_POSITION]),
                            Integer.parseInt(subStatStr[CAR_MAX_X_STAT_POSITION]),
                            Integer.parseInt(subStatStr[CAR_MIN_X_STAT_POSITION]),
                            Integer.parseInt(subStatStr[CAR_RANDOM_NUMBER_TOP_STAT_POSITION]),
                            Integer.parseInt(subStatStr[CAR_RANDOM_NUMBER_STAT_POSITION]),
                            Integer.parseInt(subStatStr[CAR_FREE_SPACE_H_STAT_POSITION]));
                    dictionary.put(subStr[CAR_NAME_POSITION], newCar);
                    value = bufferedReader.readLine();
                }
            }
        } catch (FileNotFoundException e) {
            log.error("UNABLE TO OPEN CAR DICTIONARY FILE READER", e);
        } catch (IOException e) {
            log.error("UNABLE TO WORK WITH DICTIONARY FILE READER", e);
        }
    }

    public String getEnemyCar(String name, int number) {
        Random random = new Random();
        int randomNumber;
        if (number < 0) {
            randomNumber = random.nextInt(CARS_NUMBER);
        } else {
            randomNumber = number;
        }
        switch (randomNumber) {
            case 0 -> {
                String tmp = "SUBARU";
                if (name.equals(tmp)) {
                    return "VOLKSWAGEN_POLO";
                }
                return "SUBARU";
            }
            case 1 -> {
                String tmp = "KIA";
                if (name.equals(tmp)) {
                    return "VOLKSWAGEN_POLO";
                }
                return "KIA";
            }
            case 2 -> {
                String tmp = "LAND_CRUISER";
                if (name.equals(tmp)) {
                    return "VOLKSWAGEN_POLO";
                }
                return "LAND_CRUISER";
            }
            case 3 -> {
                String tmp = "BLACK_LAND_CRUISER";
                if (name.equals(tmp)) {
                    return "VOLKSWAGEN_POLO";
                }
                return "BLACK_LAND_CRUISER";
            }
            case 4 -> {
                String tmp = "FORD_FOCUS";
                if (name.equals(tmp)) {
                    return "VOLKSWAGEN_POLO";
                }
                return "FORD_FOCUS";
            }
            case 5 -> {
                String tmp = "BENZ_TRUCK";
                if (name.equals(tmp)) {
                    return "VOLKSWAGEN_POLO";
                }
                return "BENZ_TRUCK";
            }
            case 6 -> {
                String tmp = "BUS";
                if (name.equals(tmp)) {
                    return "VOLKSWAGEN_POLO";
                }
                return "BUS";
            }
            case 7 -> {
                String tmp = "ROOT_TAXI";
                if (name.equals(tmp)) {
                    return "VOLKSWAGEN_POLO";
                }
                return "ROOT_TAXI";
            }
            case 8 -> {
                String tmp = "TRUCK";
                if (name.equals(tmp)) {
                    return "VOLKSWAGEN_POLO";
                }
                return "TRUCK";
            }
        }
        return null;
    }

    public int getEnemyY(Car enemyCar, int lineNumber) {
        Random random = new Random();
        int number;
        if (lineNumber == 0) {
            number = random.nextInt(LINES_NUMBER);
        } else {
            number = lineNumber - 1;
        }
        switch (number) {
            case FIRST_LINE_IDX -> {
                return enemyCar.getMaxTop() + random.nextInt(enemyCar.getRandomNumberTop());
            }
            case SECOND_LINE_IDX -> {
                return enemyCar.getMaxTop() + SECOND_LINE_OFFSET +
                        random.nextInt(enemyCar.getRandomNumber());
            }
            case THIRD_LINE_IDX -> {
                return enemyCar.getMaxTop() + THIRD_LINE_OFFSET +
                        random.nextInt(enemyCar.getRandomNumber());
            }
        }
        return 0;
    }
}
