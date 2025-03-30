package strategy;

/**
 * Strategy interface for choosing a door in the Monty Hall game.
 */
public interface Strategy {
    /**
     * Determines the player's final door choice based on the strategy.
     * @param playerChoice the player's initial choice
     * @param hostOpens the door opened by the host
     * @return the final door choice
     */
    int chooseDoor(int playerChoice, int hostOpens);
}
