package demo.iterators;
// Working on the example of Deitel (pp 837, 9th edition)
// re. Lists, LinkedLists and ListIterators.

import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;

public class ListTest {
    public static void main(String[] args) {
        // Δημιουργία ενός πίνακα με συμβολοσειρές (χρώματα)
        String[] colors = {"black", "yellow", "green", "blue", "violet", "silver"};
        List<String> list1 = new LinkedList<>(); // Πρώτη λίστα χρωμάτων

        // Προσθήκη κάθε χρώματος στη λίστα list1 (todo: bulk?)
        for (String color : colors) {
            list1.add(color);
        }

        // Δημιουργία δεύτερου πίνακα με άλλα χρώματα
        String[] colors2 = {"gold", "white", "brown", "blue", "gray", "silver"};
        List<String> list2 = new LinkedList<>(); // Δεύτερη λίστα χρωμάτων

        // Προσθήκη κάθε χρώματος στη λίστα list2 (todo: bulk?)
        for (String color : colors2) {
            list2.add(color);
        }

        // Συνένωση των δύο λιστών: προσθήκη όλων των στοιχείων της list2 στο τέλος της list1
        list1.addAll(list2);

        // Ελευθέρωση πόρων (αναφορά του list2 στο null, επιτρέποντας Garbage Collection) (todo: delete?)
        list2 = null;

        // Εκτύπωση όλων των στοιχείων της νέας λίστας
        printList(list1);

        // Μετατροπή όλων των στοιχείων της λίστας σε κεφαλαία
        convertToUppercaseStrings(list1);

        // Εκτύπωση των στοιχείων μετά τη μετατροπή
        printList(list1);

        // Διαγραφή στοιχείων από τη θέση 4 έως 6 (inclusive [4, 7))
        System.out.printf("%nDeleting elements 4 to 6...");
        removeItems(list1, 4, 7);

        // Εκτύπωση της λίστας μετά τη διαγραφή
        printList(list1);

        // Εκτύπωση των στοιχείων της λίστας σε αντίστροφη σειρά
        printReversedList(list1);
    }

    // Μέθοδος που εκτυπώνει τα στοιχεία της λίστας στη σειρά
    private static void printList(List<String> list) {
        System.out.printf("%nlist:%n");

        for (String color : list) {
            System.out.printf("%s ", color);
        }

        System.out.println(); // Νέα γραμμή στο τέλος
    }

    // Μέθοδος που μετατρέπει κάθε στοιχείο της λίστας σε κεφαλαία γράμματα
    private static void convertToUppercaseStrings(List<String> list) {
        ListIterator<String> iterator = list.listIterator(); // Δημιουργία iterator

        // Διάσχιση όλων των στοιχείων
        while (iterator.hasNext()) {
            String color = iterator.next(); // Λήψη του επόμενου στοιχείου
            iterator.set(color.toUpperCase()); // Αντικατάσταση με την κεφαλαιοποιημένη μορφή
        }
    }

    // Μέθοδος που αφαιρεί στοιχεία από τη λίστα, χρησιμοποιώντας υπολίστα
    private static void removeItems(List<String> list, int start, int end) {
        list.subList(start, end).clear(); // Διαγραφή όλων των στοιχείων στη συγκεκριμένη υπολίστα
    }

    // Μέθοδος που εκτυπώνει τα στοιχεία της λίστας σε αντίστροφη σειρά
    private static void printReversedList(List<String> list) {
        ListIterator<String> iterator = list.listIterator(list.size()); // Iterator ξεκινώντας από το τέλος

        System.out.printf("%nReversed List:%n");

        // Εκτύπωση των στοιχείων από το τέλος προς την αρχή
        while (iterator.hasPrevious()) {
            System.out.printf("%s ", iterator.previous());
        }
    }
}