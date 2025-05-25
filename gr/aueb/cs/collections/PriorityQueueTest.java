package gr.aueb.cs.collections;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueTest {

    public static void main(String[] args) {

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        pq.offer(42);
        pq.offer(7);
        pq.offer(99);

        // Εσωτερικά ταξινομείται ως: [7, 42, 99]
        System.out.println(pq.poll());

        // Αντιστροφή προτεραιότητας
        pq = new PriorityQueue<>(
                new Comparator<>() {
                    @Override
                    public int compare(Integer a, Integer b) {
                        return Integer.compare(b, a);
                    }
                }
                // can be replaced by this lambda (Java 8+)
                //(a, b) -> Integer.compare(b, a)
        );

        pq.offer(42);
        pq.offer(7);
        pq.offer(99);

        // Εσωτερικά: [99, 42, 7]
        System.out.println(pq.poll()); // ➤ 99 (το μεγαλύτερο βγαίνει πρώτο)

    }
}
