package demo.exceptions;

import java.util.Scanner;

public class DivideByZeroWithoutExceptionHandling {

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Δώσε αριθμητή: ");
            int numerator = scanner.nextInt();

            System.out.print("Δώσε παρονομαστή: ");
            int denominator = scanner.nextInt();

            // Αν denominator == 0, το πρόγραμμα θα καταρρεύσει με ArithmeticException
            int result = quotient(numerator, denominator);

            System.out.println("Το αποτέλεσμα είναι: " + result);
        }

        private static int quotient(int numerator, int denominator) {
            return numerator / denominator;
        }
    }
