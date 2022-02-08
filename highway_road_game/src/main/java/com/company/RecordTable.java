package com.company;

import java.io.*;
import java.util.*;

public class RecordTable {

    public File file = new File("src\\main\\resources", "RECORDS.txt");

    public class Record{
        String name;
        Integer value;
        public Record(String str, String str2){
            name = str;
            value = Integer.parseInt(str2);
        }
    }
    public void refreshRecordTable(String name,int val){
        try{
            ArrayList<Record> RecordList = new ArrayList<Record>();
            FileReader FR = new FileReader(file);
            BufferedReader reader = new BufferedReader(FR);
            String value = reader.readLine();
            while(value != null){
                String parse_symbol = "=";
                String[] sub_str = value.split(parse_symbol);
                Record record = new Record(sub_str[0], sub_str[1]);
                RecordList.add(record);
                value = reader.readLine();
            }
            AddNewRecord(name, val, RecordList);
            Collections.sort(RecordList,new RecordComparator());
            int i = 0;
            FileOutputStream fos = new FileOutputStream("src\\main\\resources\\RECORDS.txt");
            PrintStream ps = new PrintStream(fos);
            System.out.println("---RECORD TABLE---");
            for(Record record:RecordList) {
                if(i<10){
                    System.out.println((i+1)+")"+record.name+": "+record.value);
                    i++;
                }
                ps.println(record.name+"="+record.value);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void AddNewRecord(String name, int value, ArrayList<Record> list) throws IOException {
        System.out.println(name + ", your score: " + value);
        list.add(new Record(name,String.valueOf(value)));

    }

}
