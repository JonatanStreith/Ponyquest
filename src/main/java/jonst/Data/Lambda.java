package jonst.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Lambda {




    public static <T, R> List<R> getSubvalues(List<T> list, Function<T, R> func){

        List<R> returnList = new ArrayList<>();

        for (T t: list) {
            R r = func.apply(t);
            returnList.add(r);
        }
        return returnList;
    }

    public static <T, R> R getSubvalue(T t, Function<T,R> func){
        R r = func.apply(t);

        return r;
    }


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
