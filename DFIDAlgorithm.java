
import java.util.ArrayList;

public class DFIDAlgorithm {
    private int bound;
    private boolean goalFound;
    private ArrayList<String> actions;
    private Cell[][] finalGrid;

    public DFIDAlgorithm() {
        bound = 0;
        goalFound = false;
        actions = new ArrayList<>();
    }

    public void run(Cell[][] initialGrid) {
        finalGrid = getFinalGrid(initialGrid.length, initialGrid[0].length);

        while (!goalFound) {
            // Call the DFS method with current bound
            int result = dfsWithBound(initialGrid, 0);

            if (result == -1) {
                bound++; // Increase the bound if goal not found within current bound
            } else if (result == 0) {
                goalFound = true; // Goal state found
            } else {
                // Handle any error or termination condition here
                break;
            }
        }

        // Print the actions taken and cost of the route found
        System.out.println("Actions: " + actions);
        System.out.println("Cost: " + bound);
    }

    private int dfsWithBound(Cell[][] grid, int depth) {
        // Check if current depth exceeds the bound
        if (depth > bound) {
            return -1;
        }

        // Check if current state is the goal state
        if (isGoalState(grid)) {
            return 0;
        }

        // iterate through each cell
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (depth + 1 <= grid[i][j].getMoves()) {
                    // Move cell to the right
                    if (j < grid[0].length - 1 && grid[i][j + 1] == null) {
                        Cell[][] newState = copyGrid(grid);
                        newState[i][j + 1] = newState[i][j];
                        newState[i][j] = null;
                        actions.add(grid[i][j].getNumber() + " R");
                        int result = dfsWithBound(newState, depth + 1);
                        if (result == 0) {
                            return 0;
                        }
                        actions.remove(actions.size() - 1);
                    }
                    // Move cell down
                    if (i < grid.length - 1 && grid[i + 1][j] == null) {
                        Cell[][] newState = copyGrid(grid);
                        newState[i + 1][j] = newState[i][j];
                        newState[i][j] = null;
                        actions.add(grid[i][j].getNumber() + " D");
                        int result = dfsWithBound(newState, depth + 1);
                        if (result == 0) {
                            return 0;
                        }
                        actions.remove(actions.size() - 1);
                    }
                    // Move cell to the left
                    if (j > 0 && grid[i][j - 1] == null) {
                        Cell[][] newState = copyGrid(grid);
                        newState[i][j - 1] = newState[i][j];
                        newState[i][j] = null;
                        actions.add(grid[i][j].getNumber() + " L");
                        int result = dfsWithBound(newState, depth + 1);
                        if (result == 0) {
                            return 0;
                        }
                        actions.remove(actions.size() - 1);
                    }
                    // Move cell up
                    if (i > 0 && grid[i - 1][j] == null) {
                        Cell[][] newState = copyGrid(grid);
                        newState[i - 1][j] = newState[i][j];
                        newState[i][j] = null;
                        actions.add(grid[i][j].getNumber() + " U");
                        int result = dfsWithBound(newState, depth + 1);
                        if (result == 0) {
                            return 0;
                        }
                        actions.remove(actions.size() - 1);
                    }
                }
            }
        }
        return -1; // Return -1 if goal not found within current bound
    }

    private boolean isGoalState(Cell[][] grid) {
        // Check if the current state matches the goal state
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == null) {
                    if (i == grid.length - 1 && j == grid[0].length - 1) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (grid[i][j].getNumber() != finalGrid[i][j].getNumber()) {
                    return false;
                }
            }
        }
        return true;
    }

    private Cell[][] copyGrid(Cell[][] grid) {
        // Create a copy of the grid
        Cell[][] copy = new Cell[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != null) {
                    copy[i][j] = new Cell(grid[i][j].getNumber(), grid[i][j].getColor(), grid[i][j].getMoves());
                }
            }
        }
        return copy;
    }

    private Cell[][] getFinalGrid(int rows, int columns) {
        Cell[][] finalGrid = new Cell[rows][columns];
        int number = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == rows - 1 && j == columns - 1) {
                    finalGrid[i][j] = null; // Empty cell for the bottom-right corner
                } else {
                    finalGrid[i][j] = new Cell(number,"t", 1);
                    number++;
                }
            }
        }
        return finalGrid;
    }

}