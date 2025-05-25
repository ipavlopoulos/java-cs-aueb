package gr.aueb.cs.projects.walkroute.agentstack;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a walking agent (e.g. a pedestrian) navigating through a route.
 * The agent keeps track of their current location and movement history
 * using a stack, allowing step-by-step undo functionality.
 */
public class Agent {
    private Location currentLocation;        // current position of the agent
    private Stack<Location> pathHistory;     // stack to store movement history (LIFO)
    private List<Location> pathTaken;       // full path (including current)

    /**
     * Creates an agent starting at a given location.
     * @param start initial position
     */
    public Agent(Location start) {
        this.currentLocation = start;
        this.pathHistory = new Stack<>();
        this.pathTaken = new ArrayList<>();
        this.pathTaken.add(start);
    }

    /**
     * Moves the agent to a new location and pushes the previous one to history.
     * This allows the agent to later undo the move.
     * @param next the new location to move to
     */
    public void moveTo(Location next) {
        pathHistory.push(currentLocation);   // store current location before moving
        currentLocation = next;
        pathTaken.add(next);
    }

    /**
     * Undoes the last move: pops from the history stack and returns to that location.
     * If no previous locations are stored, nothing changes.
     * @return the new current location after undo
     */
    public Location undo() {
        if (!pathHistory.isEmpty()) {
            currentLocation = pathHistory.pop();
            pathTaken.remove(pathTaken.size() - 1); // remove last step
        }
        return currentLocation;
    }

    /**
     * Gets the current location of the agent.
     * @return current location
     */
    public Location getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Checks if the agent has any steps it can undo.
     * @return true if undo is possible
     */
    public boolean canUndo() {
        return !pathHistory.isEmpty();
    }

    /**
     * Returns the full path that the agent has followed.
     * @return ordered list of visited locations
     */
    public List<Location> getPathTaken() {
        return pathTaken;
    }
}
