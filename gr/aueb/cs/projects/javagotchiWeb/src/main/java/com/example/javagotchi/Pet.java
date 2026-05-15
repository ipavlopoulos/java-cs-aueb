package com.example.javagotchi;

/**
 * Αντιπροσωπεύει ένα βασικό εικονικό κατοικίδιο στην εφαρμογή Javagotchi.
 * Αυτή η κλάση λειτουργεί ως βασική οντότητα, διατηρώντας βασικά
 * χαρακτηριστικά όπως το όνομα, η πείνα και η ευτυχία.
 */
public class Pet {
    protected String name;
    protected int hunger;
    protected int happiness;
    private static int DEFAULT_HUNGER = 6;
    private static int DEFAULT_HAPPINESS = 9;

    /**
     * Δημιουργεί ένα νέο Pet με το καθορισμένο όνομα και τα προεπιλεγμένα αρχικά στατιστικά.
     *
     * @param name Το όνομα που δίνεται στο κατοικίδιο.
     */
    // Η ορατότητα (visibility) επιπέδου package-private είναι σκόπιμη εδώ.
    // Αποτρέπει την άμεση δημιουργία αντικειμένων Pet από κλάσεις εκτός αυτού του πακέτου,
    // ενθαρρύνοντας τη χρήση συγκεκριμένων υποκλάσεων ή τη διαχείριση μέσω του Singleton ShelterManager.
    Pet(String name){
        this.name = name;
        this.hunger = DEFAULT_HUNGER;
        this.happiness = DEFAULT_HAPPINESS;
    }

    /**
     * Επιστρέφει το όνομα του κατοικιδίου.
     *
     * @return Το όνομα του κατοικιδίου.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Επιστρέφει το τρέχον επίπεδο πείνας του κατοικιδίου.
     *
     * @return Το επίπεδο πείνας (0-10).
     */
    public int getHunger(){
        return this.hunger;
    }

    /**
     * Επιστρέφει το τρέχον επίπεδο ευτυχίας του κατοικιδίου.
     *
     * @return Το επίπεδο ευτυχίας (0-10).
     */
    public int getHappiness(){
        return this.happiness;
    }

    /**
     * Ταΐζει το κατοικίδιο, αυξάνοντας το επίπεδο κορεσμού του (hunger) μέχρι το μέγιστο όριο του 10.
     */
    public void feed() {
        if(this.hunger < 10){
            this.hunger += 1;
        }
    }

    /**
     * Παίζει με το κατοικίδιο, αυξάνοντας το επίπεδο ευτυχίας του (happiness) μέχρι το μέγιστο όριο του 10.
     */
    public void play() {
        if(this.happiness < 10){
            this.happiness += 1;
        }
    }

    /**
     * Προσομοιώνει το πέρασμα του χρόνου για το κατοικίδιο.
     * Μειώνει τα επίπεδα πείνας και ευτυχίας κατά 1.
     */
    public void tick(){
        this.hunger -= 1;
        this.happiness -= 1;
    }

    /**
     * Παράγει ένα μορφοποιημένο string (αλφαριθμητικό) που περιέχει την τρέχουσα κατάσταση του κατοικιδίου.
     *
     * @return Μια αναπαράσταση πολλαπλών γραμμών με το όνομα, την πείνα και την ευτυχία του κατοικιδίου.
     */
    public String checkStatus(){
        return "Όνομα κατοικιδίου: " + this.name + "\nHunger: " + this.hunger + "\nHappiness: " + this.happiness;
    }
}