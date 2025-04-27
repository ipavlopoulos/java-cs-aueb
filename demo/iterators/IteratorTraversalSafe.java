package demo.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorTraversalSafe {

    public static void main(String[] args) {
        List<String> names = new ArrayList<>(List.of("Anna", "Alex", "Bob", "Amy"));

        Iterator<String> it = names.iterator();
        while (it.hasNext()) {
            String name = it.next();
            if (name.startsWith("A")) {
                it.remove(); // ✅ safe delete
            }
        }

        System.out.println(names); // ➤ Only Bob is printed
    }
}
