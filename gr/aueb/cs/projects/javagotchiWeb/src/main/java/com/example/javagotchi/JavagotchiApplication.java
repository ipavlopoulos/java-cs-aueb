package com.example.javagotchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Η κύρια κλάση εκκίνησης (Entry Point) της εφαρμογής Javagotchi.
 * Αξιοποιεί την αρχιτεκτονική του Spring Boot για την αυτόματη ρύθμιση (auto-configuration)
 * και την ανάπτυξη (deployment) της εφαρμογής σε έναν ενσωματωμένο web server (Tomcat).
 */
@SpringBootApplication
public class JavagotchiApplication {

    /**
     * Η κύρια μέθοδος (main) που εκτελείται κατά την εκκίνηση του προγράμματος.
     * Αναθέτει τον έλεγχο της ροής στο SpringApplication, το οποίο αναλαμβάνει
     * την αρχικοποίηση του Application Context και την εκκίνηση του server.
     *
     * @param args Ορίσματα γραμμής εντολών που μπορεί να περαστούν κατά την εκτέλεση.
     */
    public static void main(String[] args) {
        SpringApplication.run(JavagotchiApplication.class, args);
    }
}
