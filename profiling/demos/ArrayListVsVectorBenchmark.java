package profiling.demos;

import java.util.ArrayList;
import java.util.Vector;
import java.util.List;

public class ArrayListVsVectorBenchmark {
    public static void main(String[] args) {
        final int N = 100_000; // will add 100k integers

        // ArrayList ==> (async) faster
        List<Integer> arrayList = new ArrayList<>();
        long startArrayList = System.nanoTime();
        for (int i = 0; i < N; i++) {
            arrayList.add(i);
        }
        long endArrayList = System.nanoTime();
        System.out.println("ArrayList time: " + (endArrayList - startArrayList)/1_000_000.0 + " ms");

        // Vector ==> (sync) typically slower
        List<Integer> vector = new Vector<>();
        long startVector = System.nanoTime();
        for (int i = 0; i < N; i++) {
            vector.add(i);
        }
        long endVector = System.nanoTime();
        System.out.println("Vector time: " + (endVector - startVector)/1_000_000.0 + " ms");

    }
}
