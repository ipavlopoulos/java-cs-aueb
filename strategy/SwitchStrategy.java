package strategy;

/**
 * A strategy where the player switches to the remaining unopened door.
 */
public class SwitchStrategy implements Strategy {
    @Override
    public int chooseDoor(int playerChoice, int hostOpens) {
        for (int i = 0; i < 3; i++) {
            if (i != playerChoice && i != hostOpens) {
                return i;
            }
        }
        return playerChoice; // fallback (should never happen)
    }
}
