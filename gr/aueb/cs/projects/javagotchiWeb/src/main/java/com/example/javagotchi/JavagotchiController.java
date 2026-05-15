package com.example.javagotchi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Ο κύριος ελεγκτής (Controller) της εφαρμογής Javagotchi.
 * Διαχειρίζεται όλα τα HTTP αιτήματα (requests) για την πλοήγηση των χρηστών,
 * τη διαχείριση συνεδριών (μέσω του username στα URL) και τις αλληλεπιδράσεις
 * με το καταφύγιο (υιοθεσία, τάισμα, παιχνίδι).
 */
@Controller
public class JavagotchiController {

    /**
     * Διαχειρίζεται το αρχικό αίτημα στην κεντρική σελίδα της εφαρμογής.
     *
     * @return Το όνομα του προτύπου (template) για τη σελίδα σύνδεσης.
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Επεξεργάζεται την υποβολή της φόρμας εισόδου (login).
     * Ελέγχει την ύπαρξη του χρήστη στο σύστημα ώστε να δρομολογήσει την κίνηση
     * είτε στη σελίδα δημιουργίας νέου καταφυγίου, είτε απευθείας στο παιχνίδι.
     *
     * @param username Το όνομα χρήστη που υποβλήθηκε.
     * @return Ένα HTTP redirect προς το κατάλληλο endpoint.
     */
    @PostMapping("/login")
    public String login(@RequestParam String username) {
        if (ShelterManager.getInstance().userExists(username)) {
            return "redirect:/dashboard/" + username;
        } else {
            return "redirect:/setup/" + username;
        }
    }

    /**
     * Εμφανίζει τη σελίδα αρχικής ρύθμισης για νέους χρήστες.
     * Περιλαμβάνει δικλείδα ασφαλείας που ανακατευθύνει τους ήδη εγγεγραμμένους χρήστες
     * πίσω στο dashboard τους, αποτρέποντας την τυχαία επανεγγραφή των δεδομένων τους.
     *
     * @param username Το όνομα του χρήστη.
     * @param model Το αντικείμενο Model του Spring για τη μεταφορά δεδομένων στο view.
     * @return Το πρότυπο ρύθμισης ή ένα redirect στο dashboard.
     */
    @GetMapping("/setup/{username}")
    public String setupPage(@PathVariable String username, Model model) {
        if (ShelterManager.getInstance().userExists(username)) {
            return "redirect:/dashboard/" + username;
        }
        model.addAttribute("username", username);
        return "setup";
    }

    /**
     * Επεξεργάζεται τα αρχικά δεδομένα ρύθμισης και δεσμεύει χώρο για το νέο καταφύγιο.
     *
     * @param username Το όνομα του χρήστη.
     * @param cages Ο αριθμός των κλουβιών που επέλεξε ο χρήστης.
     * @return Ένα HTTP redirect προς το dashboard.
     */
    @PostMapping("/setup/{username}")
    public String setupShelter(@PathVariable String username, @RequestParam int cages) {
        ShelterManager.getInstance().getOrCreateShelter(username, cages);
        return "redirect:/dashboard/" + username;
    }

    /**
     * Προβάλλει το κύριο περιβάλλον (dashboard) του καταφυγίου, τροφοδοτώντας την HTML
     * με τα τρέχοντα δεδομένα των κατοικιδίων.
     *
     * @param username Το όνομα του χρήστη.
     * @param model Το αντικείμενο Model του Spring.
     * @return Το πρότυπο του dashboard ή redirect στην αρχική αν ο χρήστης είναι άκυρος.
     */
    @GetMapping("/dashboard/{username}")
    public String dashboard(@PathVariable String username, Model model) {
        Pet[] shelter = ShelterManager.getInstance().getShelter(username);

        if (shelter == null) return "redirect:/";

        model.addAttribute("username", username);
        model.addAttribute("shelter", shelter);
        return "dashboard";
    }

    /**
     * Διαχειρίζεται όλες τις αλληλεπιδράσεις του χρήστη με το καταφύγιό του.
     * Επιλέχθηκε ένας ενιαίος (unified) χειριστής (POST endpoint) για όλες τις ενέργειες
     * προκειμένου να διατηρηθεί η λογική των φορμών απλή στην πλευρά του Thymeleaf.
     *
     * @param username Το όνομα του χρήστη.
     * @param act Η ενέργεια που ζητήθηκε (adopt, feed, play, clean).
     * @param type (Προαιρετικό) Το είδος του κατοικιδίου προς υιοθεσία.
     * @param name (Προαιρετικό) Το όνομα του νέου κατοικιδίου.
     * @param cageId (Προαιρετικό) Ο δείκτης (index) του κλουβιού στο οποίο εφαρμόζεται η ενέργεια.
     * @return Ένα HTTP redirect πίσω στο dashboard για την άμεση οπτική ανανέωση της σελίδας.
     */
    @PostMapping("/action/{username}")
    public String action(@PathVariable String username,
                         @RequestParam String act,
                         @RequestParam(required = false) String type,
                         @RequestParam(required = false) String name,
                         @RequestParam(required = false) Integer cageId) {

        Pet[] shelter = ShelterManager.getInstance().getShelter(username);
        if (shelter == null) return "redirect:/";

        if (act.equalsIgnoreCase(ActionType.ADOPT.name())) {
            // Διασχίζουμε τον πίνακα για να βρούμε την πρώτη μηδενική (null) θέση μνήμης.
            // Αυτό διασφαλίζει ότι δεν θα αντικατασταθεί κατά λάθος ένα ήδη υπάρχον κατοικίδιο.
            for (int i = 0; i < shelter.length; i++) {
                if (shelter[i] == null) {
                    if (type.equalsIgnoreCase(PetType.DRAGON.name())) {
                        shelter[i] = new DragonPet(name);
                    } else if (type.equalsIgnoreCase(PetType.SLIME.name())) {
                        shelter[i] = new SlimePet(name);
                    } else {
                        shelter[i] = new Pet(name);
                    }
                    break;
                }
            }
        } else if (cageId != null && shelter[cageId] != null) {
            if (act.equalsIgnoreCase(ActionType.FEED.name())) {
                shelter[cageId].feed();
            } else if (act.equalsIgnoreCase(ActionType.PLAY.name())) {
                shelter[cageId].play();
            } else if (act.equalsIgnoreCase(ActionType.CLEAN.name()) && shelter[cageId].getHunger() <= 0) {
                // Η ενέργεια 'clean' επιτρέπεται αυστηρά μόνο εάν το κατοικίδιο έχει πεθάνει
                // (hunger <= 0), αποτρέποντας τη διαγραφή ζωντανών κατοικιδίων από τον πίνακα.
                shelter[cageId] = null;
            }
        }

        return "redirect:/dashboard/" + username;
    }
}