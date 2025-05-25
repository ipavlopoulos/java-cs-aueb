package gr.aueb.cs.projects.montyhall.app;

import gr.aueb.cs.projects.montyhall.logic.MontyHallGame;
import java.util.Scanner;

/**
 * Εκκίνηση της εφαρμογής Monty Hall με αλληλεπίδραση χρήστη.
 * Ο χρήστης διαλέγει πόρτα, και στη συνέχεια επιλέγει αν θα αλλάξει ή όχι.
 */
public class ConsoleUI {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Δημιουργούμε νέο παιχνίδι με τυχαία τοποθέτηση του δώρου
        MontyHallGame game = new MontyHallGame();

        // Ο χρήστης επιλέγει πόρτα
        System.out.println("Διάλεξε πόρτα (0, 1, 2): ");
        int choice = sc.nextInt();
        game.chooseDoor(choice);

        // Ο παρουσιαστής ανοίγει μία από τις άλλες πόρτες χωρίς το δώρο
        game.hostOpensDoor();

        // Εμφάνιση πόρτας που άνοιξε ο παρουσιαστής
        System.out.println("Ο παρουσιαστής άνοιξε την πόρτα: " + game.getHostOpens());

        // Ο χρήστης επιλέγει αν θα αλλάξει επιλογή
        System.out.println("Θες να αλλάξεις επιλογή; (ν/ο): ");
        String input = sc.next();

        // Αν η απάντηση είναι "ν", αλλάζει πόρτα. Διαφορετικά μένει.
        boolean win = input.equalsIgnoreCase("ν")
                ? game.switchAndCheckWin()
                : game.stayAndCheckWin();

        sc.close(); // Κλείσιμο του Scanner για αποφυγή διαρροών μνήμης.

        // Εκτύπωση αποτελέσματος
        System.out.println(win ? "Κέρδισες το βραβείο!" : "Έχασες!");
    }
}
