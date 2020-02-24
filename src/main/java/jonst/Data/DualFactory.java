package jonst.Data;

@FunctionalInterface
public interface DualFactory<T, U> {
    public void dualFactory(T a, U b);
}