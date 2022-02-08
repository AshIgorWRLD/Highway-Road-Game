package recordtable;

import java.util.Comparator;

public class RecordComparator implements Comparator<Record> {
    @Override
    public int compare(Record record1, Record record2) {
        return record2.getValue().compareTo(record1.getValue());
    }
}
