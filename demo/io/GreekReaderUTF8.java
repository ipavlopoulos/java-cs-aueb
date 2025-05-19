package demo.io;

import java.io.*;

public class GreekReaderUTF8 {
    public static void main(String[] args) throws IOException {
        // Δεν χρησιμοποιούμε FileWriter, γιατί γράφει με default encoding (π.χ. Cp1253 στα Windows)
        // δεν είναι ασφαλές για UTF-8.
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("greek.txt"), "UTF-8" // δοκιμάστε "ISO-8859-7" για πείραμα
                )
        );

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        reader.close();
    }
}