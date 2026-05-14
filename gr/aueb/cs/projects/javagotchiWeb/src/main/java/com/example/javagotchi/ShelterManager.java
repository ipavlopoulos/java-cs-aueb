package com.example.javagotchi;

import java.util.*;
import java.util.concurrent.*;

public class ShelterManager {
    // Το 'instance' εξασφαλίζει ότι θα έχουμε μόνο ΕΝΑΝ Manager για όλη την εφαρμογή
    private static ShelterManager instance = null;

    // Εδώ αποθηκεύονται τα καταφύγια: Username -> Πίνακας με Pets
    private final Map<String, Pet[]> allShelters = new ConcurrentHashMap<>();

    private ShelterManager() {
        startGlobalTimer(); // Ξεκινάει το ρολόι μόλις δημιουργηθεί ο Manager
    }

    // Μέθοδος για να παίρνουμε τον Manager από οποιοδήποτε σημείο του κώδικα
    public static synchronized ShelterManager getInstance() {
        if (instance == null) instance = new ShelterManager();
        return instance;
    }

    // Αν ο χρήστης δεν έχει καταφύγιο, του φτιάχνει ένα νέο. Αν έχει, του δίνει το υπάρχον.
    public Pet[] getOrCreateShelter(String username, int cages) {
        return allShelters.computeIfAbsent(username, k -> new Pet[cages]);
    }

    public Pet[] getShelter(String username) {
        return allShelters.get(username);
    }

    //Ρωτάει ο Controller αν υπάρχει ο χρήστης
    public boolean userExists(String username) {
        return allShelters.containsKey(username);
    }

    // Αυτό είναι το "ρολόι" που τρέχει κάθε 5 ΩΡΕΣ για ΟΛΟΥΣ τους χρήστες
    private void startGlobalTimer() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            for (Pet[] shelter : allShelters.values()) {
                for (int i = 0; i < shelter.length; i++) {
                    if (shelter[i] != null) {
                        shelter[i].tick(); // Καλεί τη μέθοδο tick του κάθε Pet
                    }
                }
            }
        }, 5, 5, TimeUnit.HOURS);
    }
}
