import java.util.Random;

// todo: switch to OOP design

/**
 * A utility class to simulate the Monty Hall problem and compare strategies.
 */
public class Simulator {

    /**
     * Runs multiple simulations of the Monty Hall problem and compares
     * the outcomes between switching and staying strategies.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        int simulations = 10000;
        int winsIfSwitch = 0;
        int winsIfStay = 0;

        for (int i = 0; i < simulations; i++) {
            if (simulate(true)) winsIfSwitch++;
            if (simulate(false)) winsIfStay++;
        }

        System.out.println("Switching: " + winsIfSwitch + "/" + simulations +
                " wins (" + (winsIfSwitch * 100.0 / simulations) + "%)");
        System.out.println("Staying:   " + winsIfStay + "/" + simulations +
                " wins (" + (winsIfStay * 100.0 / simulations) + "%)");

        // Optional: run one trial with printed explanation
        System.out.println("\n--- Sample trial with switching ---");
        runTrial(true);

        System.out.println("\n--- Sample trial with staying ---");
        runTrial(false);
    }

    /**
     * Simulates a single Monty Hall game with or without switching.
     *
     * @param switchDoor whether the player switches after the host opens a door
     * @return true if the player wins the prize
     */
    public static boolean simulate(boolean switchDoor) {
        Random rand = new Random();

        int prize = rand.nextInt(3);
        int playerChoice = rand.nextInt(3);

        int hostOpens;
        do {
            hostOpens = rand.nextInt(3);
        } while (hostOpens == prize || hostOpens == playerChoice);

        int finalChoice = switchDoor
                ? 3 - playerChoice - hostOpens
                : playerChoice;

        return finalChoice == prize;
    }

    /**
     * Runs a single trial of the Monty Hall game and prints the steps.
     * This method is useful for demonstration or teaching.
     *
     * @param switchDoor whether the player switches doors
     */
    public static void runTrial(boolean switchDoor) {
        Random rand = new Random();

        int prize = rand.nextInt(3);
        int playerChoice = rand.nextInt(3);

        int hostOpens;
        do {
            hostOpens = rand.nextInt(3);
        } while (hostOpens == prize || hostOpens == playerChoice);

        System.out.println("Prize is behind door: " + prize);
        System.out.println("Player initially chose door: " + playerChoice);
        System.out.println("Host opens door: " + hostOpens + " (no prize)");

        int finalChoice = switchDoor
                ? 3 - playerChoice - hostOpens
                : playerChoice;

        System.out.println(switchDoor
                ? "Player switches to door: " + finalChoice
                : "Player stays with door: " + finalChoice);

        boolean win = finalChoice == prize;
        System.out.println(win ? "Result: Player WINS!" : "Result: Player loses.");
    }
}
