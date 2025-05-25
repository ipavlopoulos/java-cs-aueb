package gr.aueb.cs.profiling;

import java.util.ArrayList;

public class EnsureCapacityDemo {
    public static void main(String[] args) {
        final int N = 500000;

        // Πρώτο σενάριο: χωρίς ensureCapacity
        long start1 = System.nanoTime();
        ArrayList<String> list1 = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            list1.add("obstacle_" + i);
        }
        long end1 = System.nanoTime();
        double time1 = (end1 - start1) / 1e6;

        // Δεύτερο σενάριο: με ensureCapacity
        long start2 = System.nanoTime();
        ArrayList<String> list2 = new ArrayList<>();
        list2.ensureCapacity(N);
        for (int i = 0; i < N; i++) {
            list2.add("obstacle_" + i);
        }
        long end2 = System.nanoTime();
        double time2 = (end2 - start2) / 1e6;

        // Αποτελέσματα
        System.out.printf("Χωρίς ensureCapacity: %.2f ms%n", time1);
        System.out.printf("Με    ensureCapacity: %.2f ms%n", time2);
    }
}
