package simulation;

import strategy.StayStrategy;
import strategy.Strategy;
import strategy.SwitchStrategy;

/**
 * Runs multiple Monty Hall simulations to evaluate strategies.
 */
public class MontyHallSimulator {
    public static void main(String[] args) {
        int simulations = 10000;
        runSimulation(new StayStrategy(), simulations, "Stay with original choice");
        runSimulation(new SwitchStrategy(), simulations, "Switch to other door");
    }

    /**
     * Runs a given strategy multiple times and prints the results.
     * @param strategy the strategy to test
     * @param times number of trials to run
     * @param label description label for the strategy
     */
    public static void runSimulation(Strategy strategy, int times, String label) {
        int wins = 0;
        for (int i = 0; i < times; i++) {
            MontyHallTrial trial = new MontyHallTrial();
            if (trial.play(strategy)) {
                wins++;
            }
        }
        System.out.println(label + ": " + wins + "/" + times + " wins (" + (wins * 100.0 / times) + "%)");
    }
}