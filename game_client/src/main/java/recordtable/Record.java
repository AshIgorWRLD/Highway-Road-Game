package recordtable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Record {
    private String name;
    private Integer value;

    public Record(String str, String str2) {
        name = str;
        value = Integer.parseInt(str2);
    }
}
