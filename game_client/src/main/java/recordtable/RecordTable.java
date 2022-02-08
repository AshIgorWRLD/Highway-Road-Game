package recordtable;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;

@Slf4j
public class RecordTable {
    private static final String PARSE_SYMBOL = "=";
    private static final int PLAYER_NAME_POSITION = 0;
    private static final int PLAYER_SCORE_POSITION = 1;
    private static final int VISIBLE_HIGH_SCORES_AMOUNT = 10;

    private final File file = new File("src\\main\\resources", "RECORDS.txt");

    public void refreshRecordTable(String name, int value) {
        try (FileReader fileReader = new FileReader(file)) {
            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                ArrayList<Record> recordList = new ArrayList<Record>();
                String line = bufferedReader.readLine();
                while (line != null) {
                    String[] subStr = line.split(PARSE_SYMBOL);
                    Record record = new Record(subStr[PLAYER_NAME_POSITION],
                            subStr[PLAYER_SCORE_POSITION]);
                    recordList.add(record);
                    line = bufferedReader.readLine();
                }
                addNewRecord(name, value, recordList);
                recordList.sort(new RecordComparator());
                int i = 0;
                FileOutputStream fileOutputStream =
                        new FileOutputStream("src\\main\\resources\\RECORDS.txt");
                PrintStream printStream = new PrintStream(fileOutputStream);
                log.info("---RECORD TABLE---");
                for (Record record : recordList) {
                    if (i < VISIBLE_HIGH_SCORES_AMOUNT) {
                        log.info((i + 1) + ")" + record.getName() + ": " + record.getValue());
                        i++;
                    }
                    printStream.println(record.getName() + PARSE_SYMBOL + record.getValue());
                }
                printStream.close();
                fileOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            log.error("UNABLE TO FIND RECORDS FILE");
        } catch (IOException e) {
            log.error("UNABLE TO WORK WITH RECORDS FILE");
        }
    }

    public void addNewRecord(String name, int value, ArrayList<Record> list) throws IOException {
        log.info(name + ", your score: " + value);
        list.add(new Record(name, String.valueOf(value)));
    }
}
