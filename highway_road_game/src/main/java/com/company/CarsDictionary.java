package com.company;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CarsDictionary {
    public Map<String, Car> dictionary = new HashMap<String, Car>();
    public File file = new File("src\\main\\resources", "ALL_CARS_PATH.txt");
    public CarName CN = new CarName();
    public CarsDictionary(){
        ParseCarsStats();
    }
    public void ParseCarsStats(){
        try{
            FileReader FR = new FileReader(file);
            BufferedReader reader = new BufferedReader(FR);
            String value = reader.readLine();
            while(!value.equals("$")){
                String parse_symbol = "=";
                String[] sub_str = value.split(parse_symbol);
                String parse_stat_symbol = ",";
                String[] sub_stat_str = sub_str[1].split(parse_stat_symbol);
                Car new_car = new Car(CN.GetName(sub_str[0]),Integer.parseInt(sub_stat_str[0]),
                        Integer.parseInt(sub_stat_str[1]),Integer.parseInt(sub_stat_str[2]),
                        new ImageIcon(sub_stat_str[3]).getImage(),new ImageIcon(sub_stat_str[4]).getImage(),
                        new ImageIcon(sub_stat_str[5]).getImage(),Integer.parseInt(sub_stat_str[6]),
                        Integer.parseInt(sub_stat_str[7]), Integer.parseInt(sub_stat_str[8]),
                        Integer.parseInt(sub_stat_str[9]),Integer.parseInt(sub_stat_str[10]),
                        Integer.parseInt(sub_stat_str[11]), Integer.parseInt(sub_stat_str[12]),
                        Integer.parseInt(sub_stat_str[13]), Integer.parseInt(sub_stat_str[14]),
                        Integer.parseInt(sub_stat_str[15]), Integer.parseInt(sub_stat_str[16]));
                dictionary.put(sub_str[0], new_car);
                value = reader.readLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getEnemyCar(String name, int numb){
        Random random = new Random();
        int number;
        if(numb < 0) {
            number = random.nextInt(9);
        }else{
            number = numb;
        }
        switch (number) {
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
            case 8 ->{
                String tmp = "TRUCK";
                if (name.equals(tmp)) {
                    return "VOLKSWAGEN_POLO";
                }
                return "TRUCK";
            }
        }
        return null;
    }

    public int GetEnemyY(Car enemyCar, int lineN){
        Random random = new Random();
        int number;
        if(lineN == 0){
            number = random.nextInt(3);
        }else{
            number = lineN-1;
        }
        switch (number){
            case 0->{
                return enemyCar.MAX_TOP + random.nextInt(enemyCar.random_number_top);
            }
            case 1->{
                return enemyCar.MAX_TOP+120 + random.nextInt(enemyCar.random_number);
            }
            case 2->{
                return enemyCar.MAX_TOP+270 + random.nextInt(enemyCar.random_number);
            }
        }
        return 0;
    }
}
