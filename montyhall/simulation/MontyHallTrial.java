package montyhall.simulation;

import montyhall.strategy.Strategy;

import java.util.Random;

/**
 * Represents a single trial of the Monty Hall game.
 */
public class MontyHallTrial {
    private int prizeDoor;      // the door that has the prize
    private int playerChoice;   // the player's initial choice
    private int hostOpens;      // the door opened by the host
    private Random rand = new Random();

    /**
     * Initializes a new Monty Hall trial with random prize and player choices.
     */
    public MontyHallTrial() {
        prizeDoor = rand.nextInt(3);
        playerChoice = rand.nextInt(3);

        // Host opens a door that is not the prize and not the player's choice
        do {
            hostOpens = rand.nextInt(3);
        } while (hostOpens == prizeDoor || hostOpens == playerChoice);
    }

    /**
     * Plays the trial with a given montyhall.strategy.
     * @param strategy the montyhall.strategy to apply
     * @return true if the player wins, false otherwise
     */
    public boolean play(Strategy strategy) {
        int finalChoice = strategy.chooseDoor(playerChoice, hostOpens);
        System.out.println("Prize: " + prizeDoor + ", Player chose: " + playerChoice + ", Host opened: " + hostOpens + ", Final choice: " + finalChoice);
        return finalChoice == prizeDoor;
    }
}