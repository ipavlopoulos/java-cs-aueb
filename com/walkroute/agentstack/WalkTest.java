package com.walkroute.agentstack;

public class WalkTest {
    public static void main(String[] args) {
        // The starting location of the agent
        Location start = new Location(37.9870, 23.7314, 95, "Patision", "76", "10434");
        Agent agent = new Agent(start);
        System.out.println("Now at: " + agent.getCurrentLocation());

        // The agent moves to another location
        Location step1 = new Location(37.9871, 23.7316, 95, "Patision", "78", "10434");
        agent.moveTo(step1);
        System.out.println("Now at: " + agent.getCurrentLocation());

        // The agent moves again
        Location step2 = new Location(37.9872, 23.7318, 95, "Patision", "80", "10434");
        agent.moveTo(step2);
        System.out.println("Now at: " + agent.getCurrentLocation());

        // The agent goes back
        agent.undo();
        System.out.println("Undo → now at: " + agent.getCurrentLocation());

        // The agent goes back again
        agent.undo();
        System.out.println("Undo → now at: " + agent.getCurrentLocation());
    }
}
