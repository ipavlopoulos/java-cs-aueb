package gr.aueb.cs.examples.unit;

public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public double divide(int a, int b) {
        if (b == 0) {
            //System.out.println("Divide by zero");
            throw new ArithmeticException("Δεν μπορείς να διαιρέσεις με το μηδέν!");
        }
        return (double) a / b;
    }
}

