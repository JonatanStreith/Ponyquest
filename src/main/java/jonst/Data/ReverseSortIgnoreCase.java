package jonst.Data;

import java.util.Comparator;

public class ReverseSortIgnoreCase implements Comparator<String> {
    public int compare(String s1, String s2) {

        return s2.toLowerCase().compareTo(s1.toLowerCase());
    }
}

