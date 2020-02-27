package jonst.Data;

import jonst.Models.Objects.GenericObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Lambda {

    public static <T extends GenericObject> Predicate<T> predicateByName(String name){

        Predicate<T> pred = (T g) -> g.getName().equalsIgnoreCase(name) || g.getAlias().stream().anyMatch(name::equalsIgnoreCase);

        return pred;
    }

    public static Predicate<GenericObject> predicateById(String id){

        Predicate<GenericObject> pred = (GenericObject g) -> g.getId().equalsIgnoreCase(id);

        return pred;
    }


    public static <T, R> List<R> getSubvalues(List<T> list, Function<T, R> func) {

        List<R> returnList = new ArrayList<>();

        for (T t : list) {
            R r = func.apply(t);
            returnList.add(r);
        }
        return returnList;
    }

    public static <T, R> R getSubvalue(T t, Function<T, R> func) {
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

    public static <T> boolean exists(List<T> list, Predicate<T> predicate) {

        //Does the list contain an element that satisfies the provided predicate?

        for (T gen : list) {
            if (predicate.test(gen)) {
                return true;
            }
        }
        return false;
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

    public static <T> void processList(List<T> list, Predicate<T> predicate, Consumer<T> consumer) {
        for (T t : list) {
            if (predicate.test(t))
                consumer.accept(t);
        }
    }

    public static <T> void processList(List<T> list, Consumer<T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }

    public static <T, U> void processLists(List<T> firstList, List<U> secondList, DualTest<T, U> test, DualFactory<T, U> process) {

        List<T> returnList = new ArrayList<>();

        for (T first : firstList) {
            for (U second : secondList) {

                if (test.dualTest(first, second)) {
                    process.dualFactory(first, second);
                }
            }
        }
    }

}
