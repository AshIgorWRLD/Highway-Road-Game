package com.company;

import java.util.Comparator;

public class RecordComparator implements Comparator<RecordTable.Record> {
    @Override
    public int compare(RecordTable.Record r1, RecordTable.Record r2){
        return r2.value.compareTo(r1.value);
    }
}
