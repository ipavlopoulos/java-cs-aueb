package gr.aueb.cs.examples.exceptions;

public class ThrowTest {
    public static void main(String[] args) {
        try {
            throw new RuntimeException("Σφάλμα!");
        } catch (RuntimeException e) {
            System.out.println("Πιάστηκε εξαίρεση: " + e.getMessage());
        }
    }
}
