package demo.io;

import java.io.*;

public class GreekWriterUTF8 {
    public static void main(String[] args) throws IOException {
        // Δημιουργία Writer με ρητή κωδικοποίηση UTF-8
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream("greek.txt"), "UTF-8"
                )
        );

        writer.write("Καλημέρα");
        writer.newLine();
        writer.write("Πώς είσαι;");
        writer.newLine();
        writer.write("Αυτό είναι δοκιμαστικό αρχείο σε UTF-8.");
        writer.close();

        System.out.println("Το αρχείο greek.txt γράφτηκε με επιτυχία σε UTF-8.");
    }
}
