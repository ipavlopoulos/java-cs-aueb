package gr.aueb.cs.examples.graphics;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;

public class JavaTagCloud {

    public static void main(String[] args) {
        // Καθορισμός τελικών διαστάσεων της εικόνας (πλάτος x ύψος σε pixels)
        int width = 1200;
        int height = 800;

        // 1. Δημιουργία χάρτη (Map) με τις κύριες έννοιες της Java και τη βαρύτητά τους (weight)
        // Η βαρύτητα καθορίζει το μέγεθος της γραμματοσειράς και την προτεραιότητα τοποθέτησης.
        Map<String, Integer> coreConcepts = new LinkedHashMap<>();
        coreConcepts.put("OBJECT-ORIENTED PROGRAMMING", 100);
        coreConcepts.put("POLYMORPHISM", 95);
        coreConcepts.put("INHERITANCE", 90);
        coreConcepts.put("ENCAPSULATION", 85);
        coreConcepts.put("ABSTRACTION", 85);
        coreConcepts.put("JAVA", 80);
        coreConcepts.put("CLASSES", 70);
        coreConcepts.put("OBJECTS", 70);
        coreConcepts.put("INTERFACES", 65);
        coreConcepts.put("ABSTRACT CLASSES", 60);
        coreConcepts.put("METHODS", 55);
        coreConcepts.put("CONSTRUCTORS", 50);
        coreConcepts.put("OVERRIDING", 50);
        coreConcepts.put("OVERLOADING", 45);
        coreConcepts.put("GARBAGE COLLECTION", 45);
        coreConcepts.put("JVM", 40);
        coreConcepts.put("JDK", 40);
        coreConcepts.put("JRE", 35);
        coreConcepts.put("STRINGS", 35);
        coreConcepts.put("COLLECTIONS FRAMEWORK", 35);
        coreConcepts.put("EXCEPTION HANDLING", 30);
        coreConcepts.put("GENERICS", 30);
        coreConcepts.put("THREADS", 28);
        coreConcepts.put("STREAMS API", 28);
        coreConcepts.put("LAMBDA EXPRESSIONS", 25);
        coreConcepts.put("ACCESS MODIFIERS", 22);
        coreConcepts.put("DATA TYPES", 20);
        coreConcepts.put("MULTITHREADING", 20);
        coreConcepts.put("SERIALIZATION", 18);
        coreConcepts.put("REFLECTION", 15);
        coreConcepts.put("PACKAGES", 15);

        // Πίνακας με μικρότερες λέξεις-κλειδιά (fillers) για να γεμίσουν τα κενά ανάμεσα στις μεγάλες λέξεις
        String[] fillerTerms = {
                "this", "super", "final", "static", "public", "private", "protected", "void", "new",
                "class", "enum", "null", "try", "catch", "throw", "List", "Map", "Set", "int", "double"
        };

        // Μετατροπή των κύριων εννοιών σε μια λίστα αντικειμένων CloudWord για επεξεργασία
        List<CloudWord> wordList = new ArrayList<>();
        coreConcepts.forEach((word, weight) -> wordList.add(new CloudWord(word, weight)));

        // Αρχικοποίηση γεννήτριας τυχαίων αριθμών με συγκεκριμένο seed (101) για σταθερό/ισορροπημένο layout
        Random rand = new Random(101);

        // Εισαγωγή πολλαπλών αντιγράφων (duplicates) από τις λέξεις-fillers για αύξηση της πυκνότητας
        for (String filler : fillerTerms) {
            // Κάθε λέξη-filler θα εμφανιστεί τυχαία από 4 έως 7 φορές
            int duplicatesCount = 4 + rand.nextInt(4);
            for (int i = 0; i < duplicatesCount; i++) {
                // Απόδοση μικρής τυχαίας βαρύτητας (10 έως 21) για να χωράνε σε μικρά κενά
                int randomWeight = 10 + rand.nextInt(12);
                wordList.add(new CloudWord(filler, randomWeight));
            }
        }

        // Ταξινόμηση της λίστας σε φθίνουσα σειρά με βάση το βάρος (weight)
        // Είναι κρίσιμο οι μεγάλες λέξεις να τοποθετηθούν πρώτες στο κέντρο, αλλιώς δεν θα βρουν χώρο μετά.
        wordList.sort((w1, w2) -> Integer.compare(w2.weight, w1.weight));

        // 2. Προετοιμασία της εικόνας (Buffer) και των ρυθμίσεων σχεδίασης
        BufferedImage cloudImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = cloudImage.createGraphics();

        // Ενεργοποίηση Anti-aliasing για απόλυτα ομαλή και καθαρή σχεδίαση των γραμματοσειρών
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Δημιουργία καθαρού λευκού υπόβαθρου (καμβά)
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Χρωματική παλέτα (Μπλε, Teal, Πορτοκαλί, Κόκκινο, Μωβ) εμπνευσμένη από το οικοσύστημα της Java
        Color[] palette = {
                new Color(0x00597A), new Color(0x143F6B), new Color(0xF55353),
                new Color(0xFEB139), new Color(0x4A1C40), new Color(0x2E8B57), new Color(0x5F9EA0)
        };

        // Πίνακας pixel (Boolean Mask) για να ελέγχουμε ποια σημεία της εικόνας είναι ήδη κατειλημμένα από κείμενο
        boolean[][] occupiedPixels = new boolean[width][height];

        // Υπολογισμός των κεντρικών συντεταγμένων του καμβά
        double centerX = width / 2.0;
        double centerY = height / 2.0;

        // 3. Κύριος αλγόριθμος τοποθέτησης των λέξεων σε σπειροειδή διάταξη (Spiral Packing)
        for (CloudWord cloudWord : wordList) {
            String word = cloudWord.text;
            int weight = cloudWord.weight;

            // Υπολογισμός μεγέθους γραμματοσειράς βάσει του βάρους της λέξης (Μέγιστο: 65, Ελάχιστο: 11)
            int fontSize = (int) (11 + (weight / 100.0) * 54);
            // Οι μεγάλες λέξεις γίνονται Bold (έντονες), οι μικρές μένουν Plain (απλές)
            Font font = new Font("SansSerif", weight > 40 ? Font.BOLD : Font.PLAIN, fontSize);
            g2d.setFont(font);

            // Μέτρηση των ακριβών διαστάσεων (πλάτος, ύψος) που θα πιάσει η λέξη στην οθόνη
            FontMetrics fm = g2d.getFontMetrics();
            int wordWidth = fm.stringWidth(word);
            int wordHeight = fm.getHeight();

            // Αποφυγή σφαλμάτων σε περίπτωση που μια λέξη έχει μηδενικές διαστάσεις
            if (wordWidth == 0 || wordHeight == 0) continue;

            // Απόφαση προσανατολισμού (50% πιθανότητα να είναι Οριζόντια [0°] ή Κατακόρυφη [90°])
            double rotationAngle = (rand.nextBoolean()) ? 0 : Math.PI / 2;

            // Υπολογισμός των "πραγματικών" διαστάσεων που καταλαμβάνει το πλαίσιο της λέξης μετά την περιστροφή
            int effectiveWidth = (int) (wordWidth * Math.abs(Math.cos(rotationAngle)) + wordHeight * Math.abs(Math.sin(rotationAngle)));
            int effectiveHeight = (int) (wordWidth * Math.abs(Math.sin(rotationAngle)) + wordHeight * Math.abs(Math.cos(rotationAngle)));

            boolean placed = false;         // Σημαία που δείχνει αν η λέξη τοποθετήθηκε επιτυχώς
            double radius = 0.0;            // Η ακτίνα της σπείρας (ξεκινάει από το κέντρο = 0)
            double currentAngle = rand.nextDouble() * 2 * Math.PI; // Τυχαία γωνία εκκίνησης στη σπείρα

            // Η σπείρα επεκτείνεται προς τα έξω μέχρι η λέξη να βρει ελεύθερο χώρο ή να βγούμε εκτός ορίων
            while (!placed && radius < Math.max(width, height)) {
                // Μετατροπή των πολικών συντεταγμένων (ακτίνα, γωνία) σε καρτεσιανές (X, Y) με κέντρο την οθόνη
                int testX = (int) (centerX + radius * Math.cos(currentAngle) - (effectiveWidth / 2.0));
                int testY = (int) (centerY + radius * Math.sin(currentAngle) - (effectiveHeight / 2.0));

                // Έλεγχος αν το πλαίσιο της λέξης χωράει μέσα στα όρια του καμβά (με ένα μικρό περιθώριο 10 pixels)
                if (testX > 10 && testX + effectiveWidth < width - 10 && testY > 10 && testY + effectiveHeight < height - 10) {

                    // Έλεγχος μέσω της μάσκας pixel αν ο συγκεκριμένος χώρος είναι ελεύθερος (δεν υπάρχει άλλη λέξη εκεί)
                    if (!isAreaOccupied(testX, testY, effectiveWidth, effectiveHeight, occupiedPixels)) {

                        // Επιλογή τυχαίου χρώματος από την παλέτα
                        g2d.setColor(palette[rand.nextInt(palette.length)]);

                        // Αποθήκευση της τρέχουσας κατάστασης του μετασχηματισμού (συντεταγμένων) των γραφικών
                        AffineTransform originalAt = g2d.getTransform();

                        // Μεταφορά του κέντρου σχεδίασης στο σημείο τοποθέτησης και εφαρμογή της περιστροφής
                        g2d.translate(testX + effectiveWidth / 2.0, testY + effectiveHeight / 2.0);
                        g2d.rotate(rotationAngle);

                        // Σχεδίαση της λέξης ευθυγραμμισμένης στο κέντρο του πλαισίου της
                        g2d.drawString(word, -wordWidth / 2, (wordHeight / 2) - fm.getDescent());

                        // Επαναφορά των γραφικών στην αρχική τους κατάσταση (επαναφορά περιστροφής)
                        g2d.setTransform(originalAt);

                        // Ενημέρωση της μάσκας pixel ώστε ο χώρος αυτός να κλειδωθεί για τις επόμενες λέξεις
                        markAreaOccupied(testX, testY, effectiveWidth, effectiveHeight, occupiedPixels);
                        placed = true; // Η λέξη τοποθετήθηκε, σπάει το loop
                    }
                }
                // Μικρά βήματα αύξησης της σπείρας για να πετύχουμε όσο το δυνατόν πιο πυκνή τοποθέτηση
                radius += 0.15;
                currentAngle += 0.08;
            }
        }
        g2d.dispose(); // Απελευθέρωση των πόρων του Graphics2D καμβά

        // 4. Εφαρμογή μαθηματικού Radial Gradient (Vignette) για το ομαλό σβήσιμο (fade) στις άκρες
        BufferedImage finalCloud = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // Καθορισμός της μέγιστης ακτίνας εξασθένισης
        double maxDistance = Math.sqrt(centerX * centerX + centerY * centerY) * 0.82;

        // Σάρωση ολόκληρης της εικόνας pixel προς pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = cloudImage.getRGB(x, y);

                // Αν το pixel είναι καθαρό λευκό (υπόβαθρο), το αφήνουμε ως έχει και προχωράμε
                if (rgb == Color.WHITE.getRGB()) {
                    finalCloud.setRGB(x, y, rgb);
                    continue;
                }

                // Υπολογισμός της Ευκλείδειας απόστασης του τρέχοντος pixel από το κέντρο της εικόνας
                double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));

                // Υπολογισμός του συντελεστή φωτεινότητας/διαφάνειας (1.0 στο κέντρο, πλησιάζει το 0.0 στις άκρες)
                double factor = 1.0 - (distance / maxDistance);
                // Χρήση εκθέτη (1.5) για πιο ομαλή και φυσική καμπύλη σβησίματος (drop-off rate)
                factor = Math.max(0.0, Math.min(1.0, Math.pow(factor, 1.5)));

                // Απομόνωση των χρωματικών καναλιών (Red, Green, Blue) του pixel
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Ανάμειξη (linear interpolation) του χρώματος του κειμένου με το λευκό χρώμα του φόντου βάσει του factor
                // Όσο πιο μακριά από το κέντρο, τόσο περισσότερο το χρώμα "σβήνει" προς το λευκό (255)
                int blendedRed = (int) (red * factor + 255 * (1 - factor));
                int blendedGreen = (int) (green * factor + 255 * (1 - factor));
                int blendedBlue = (int) (blue * factor + 255 * (1 - factor));

                // Ανασύνθεση των καναλιών σε ένα ενιαίο RGB integer (με πλήρη αδιαφάνεια 255 στο κανάλι Alpha)
                int packedRgb = (255 << 24) | (blendedRed << 16) | (blendedGreen << 8) | blendedBlue;
                finalCloud.setRGB(x, y, packedRgb);
            }
        }

        // 5. Αποθήκευση του τελικού αποτελέσματος σε αρχείο εικόνας PNG
        try {
            File output = new File("java_dense_tag_cloud.png");
            ImageIO.write(finalCloud, "png", output);
            System.out.println("Το πυκνό Tag Cloud δημιουργήθηκε επιτυχώς στο: " + output.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Σφάλμα κατά την αποθήκευση της εικόνας: " + e.getMessage());
        }
    }

    /**
     * Ελέγχει αν οποιοδήποτε pixel μέσα στο ζητούμενο παραλληλόγραμμο πλαίσιο είναι ήδη κατειλημμένο.
     * Προσθέτει επίσης ένα τεχνητό περιθώριο (padding) 1 pixel γύρω από τη λέξη για καθαρότερο οπτικό αποτέλεσμα.
     */
    private static boolean isAreaOccupied(int x, int y, int w, int h, boolean[][] mask) {
        int startX = Math.max(0, x - 1);
        int startY = Math.max(0, y - 1);
        int endX = Math.min(mask.length - 1, x + w + 1);
        int endY = Math.min(mask[0].length - 1, y + h + 1);

        for (int row = startY; row < endY; row++) {
            for (int col = startX; col < endX; col++) {
                if (mask[col][row]) return true; // Βρέθηκε σύγκρουση (επικάλυψη)
            }
        }
        return false; // Ο χώρος είναι απόλυτα καθαρός
    }

    /**
     * Σημειώνει όλα τα pixels ενός παραλληλογράμμου ως "κατειλημμένα" (true) στη μάσκα.
     */
    private static void markAreaOccupied(int x, int y, int w, int h, boolean[][] mask) {
        for (int row = y; row < y + h; row++) {
            for (int col = x; col < x + w; col++) {
                mask[col][row] = true;
            }
        }
    }

    /**
     * Βοηθητική εσωτερική κλάση (POJO) για την αναπαράσταση μιας λέξης στο cloud.
     */
    private static class CloudWord {
        String text;
        int weight;

        CloudWord(String text, int weight) {
            this.text = text;
            this.weight = weight;
        }
    }
}