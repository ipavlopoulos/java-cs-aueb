package gr.aueb.cs.projects.walkroute.agentstack;

/**
 * Represents a 3D pedestrian map grid,
 * where each cell can be either walkable or an obstacle.
 */
public class Map3D {
    private final int[][][] grid;  // 3D grid: 0 = free, 1 = obstacle

    /**
     * Constructs a 3D map of specified dimensions.
     *
     * @param xSize number of cells along x-axis
     * @param ySize number of cells along y-axis
     * @param zSize number of levels (height)
     */
    public Map3D(int xSize, int ySize, int zSize) {
        grid = new int[xSize][ySize][zSize];
    }

    /**
     * Marks a cell as an obstacle.
     */
    public void setObstacle(int x, int y, int z) {
        grid[x][y][z] = 1;
    }

    /**
     * Checks if a cell is an obstacle.
     */
    public boolean isObstacle(int x, int y, int z) {
        return grid[x][y][z] == 1;
    }

    /**
     * Checks if a coordinate is within the map bounds.
     */
    public boolean isValid(int x, int y, int z) {
        return x >= 0 && y >= 0 && z >= 0 &&
                x < grid.length &&
                y < grid[0].length &&
                z < grid[0][0].length;
    }

    public int getXSize() { return grid.length; }
    public int getYSize() { return grid[0].length; }
    public int getZSize() { return grid[0][0].length; }
}
