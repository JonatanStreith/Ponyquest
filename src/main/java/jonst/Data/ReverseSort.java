package jonst.Data;

import java.util.Comparator;

public class ReverseSort implements Comparator<Object> {
    public int compare(Object o1, Object o2) {
        String s1 = (String) o1;
        String s2 = (String) o2;
        return s2.compareTo(s1);
    }
}
