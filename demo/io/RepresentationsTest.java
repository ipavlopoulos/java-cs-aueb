package demo.io;

public class RepresentationsTest {

    public static void main(String[] args) {
        System.out.println("1. Integers");
        printBinary(5);

        System.out.println("2. Unicode");
        printBinary('5');

        printBinary();
    }

    private static void printBinary(int number) {
        String binary = Integer.toBinaryString(number);
        System.out.println("Η δυαδική αναπαράσταση του 5 είναι: " + binary);
    }

    private static void printBinary(char number) {
        int code = (int) number;
        String binary = String.format("%16s", Integer.toBinaryString(code)).replace(' ', '0');
        System.out.println("Unicode: " + code);           // 53
        System.out.println("Δυαδικό: " + binary);         // 0000000000110101
    }

    private static void printBinary() {
        String ch = "A";
        System.out.println("Μήκος '"+ch+"' = " + ch.length()); // 1

        String emoji = "😊";
        System.out.println("Μήκος '"+emoji+"' = " + emoji.length()); // 2!
        System.out.println("1ο char: " + Integer.toHexString(emoji.charAt(0))); // d83d
        System.out.println("2ο char: " + Integer.toHexString(emoji.charAt(1))); // de0a

        // Η μέθοδος codePointCount(int beginIndex, int endIndex) της κλάσης String
        // επιστρέφει πόσους πραγματικούς χαρακτήρες Unicode (code points) υπάρχουν
        // στο υποσύνολο της συμβολοσειράς από beginIndex έως endIndex.
        System.out.println("Χαρακτήρες (code points): " + emoji.codePointCount(0, emoji.length())); // 1
    }
}
