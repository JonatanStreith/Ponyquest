package jonst.Data;

@FunctionalInterface
public interface DualTest<T, U> {
    public boolean dualTest(T a, U b);
}
