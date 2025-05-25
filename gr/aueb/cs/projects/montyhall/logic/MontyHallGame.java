package gr.aueb.cs.projects.montyhall.logic;

import gr.aueb.cs.projects.montyhall.model.Door;
import java.util.Random;

/**
 * Simulates a game of the Monty Hall problem using Door objects.
 * The game initializes three doors, randomly places a prize,
 * allows the player to choose, and the host to open a door.
 */
public class MontyHallGame {

    private Door[] doors; // Array of Door objects representing the doors in the game
    private int playerChoice;
    private int hostOpens;

    /**
     * Constructs a new Monty Hall game by initializing three doors,
     * one of which randomly contains a prize.
     */
    public MontyHallGame() {
        doors = new Door[3];
        Random rand = new Random();
        int prizeDoor = rand.nextInt(3);
        for (int i = 0; i < 3; i++) {
            doors[i] = new Door(i == prizeDoor);
        }
    }

    /**
     * Sets the door initially chosen by the player.
     *
     * @param choice the index (0, 1, or 2) of the door the player selects
     */
    public void chooseDoor(int choice) {
        this.playerChoice = choice;
    }

    /**
     * 
     * Getter and Setter for the door the host opens.
     * 
     */
    public void setHostOpens(int hostOpens) {
        if (hostOpens != playerChoice && !doors[hostOpens].hasPrize()) { // validation
            this.hostOpens = hostOpens;
        }
    }

    public int getHostOpens() {
        return hostOpens;
    }

    /**
     * The host opens one of the doors that was not chosen by the player
     * and does not contain the prize.
     * The door is marked as open and stored for reference.
     */
    public void hostOpensDoor() {
        for (int i = 0; i < 3; i++) {
            if (i != playerChoice && !doors[i].hasPrize()) {
                setHostOpens(i); // keep the first valid door
                doors[i].open(); // open the door
                break; // stop after opening one door
            }
        }
    }

    /**
     * Simulates the player switching to the other unopened door,
     * and returns whether they win the prize.
     *
     * @return true if the switched-to door has the prize, false otherwise
     */
    public boolean switchAndCheckWin() {
        for (int i = 0; i < 3; i++) {
            if (i != playerChoice && i != hostOpens) {
                return doors[i].hasPrize();
            }
        }
        return false;
    }

    /**
     * Simulates the player staying with their original door choice,
     * and returns whether they win the prize.
     *
     * @return true if the originally chosen door has the prize, false otherwise
     */
    public boolean stayAndCheckWin() {
        return doors[playerChoice].hasPrize();
    }
}
