package gr.aueb.cs.review;

import java.util.ArrayList;

public class ConstructorBuildingTest {
    public static void main(String[] args) {
        ArrayList<SongElement> elems = new ArrayList<SongElement>();

        // Κώδικας Α
        try {
            // Δημιουργία της βασικής λίστας τραγουδιών (δεν ανήκει σε άλλη)
            SongList songList1 = new SongList("SongList1", null);
            // sl1.elems = []

            // Δημιουργία υπολίστας που ανήκει στην SongList1
            SongList songList2 = new SongList("SongList2", songList1);
            // sl1.elems = [sl2]

            // Δημιουργία τραγουδιών
            Song song1 = new Song("Song1", 3.45f, songList2); // sl2.elems = [s1]
            Song song2 = new Song("Song2", 2.41f, songList1); // sl1.elems = [sl2, s2]
            Song song3 = new Song("Song3", 2.17f, songList1); // sl1.elems = [sl2, s2, s3]

            elems.addAll(songList1.elems);
            elems.addAll(songList2.elems);
            elems.add(songList1);

            // Εκτύπωση περιεχομένων
            for (SongElement s : elems) {
                System.out.printf("%s has duration %3.2f%n", s.name, s.getDuration());
            }

        } catch (Exception e) {
            System.err.println("Σφάλμα: " + e.getMessage());
        }

    }
}

abstract class SongElement {
    String name;                // Όνομα του τραγουδιού ή της λίστας
    SongElement songList;       // Η λίστα στην οποία ανήκει το στοιχείο

    public SongElement(String name, SongElement songList) throws Exception {
        this.name = name;

        if (songList != null) {
            if (!(songList instanceof SongList)) {
                throw new Exception("Το songList πρέπει να είναι λίστα τραγουδιών (SongList)");
            } else {
                ((SongList) songList).elems.add(this);  // προσθήκη του στοιχείου στη λίστα
            }
        }

        this.songList = songList; // ενημέρωση του πεδίου (αφού περάσει ο έλεγχος)
    }

    // Προσθέτουμε dummy getDuration() για να μην έχουμε compile error στο main
    public abstract Float getDuration();

}

class SongList extends SongElement {
    ArrayList<SongElement> elems;

    public SongList(String name, SongElement songList) throws Exception {
        super(name, songList);
        elems = new ArrayList<SongElement>();
    }

    @Override
    public Float getDuration() {
        float sum = 0.0f;
        for (SongElement el : elems) {
            Float d = el.getDuration();
            if (d != null) sum += d;
        }
        return sum;
    }
}

class Song extends SongElement {
    private Float duration;//διάρκεια

    public Song(String name, Float duration, SongElement songList) throws Exception {
        super(name, songList);
        this.duration = duration;
    }

    @Override
    public Float getDuration() {
        return duration;
    }
}
