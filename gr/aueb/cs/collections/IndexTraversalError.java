package gr.aueb.cs.collections;

import java.util.*;

public class IndexTraversalError {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(List.of("Anna", "Alex", "Bob", "Amy"));

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).startsWith("A")) {
                names.remove(i);  // ❌ updating the list while accessing it
            }
        }
        // "Anna" is deleted at 1st iteration
        // "Alex" moves to index 0
        // i --> 1, and "Alex" is never accessed

        System.out.println(names);

    }
}
