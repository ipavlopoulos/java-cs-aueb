package gr.aueb.cs.projects.walkroute.llist;

public class WalkRouteDemo {
    public static void main(String[] args) {
        WalkRoute route = new WalkRoute(true);

        route.addStep("Πλατεία Βικτωρίας");
        route.addStep("Μαυροματαίων");
        route.addStep("ΕΜΠ");
        route.addStep("Πολυτεχνείο");
        route.printRoute();

        System.out.println("\n⚠️ Εμπόδιο στην 'Μαυροματαίων' — την αποφεύγουμε.");
        route.avoidStep("Μαυροματαίων");
        route.printRoute();

        System.out.println("\n➕ Παρεμβάλλουμε 'Στουρνάρη' πριν το 'Πολυτεχνείο'");
        route.insertBefore("Πολυτεχνείο", "Στουρνάρη");
        route.printRoute();
    }
}