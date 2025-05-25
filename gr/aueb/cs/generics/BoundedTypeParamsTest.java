package gr.aueb.cs.generics;

public class BoundedTypeParamsTest {
    public static void main(String[] args) {
        NumericBox<Integer> box1 = new NumericBox<>();
        NumericBox<Double> box2 = new NumericBox<>();
        //NumericBox<String> box3 = new NumericBox<>(); // ⛔ Compile-time error
    }
}

class NumericBox<T extends Number> {
    private T value;

    public void set(T value) {
        this.value = value;
    }

    public double getDoubleValue() {
        return value.doubleValue(); // calling a method of the supertype
    }
}
