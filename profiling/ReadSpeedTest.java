package profiling;

import java.io.*;

public class ReadSpeedTest {
    public static void main(String[] args) throws IOException {
        String filename = "bigfile.dat";
        writeBigFile(filename);

        System.out.println("Ανάγνωση με FileInputStream:");
        long time1 = readWithFileInputStream(filename);

        System.out.println("\nΑνάγνωση με BufferedInputStream:");
        long time2 = readWithBufferedInputStream(filename);

        System.out.printf("\nΚαθυστέρηση χωρίς buffer:  %.2f δευτερόλεπτα\n", time1 / 1000.0);
        System.out.printf("Καθυστέρηση με buffer:      %.2f δευτερόλεπτα\n", time2 / 1000.0);
    }

    public static long readWithFileInputStream(String filename) throws IOException {
        FileInputStream in = new FileInputStream(filename);
        long start = System.currentTimeMillis();

        while (in.read() != -1) {
            // διαβάζουμε byte-προς-byte
        }

        in.close();
        return System.currentTimeMillis() - start;
    }

    public static long readWithBufferedInputStream(String filename) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));
        long start = System.currentTimeMillis();

        while (in.read() != -1) {
            // διαβάζουμε byte-προς-byte αλλά από μνήμη
        }

        in.close();
        return System.currentTimeMillis() - start;
    }

    public static void writeBigFile(String filename) throws IOException {
        FileOutputStream f = new FileOutputStream(filename);
        for (int i = 0; i < 1_000_000; i++) { // 1MB περίπου
            f.write('A');
        }
        f.close();
    }
}