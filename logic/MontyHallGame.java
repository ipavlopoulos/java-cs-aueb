package logic;

import model.Door;
import java.util.Random;

/**
 * Simulates a game of the Monty Hall problem using Door objects.
 * The game initializes three doors, randomly places a prize,
 * allows the player to choose, and the host to open a door.
 */
public class MontyHallGame {
    // todo: create getters/setters

    private Door[] doors;
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
     * The host opens one of the doors that was not chosen by the player
     * and does not contain the prize.
     * The door is marked as open and stored for reference.
     */
    public void hostOpensDoor() {
        for (int i = 0; i < 3; i++) {
            if (i != playerChoice && !doors[i].hasPrize()) {
                doors[i].open();
                hostOpens = i;
                break;
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
