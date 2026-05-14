package com.example.javagotchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Αυτή η σημείωση λέει στη Java ότι αυτό είναι ένα project Spring Boot
@SpringBootApplication
public class JavagotchiApplication {
    public static void main(String[] args) {
        // Αυτή η γραμμή ξεκινάει αυτόματα τον εσωτερικό server (Tomcat)
        SpringApplication.run(JavagotchiApplication.class, args);
    }
}
