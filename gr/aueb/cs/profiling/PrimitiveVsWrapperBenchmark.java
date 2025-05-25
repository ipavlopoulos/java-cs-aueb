package gr.aueb.cs.profiling;

public class PrimitiveVsWrapperBenchmark {
    public static void main(String[] args) {
        final int N = 1_000_000;

        // Primitive int
        long startPrimitive = System.nanoTime();
        int sumInt = 0;
        for (int i = 0; i < N; i++) {
            sumInt += i;
        }
        long endPrimitive = System.nanoTime();
        long durationPrimitive = endPrimitive - startPrimitive;

        // Wrapper Integer
        long startWrapper = System.nanoTime();
        Integer sumInteger = 0;
        for (int i = 0; i < N; i++) {
            sumInteger += i; // unboxing και re-boxing κάθε φορά
        }
        long endWrapper = System.nanoTime();
        long durationWrapper = endWrapper - startWrapper;

        // Αποτελέσματα
        System.out.println("Χρόνος με int: " + durationPrimitive / 1_000_000.0 + " ms");
        System.out.println("Χρόνος με Integer: " + durationWrapper / 1_000_000.0 + " ms");
    }
}

