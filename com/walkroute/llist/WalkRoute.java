package com.walkroute.llist;

public class WalkRoute {
    private RouteNode start;
    public boolean verbose;

    WalkRoute(boolean verbose) {
        this.verbose = verbose;
        /*
         * Όταν verbose = true, και ο χρήστης καλεί την printRoute(), θα εμφανιστούν
         * τόσο τα εμπόδια όσο και τα σημεία της διαδρομής. Αν είναι false, θα
         * εμφανιστούν
         * μόνο τα σημεία της διαδρομής. Αυτό μπορεί να είναι χρήσιμο για τον υπολογισμό
         * κόστους
         * της διαδρομής κτλ.
         */
    }

    // Προσθήκη σημείου στο τέλος της διαδρομής
    public void addStep(String location) {
        if (start == null) {
            start = new RouteNode(location);
        } else {
            RouteNode current = start;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new RouteNode(location);
        }
    }

    // Εκτύπωση της διαδρομής
    public void printRoute() {
        if (start == null) {
            System.out.println("Route is empty.");
            return;
        }

        RouteNode current = start;
        System.out.print("Route: ");
        while (current != null) {
            System.out.print(current.location);
            if (current.next != null)
                System.out.print(" → ");
            current = current.next;
        }
        System.out.println();
    }

    // Αφαίρεση σημείου από τη διαδρομή (π.χ. λόγω εμποδίου)
    public void avoidStep(String location) {
        if (start == null)
            return;

        if (start.location.equals(location)) {
            start = start.next;
            return;
        }

        RouteNode prev = start;
        RouteNode current = start.next;

        while (current != null) {
            if (current.location.equals(location)) {
                if (this.verbose) {
                    insertBefore(current.next.location, prev.location);
                } else {
                    prev.next = current.next;
                }
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    // Παρεμβολή σημείου πριν από συγκεκριμένο άλλο σημείο
    public void insertBefore(String target, String newStep) {
        if (start == null)
            return;

        if (start.location.equals(target)) {
            RouteNode newNode = new RouteNode(newStep);
            newNode.next = start;
            start = newNode;
            return;
        }

        RouteNode prev = start;
        RouteNode current = start.next;

        while (current != null) {
            if (current.location.equals(target)) {
                RouteNode newNode = new RouteNode(newStep);
                newNode.next = current;
                prev.next = newNode;
                return;
            }
            prev = current;
            current = current.next;
        }
    }
}
