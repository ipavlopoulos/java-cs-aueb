package demo.generics;

import java.util.ArrayList;

public class GenericsTest {

    public static void main(String[] args) {
        // Proper use if only integers are involved
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(20);
        //numbers.add("This is a test");

        int sum = 0;
        for (int n : numbers) {
            sum += n;
        }
        System.out.println("Άθροισμα: " + sum);

        // Using objects instead of Integers
        ArrayList<Object> rawNumbers = new ArrayList<>();
        rawNumbers.add(10);
        rawNumbers.add(20);
        rawNumbers.add(3.0);

        int rawSum = 0;
        for (Object n : rawNumbers) {
            // casting to integers
            if (n instanceof Integer) {
                rawSum += (int) n;
            }
            // casting in case of Double
            else if (n instanceof Double) {
                // cast to Double, then get the int value
                rawSum += ((Double) n).intValue();
            }
            // ignoring the rest
        }
        System.out.println("Άθροισμα: " + rawSum);

    }
}
