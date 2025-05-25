package gr.aueb.cs.generics;

public class GenericMethodTest {
    public static void main(String[] args) {
        //String[] words = {"hello", "world"};
        //printArray(words);    // T ==> String

        Integer[] numbers = {1, 2, 3};
        printArray(numbers);  // T ==> Integer

        //printArray('oops');

    }


    private static <T extends Number> void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
    private static <T extends Double> void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

}
