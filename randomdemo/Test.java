package randomdemo;

import java.util.Random;

/**
 * Demonstrates and compares Math.random() and Random class in Java.
 */
public class Test {

    public static void main(String[] args) {
        System.out.println("=== Using Math.random() ===");
        for (int i = 0; i < 5; i++) {
            double randValue = Math.random(); // [0.0, 1.0)
            int door = (int) (randValue * 3); // 0, 1, 2
            System.out.printf("Random double: %.4f → door %d%n", randValue, door);
        }

        System.out.println("\n=== Using Random class ===");
        Random rand = new Random(); // no seed: different each time
        for (int i = 0; i < 5; i++) {
            int door = rand.nextInt(3); // 0, 1, 2
            System.out.println("Random door: " + door);
        }

        System.out.println("\n=== Using Random with fixed seed (42) ===");
        // todo: discuss where is this useful
        Random fixedRand = new Random(42); // deterministic sequence
        for (int i = 0; i < 5; i++) {
            int door = fixedRand.nextInt(3);
            System.out.println("Seeded door: " + door);
        }
    }
}
