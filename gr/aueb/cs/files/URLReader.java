package gr.aueb.cs.files;

import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class URLReader {
    public static void main(String[] args) {
        try {
            // Βήμα 1: Ορισμός URL
            URL url = new URL("https://www.gutenberg.org/files/1065/1065-0.txt");

            // Βήμα 2: Άνοιγμα ροής εισόδου από το URL
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream(), "UTF-8")
            );

            // Βήμα 3: Ανάγνωση περιεχομένου γραμμή-γραμμή
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null && count < 20) { // εμφάνιση μόνο 20 γραμμών
                System.out.println(line);
                count++;
            }

            // Βήμα 4: Κλείσιμο ροής
            reader.close();
        } catch (IOException e) {
            System.err.println("Σφάλμα: " + e.getMessage());
        }
    }
}
