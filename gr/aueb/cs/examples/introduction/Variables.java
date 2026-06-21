package gr.aueb.cs.examples.introduction;

public class Variables {

    private int x;

    public Variables(){
        this.x = 0;
    }

    private void precisionLoss(){
        // Ο αριθμός 16.777.217 χρειάζεται 25 bits για να αναπαρασταθεί πλήρως
        int originalInt = 16777217;

        // Αυτόματη μετατροπή (widening primitive conversion) από int σε float
        float promotedFloat = originalInt;

        // Μετατροπή ξανά σε int για να δούμε αν χάθηκε κάτι
        int backToInt = (int) promotedFloat;

        System.out.println("Αρχικός Int:  " + originalInt);
        System.out.println("Τιμή σε Float: " + promotedFloat);
        System.out.println("Int μετά τη μετατροπή: " + backToInt);

        if (originalInt != backToInt) {
            System.out.println("\nΠΡΟΣΟΧΗ: Οι τιμές ΔΕΝ είναι ίσες!");
            System.out.println("Διαφορά: " + (originalInt - backToInt));
        }

    }

    public static void main(String[] args) {
        // byte x = (byte) (200L-200L);

        Variables v = new Variables();
        v.x = 10;
        System.out.println(v.x);

        // overflow
        byte x = 127;

        System.out.println(x);
        x += 1;
        // x = (byte) (x + 1);
        System.out.println(x);
        String s = "Hello World";
        System.out.println(s.charAt(2));

        // precision loss
        double d = 3.14;
        float f = 3.14f;
        System.out.println(d);
        System.out.println(f);

        double big = 1.123456789012345;
        float small = 1.123456789012345f;
        System.out.println(big);
        System.out.println(small);

        /* ⚠️
            Όταν γράφουμε δεκαδικό αριθμό, η Java υποθέτει
            ότι θέλουμε τον πιο *ακριβή* τύπο, δηλαδή double.
            Αν θέλουμε float, πρέπει να το δηλώσουμε.
         */
        v.precisionLoss();

    }


}
