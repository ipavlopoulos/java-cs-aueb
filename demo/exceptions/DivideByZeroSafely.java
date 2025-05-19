package demo.exceptions;

import java.util.InputMismatchException;

import java.util.Scanner;


public class DivideByZeroSafely {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Δώσε αριθμητή: ");
            int numerator = scanner.nextInt();

            System.out.print("Δώσε παρονομαστή: ");
            int denominator = scanner.nextInt();

            int result = quotient(numerator, denominator);

            System.out.println("Το αποτέλεσμα είναι: " + result);

            } catch (ArithmeticException e) {
            System.err.println("Σφάλμα: Διαίρεση με το μηδέν δεν επιτρέπεται: " + e.getMessage());

        } catch (InputMismatchException e) {
            System.err.println("Σφάλμα: Παρακαλώ δώστε έγκυρους ακέραιους αριθμούς.");

        } finally {
            System.out.println("Τέλος προγράμματος (finally block).");
            scanner.close();
        }
    }

    private static int quotient(int numerator, int denominator) {
        return numerator / denominator;
    }
}
