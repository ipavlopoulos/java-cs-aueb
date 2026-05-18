📄 Τεχνική Τεκμηρίωση Javagotchi Mod
1. Javagotchi.java (The Registry)
Είναι ο "ληξίαρχος" του mod. Χρησιμοποιεί τον IEventBus για να δηλώσει τα πάντα στο παιχνίδι.
•	Entity Registration: Ορίζει το "pet" με μέγεθος 0.6x1.0 (ύψος/πλάτος).
•	Items: Δημιουργεί αυτόματα ένα Spawn Egg με χρώματα Λευκό/Πράσινο.
•	Creative Tab: Φτιάχνει μια δική του καρτέλα στο inventory του Creative mode με το όνομα του mod, ώστε να βρίσκεις το αυγό εύκολα.
2. PetEntity.java (The Logic)
Εδώ χτυπάει η καρδιά του AI. Το ζωάκι σου δεν είναι απλό entity, είναι ένα Data-Driven Pet.
•	Synched Data: Χρησιμοποιεί EntityDataAccessor (Hunger, Happiness, Energy). Αυτό σημαίνει ότι οι τιμές συγχρονίζονται αυτόματα μεταξύ Server και Client (για να τις βλέπεις στο HUD).
•	Interactions: * 🍎 Apple: Hunger +3 και Heal.
o	🥕 Carrot: Energy +4.
o	Stick: Happiness +2 αλλά Energy -1 (το παιχνίδι το κουράζει!).
•	AI Logic: Αν η πείνα ή η ευτυχία φτάσουν στο 0, το entity κάνει discard() (πεθαίνει/εξαφανίζεται). Αν το Energy φτάσει στο 0, η ταχύτητά του μηδενίζεται (εξάντληση).
3. ClientModEvents.java (The Visuals)
Αυτό το αρχείο περιέχει όλη τη "μαγεία" της εμφάνισης.
•	PetModel: Ένα custom 3D μοντέλο με κεφάλι, αυτιά, σώμα και 4 πόδια.
•	Animations: Στη μέθοδο setupAnim, τα πόδια κινούνται με βάση τη συνάρτηση cos (ημίτονο), δημιουργώντας μια φυσική κίνηση περπατήματος.
•	Render Layer: Καταχωρεί το HUD Overlay και το Renderer, συνδέοντας τον κώδικα με τα γραφικά.
4. PetHudOverlay.java (The UI)
Το interface που βλέπει ο παίκτης στην οθόνη του.
•	Detection: Χρησιμοποιεί ένα AABB (Bounding Box) για να "ψάξει" αν υπάρχει κάποιο PetEntity σε ακτίνα 3 blocks από τον παίκτη.
•	Targeting: Αν υπάρχουν πολλά, επιλέγει αυτό που είναι πιο κοντά στον παίκτη (min(Comparator)).
•	Rendering: Σχεδιάζει κείμενο απευθείας στην οθόνη (Centered String) δείχνοντας τα στατιστικά σε πραγματικό χρόνο με διαφορετικά χρώματα (Hex codes).
