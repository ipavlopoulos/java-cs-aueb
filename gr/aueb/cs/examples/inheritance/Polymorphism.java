package gr.aueb.cs.examples.inheritance;

class X {
    int a;
    X(int i) {
        a = i;
    }
}
class Y extends X {
    int b;
    Y(int i, int j) {
        super(j);
        b = i;
    }
}
class Polymorphism {
    public static void main(String args[]) {
        X x = new X(10);
        X x2;
        Y y = new Y(5, 6);
        x2 = x; // OK, αναφορές ίδιας κλάσης
        System.out.println("x2.a: " + x2.a);
        x2 = y; // πάλι Ok, η κλάση Y είναι υποκλάση της X
        System.out.println("x2.a: " + x2.a);
// αναφορές σε X γνωρίζουν μόνο μέλη της κλάσης X
        x2.a = 19; // OK
// x2.b = 27; // Λάθος, η κλάση X δεν έχει μέλος b
    }
}
