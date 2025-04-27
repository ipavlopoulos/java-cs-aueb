package demo.iterators;

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
                (a, b) -> Integer.compare(b, a)
        );

        pq.offer(42);
        pq.offer(7);
        pq.offer(99);

        // Εσωτερικά: [99, 42, 7]
        System.out.println(pq.poll()); // ➤ 99 (το μεγαλύτερο βγαίνει πρώτο)

    }
}
