package walkroute;

public class Main {
    public static void main(String[] args) {
        System.out.print("Hello and welcome! More to be filled in...");
    }
}
/* potential packaging
pedestrian/
├── model/         // basic world model/entities (Location, Route, κτλ.)
│   ├── Location.java
│   └── Route.java (ή Path.java)
│
├── routing/       // Routing logic
│   └── Router.java
│
├── io/            // In/Out, e.g., from the user
│   └── MapLoader.java
│
├── ui/            // GUI or so
│   └── ConsoleUI.java
│
└── app/           // The app
└── PedestrianApp.java
*/