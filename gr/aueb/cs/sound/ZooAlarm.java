package gr.aueb.cs.sound;
import java.awt.Toolkit;

public class ZooAlarm {

        public static void main(String[] args) {
            // Παράδειγμα δεδομένων: Το κλουβί στη θέση 1 και 3 είναι άδεια
            String[] cages = {"Λιοντάρι", "", "Τίγρης", "   ", "Αρκούδα"};

            System.out.println("=== Έναρξη ελέγχου κλουβιών ===\n");

            for (int i = 0; i < cages.length; i++) {
                if (isCageEmpty(cages[i])) {
                    System.out.println("⚠️ ALERT: Το κλουβί " + i + " είναι ΑΔΕΙΟ!");

                    // Μέθοδος 1: ASCII Bell (για πραγματική κονσόλα)
                    System.out.print("\007");
                    System.out.flush();

                    // Μέθοδος 2: System Beep (πιο σίγουρο για Windows/macOS)
                    Toolkit.getDefaultToolkit().beep();

                    // Μικρή παύση για να προλάβει να ακουστεί ο ήχος πριν τον επόμενο
                    try { Thread.sleep(500); } catch (InterruptedException e) {}
                } else {
                    System.out.println("✅ Κλουβί " + i + ": OK (" + cages[i] + ")");
                }
            }

            System.out.println("\n=== Ο έλεγχος ολοκληρώθηκε ===");
        }

        public static boolean isCageEmpty(String cage) {
            return cage == null || cage.trim().isEmpty();
        }
}

