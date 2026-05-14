package com.example.javagotchi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // Δηλώνει ότι αυτή η κλάση διαχειρίζεται σελίδες Web
public class JavagotchiController {

    //Εμφάνιση της αρχικής σελίδας (Login)
    @GetMapping("/")
    public String index() {
        return "index"; // Ψάχνει το αρχείο index.html στα templates
    }

    //Διαδικασία εισόδου χρήστη
    @PostMapping("/login")
    public String login(@RequestParam String username) {
        if (ShelterManager.getInstance().userExists(username)) {
            // Αν είναι παλιός, πάει κατευθείαν στο παιχνίδι
            return "redirect:/dashboard/" + username;
        } else {
            // Αν είναι νέος, πάει στη σελίδα ρυθμίσεων
            return "redirect:/setup/" + username;
        }
    }

    //Η σελίδα που ρωτάει για τα κλουβιά τους νέους χρήστες
    @GetMapping("/setup/{username}")
    public String setupPage(@PathVariable String username, Model model) {
        if (ShelterManager.getInstance().userExists(username)) {
            return "redirect:/dashboard/" + username; // Αν υπάρχει ήδη, γυρνάει στο dashboard
        }
        model.addAttribute("username", username);
        return "setup";
    }

    //Αποθήκευση της επιλογής κλουβιών
    @PostMapping("/setup/{username}")
    public String setupShelter(@PathVariable String username, @RequestParam int cages) {
        ShelterManager.getInstance().getOrCreateShelter(username, cages);
        return "redirect:/dashboard/" + username;
    }

    //Εμφάνιση του καταφυγίου του χρήστη
    @GetMapping("/dashboard/{username}")
    public String dashboard(@PathVariable String username, Model model) {
        Pet[] shelter = ShelterManager.getInstance().getShelter(username);

        if (shelter == null) return "redirect:/"; // Αν ο χρήστης δεν υπάρχει, γυρνάμε στην αρχή

        model.addAttribute("username", username); // Περνάμε το όνομα στην HTML
        model.addAttribute("shelter", shelter);   // Περνάμε τον πίνακα με τα pets στην HTML
        return "dashboard"; // Εμφανίζει το dashboard.html
    }

    //Διαχείριση όλων των ενεργειών (adopt, feed, play)
    @PostMapping("/action/{username}")
    public String action(@PathVariable String username,
                         @RequestParam String act,
                         @RequestParam(required = false) String type,
                         @RequestParam(required = false) String name,
                         @RequestParam(required = false) Integer cageId) {

        Pet[] shelter = ShelterManager.getInstance().getShelter(username);
        if (shelter == null) return "redirect:/";

        if (act.equals("adopt")) {
            // Ψάχνει το πρώτο άδειο κλουβί για το νέο pet
            for (int i = 0; i < shelter.length; i++) {
                if (shelter[i] == null) {
                    if ("Dragon".equals(type)) shelter[i] = new DragonPet(name);
                    else if ("Slime".equals(type)) shelter[i] = new SlimePet(name);
                    else shelter[i] = new Pet(name);
                    break;
                }
            }
        } else if (cageId != null && shelter[cageId] != null) {
            // Εκτελεί την ενέργεια στο συγκεκριμένο κλουβί
            if ("feed".equals(act)) {
                shelter[cageId].feed();
            } else if ("play".equals(act)) {
                shelter[cageId].play();
            } else if ("clean".equals(act) && shelter[cageId].getHunger() <= 0) {
                // Αν πατήσει clean ΚΑΙ το ζωάκι είναι νεκρό, αδειάζει το κλουβί
                shelter[cageId] = null;
            }
        }

        // Aνανέωση της σελίδα του dashboard μετά την ενέργεια
        return "redirect:/dashboard/" + username;
    }
}
