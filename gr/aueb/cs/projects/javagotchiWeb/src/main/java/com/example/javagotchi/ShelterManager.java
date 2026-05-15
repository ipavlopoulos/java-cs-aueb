package com.example.javagotchi;

import java.util.*;
import java.util.concurrent.*;

/**
 * Διαχειριστής Καταφυγίων (ShelterManager).
 * Αυτή η κλάση λειτουργεί ως το κεντρικό σημείο ελέγχου για όλα τα κατοικίδια
 * και τα καταφύγια των χρηστών στην εφαρμογή.
 */
public class ShelterManager {

    /**
     * Η στατική μεταβλητή instance διασφαλίζει την εφαρμογή του Singleton Pattern,
     * επιτρέποντας την ύπαρξη μόνο ενός διαχειριστή σε όλη τη διάρκεια λειτουργίας του server.
     */
    private static ShelterManager instance = null;

    /**
     * Χρήση ConcurrentHashMap για την αποθήκευση των καταφυγίων (Username -> Pets[]).
     * Επιλέχθηκε αυτή η δομή για να διασφαλιστεί η ακεραιότητα των δεδομένων κατά την
     * ταυτόχρονη πρόσβαση πολλαπλών χρηστών.
     */
    private final Map<String, Pet[]> allShelters = new ConcurrentHashMap<>();

    /**
     * Ιδιωτικός κατασκευαστής (Private Constructor).
     * Αποτρέπει την απευθείας δημιουργία αντικειμένων από άλλες κλάσεις,
     * επιβάλλοντας τη χρήση της μεθόδου getInstance().
     */
    private ShelterManager() {
        startGlobalTimer();
    }

    /**
     * Επιστρέφει το μοναδικό αντικείμενο ShelterManager.
     * Η μέθοδος είναι συγχρονισμένη (synchronized) για να αποφευχθούν
     * προβλήματα ταυτόχρονης δημιουργίας σε multithread περιβάλλον.
     *
     * @return Το κεντρικό instance του ShelterManager.
     */
    public static synchronized ShelterManager getInstance() {
        if (instance == null) {
            instance = new ShelterManager();
        }
        return instance;
    }

    /**
     * Ανακτά το καταφύγιο ενός χρήστη ή δημιουργεί ένα νέο αν αυτό δεν υπάρχει.
     *
     * @param username Το όνομα του χρήστη.
     * @param cages Ο αριθμός των θέσεων (κλουβιών) στο καταφύγιο.
     * @return Ένας πίνακας με τα κατοικίδια του χρήστη.
     */
    public Pet[] getOrCreateShelter(String username, int cages) {
        return allShelters.computeIfAbsent(username, k -> new Pet[cages]);
    }

    /**
     * Ανακτά το υπάρχον καταφύγιο ενός χρήστη.
     *
     * @param username Το όνομα του χρήστη.
     * @return Ο πίνακας με τα κατοικίδια ή null αν ο χρήστης δεν βρεθεί.
     */
    public Pet[] getShelter(String username) {
        return allShelters.get(username);
    }

    /**
     * Ελέγχει αν ένας χρήστης έχει ήδη δημιουργήσει καταφύγιο.
     *
     * @param username Το όνομα του χρήστη προς έλεγχο.
     * @return True αν ο χρήστης υπάρχει, αλλιώς false.
     */
    public boolean userExists(String username) {
        return allShelters.containsKey(username);
    }

    /**
     * Εκκινεί έναν κεντρικό χρονοπρογραμματιστή (Global Timer).
     * Αυτή η μέθοδος υλοποιεί την αυτόματη μείωση των στατιστικών όλων των κατοικιδίων
     * στο παρασκήνιο, ανεξάρτητα από τη δραστηριότητα των χρηστών.
     */
    private void startGlobalTimer() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Ο χρονοπρογραμματιστής εκτελείται περιοδικά κάθε 5 ώρες.
        // Επιλέχθηκε σταθερός ρυθμός εκτέλεσης για να διατηρείται η συνέπεια του παιχνιδιού.
        scheduler.scheduleAtFixedRate(() -> {
            for (Pet[] shelter : allShelters.values()) {
                for (int i = 0; i < shelter.length; i++) {
                    if (shelter[i] != null) {
                        shelter[i].tick();
                    }
                }
            }
        }, 5, 5, TimeUnit.HOURS);
    }
}