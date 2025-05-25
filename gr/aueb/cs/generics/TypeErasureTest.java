package gr.aueb.cs.generics;

import java.util.ArrayList;
import java.util.List;

public class TypeErasureTest {
    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        System.out.println(a.getClass() == b.getClass()); // true
    }
}
