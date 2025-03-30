package montyhall;

import java.util.Random;
import java.util.Scanner;

/**
 * Interactive single trial of the Monty Hall game using user choices.
 */
public class Trial {

    /**
     * Runs one interactive round of the Monty Hall game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        int prize = rand.nextInt(3); // door with the prize

        // Prompt user to pick a door
        System.out.print("Choose a door (0, 1, 2): ");
        int playerChoice = sc.nextInt();

        while (playerChoice < 0 || playerChoice > 2) {
            System.out.print("Invalid choice. Please enter 0, 1, or 2: ");
            playerChoice = sc.nextInt();
        }

        // Host opens a door that's not the prize and not the player's choice
        int hostOpens;
        do {
            hostOpens = rand.nextInt(3);
        } while (hostOpens == prize || hostOpens == playerChoice);

        System.out.println("The host opens door: " + hostOpens + " (no prize)");

        // Ask the player if they want to switch
        System.out.print("Do you want to switch doors? (y/n): ");
        String input = sc.next();

        boolean switchDoor = input.equalsIgnoreCase("y");

        // Determine final choice
        int finalChoice = switchDoor
                ? 3 - playerChoice - hostOpens
                : playerChoice;

        System.out.println(switchDoor
                ? "You switched to door: " + finalChoice
                : "You stayed with door: " + finalChoice);

        // Reveal the outcome
        System.out.println("The prize was behind door: " + prize);
        boolean win = finalChoice == prize;
        System.out.println(win ? "🎉 You WIN!" : "❌ You lose.");

        sc.close();
    }
}
