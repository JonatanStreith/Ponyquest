package jonst.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Lambda {

    public static <T> List<T> subList(List<T> list, Predicate<T> predicate) {

        List<T> returnList = new ArrayList<>();

        for (T gen : list) {
            if (predicate.test(gen)) {
                returnList.add(gen);
            }
        }
        return returnList;
    }

    public static <T, U> List<T> subList(List<T> firstList, List<U> secondList, DualTest<T, U> dual) {

        List<T> returnList = new ArrayList<>();

        for (T first : firstList) {
            for (U second : secondList) {

                if (dual.dualTest(first, second)) {
                    returnList.add(first);
                }
            }
        }
        return returnList;
    }

    public static <T> T getFirst(List<T> list, Predicate<T> predicate) {

        for (T gen : list) {
            if (predicate.test(gen)) {
                return gen;
            }
        }
        return null;
    }

    public static <T, U> T getFirst(List<T> firstList, List<U> secondList, DualTest<T, U> dual) {

        for (T first : firstList) {
            for (U second : secondList) {

                if (dual.dualTest(first, second)) {
                    return first;
                }
            }
        }
        return null;
    }
}
