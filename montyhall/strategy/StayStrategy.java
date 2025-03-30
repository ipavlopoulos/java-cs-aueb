package montyhall.strategy;

/**
 * A montyhall.strategy where the player sticks with their original choice.
 */
public class StayStrategy implements Strategy {
    @Override
    public int chooseDoor(int playerChoice, int hostOpens) {
        return playerChoice;
    }
}