package gr.aueb.cs.projects.walkroute.agentstack;

import java.util.Scanner;

/**
 * Simple interactive demo for moving an Agent around
 * and using a stack to undo previous steps.
 * todo: (a) add -h option (show route history), (b) utilise the map
 */
public class InteractiveDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Start the agent at Patision 76
        Location start = new Location(37.9870, 23.7314, 95, "Patision", "76", "10434");
        Agent agent = new Agent(start);

        System.out.println("🚶‍♂️ Agent starting at: " + agent.getCurrentLocation());

        boolean running = true;
        while (running) {
            System.out.println("\nChoose action: (m)ove, (u)ndo, (h)istory, (q)uit");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "m":
                    System.out.print("Enter new latitude: ");
                    double lat = Double.parseDouble(scanner.nextLine());

                    System.out.print("Enter new longitude: ");
                    double lon = Double.parseDouble(scanner.nextLine());

                    System.out.print("Enter new height (meters): ");
                    double h = Double.parseDouble(scanner.nextLine());

                    System.out.print("Street name: ");
                    String street = scanner.nextLine();

                    System.out.print("Street number: ");
                    String number = scanner.nextLine();

                    System.out.print("Postal code: ");
                    String postalCode = scanner.nextLine();

                    Location next = new Location(lat, lon, h, street, number, postalCode);
                    agent.moveTo(next);
                    System.out.println("✅ Moved to: " + agent.getCurrentLocation());
                    break;

                case "u":
                    if (agent.canUndo()) {
                        Location previous = agent.undo();
                        System.out.println("↩️ Undid last move. Now at: " + previous);
                    } else {
                        System.out.println("⚠️ No previous location to undo.");
                    }
                    break;

                case "q":
                    running = false;
                    System.out.println("👋 Goodbye!");
                    break;

                case "h":
                    System.out.println("📜 Path history:");
                    for (Location loc : agent.getPathTaken()) {
                        System.out.println("→ " + loc);
                    }
                    break;

                default:
                    System.out.println("Invalid input. Use m/u/h/q.");
            }
        }

        scanner.close();
    }
}
