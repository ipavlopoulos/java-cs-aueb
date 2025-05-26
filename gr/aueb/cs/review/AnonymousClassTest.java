package gr.aueb.cs.review;

public class AnonymousClassTest {

        public static void main(String args[]) {

            // Δημιουργία αντικειμένου τύπου UniOpa και άμεση κλήση της μεθόδου greeting()
            // Η μέθοδος greeting επιστρέφει "Hello students!"
            System.out.println(new UniOpa().greeting());

            // Δημιουργία ανώνυμης κλάσης που υλοποιεί το interface Ido
            // Η μέθοδος hi() υλοποιείται επί τόπου (inline) και επιστρέφει "hello"
            // Ακολουθεί άμεση κλήση της μεθόδου hi() στο αντικείμενο που δημιουργείται
            System.out.println(new Ido() {
                public String hi() {
                    return "hello";
                }
            }.hi());  // Εκτυπώνει: hello
        }
    }

    // Ορισμός ενός λειτουργικού interface με μία αφηρημένη μέθοδο
    interface Ido {
        String hi(); // abstract method που πρέπει να υλοποιηθεί
    }

    // Κλάση με ένα πεδίο name (δεν χρησιμοποιείται εδώ) και μία μέθοδο που επιστρέφει μήνυμα
    class UniOpa {
        String name;

        // Επιστρέφει χαιρετισμό προς τους φοιτητές
        String greeting() {
            return "Hello students!";
        }
    }

