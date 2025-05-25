package gr.aueb.cs.generics;

import java.util.*;

// Κλάση Student
class Student {
    private String name;
    private int id;
    private double grade;

    public Student(String name, int id, double grade) {
        this.name = name;
        this.id = id;
        this.grade = grade;
    }

    public String getName() { return name; }
    public int getId() { return id; }
    public double getGrade() { return grade; }

    @Override
    public String toString() {
        return "AM: " + id + ", Όνομα: " + name + ", Βαθμός: " + grade;
    }
}

// Comparator για ταξινόμηση κατά βαθμό (φθίνουσα)
class GradeComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return Double.compare(s2.getGrade(), s1.getGrade());
    }
}

// Comparator για ταξινόμηση κατά όνομα (αλφαβητικά)
class NameComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.getName().compareTo(s2.getName());
    }
}

// Comparator για ταξινόμηση κατά AM (αύξουσα)
class IdComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return Integer.compare(s1.getId(), s2.getId());
    }
}

// Main
public class StudentSortingComparatorDemo {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Άννα Παπαδοπούλου", 2023001, 8.5));
        students.add(new Student("Βασίλης Κωνσταντίνου", 2023015, 7.3));
        students.add(new Student("Γιάννης Ρουμελιώτης", 2023003, 9.1));
        students.add(new Student("Ελένη Ζαφειράτου", 2023008, 8.5));
        students.add(new Student("Δημήτρης Τσάκωνας", 2023002, 6.9));

        System.out.println("=== Αρχική λίστα ===");
        printList(students);

        System.out.println("\n=== Ταξινόμηση κατά βαθμό ===");
        Collections.sort(students, new GradeComparator());
        printList(students);

        System.out.println("\n=== Ταξινόμηση κατά όνομα ===");
        Collections.sort(students, new NameComparator());
        printList(students);

        System.out.println("\n=== Ταξινόμηση κατά AM ===");
        Collections.sort(students, new IdComparator());
        printList(students);
    }

    public static void printList(List<Student> list) {
        for (Student s : list) {
            System.out.println(s);
        }
    }
}

