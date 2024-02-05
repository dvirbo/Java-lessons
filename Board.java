import java.util.Arrays;

public class Board {

    private Cell[][] grid;
    private int[][] whiteCells;
    private int emptyX;
    private int emptyY;

    public Board(int[][] grid, int[][] whiteCells, int[] boardCells) {
        int numRows = grid.length;
        int numCols = grid[0].length;

        this.grid = new Cell[numRows][numCols];
        this.emptyX = -1;
        this.emptyY = -1;

        // Initialize the grid with cells
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int cellNumber = grid[i][j];
                String cellColor = "Red";
                int cellMoves = 30;

                // Check if the cell is a white cell
                for (int[] whiteCell : whiteCells) {
                    int whiteCellX = whiteCell[0];
                    int whiteCellY = whiteCell[1];

                    if (i == whiteCellX && j == whiteCellY) {
                        cellColor = "White";
                        cellMoves = boardCells[whiteCellX * numCols + whiteCellY];
                        break;
                    }
                }

                this.grid[i][j] = new Cell(cellNumber, cellColor, cellMoves);

                // Set the coordinates of the empty cell
                if (cellNumber == 0) {
                    emptyX = i;
                    emptyY = j;
                }
            }
        }
    }

    // Getters and setters for grid, whiteCells, and boardCells

    public void updateBlockPosition(int number, int newX, int newY) {
        // Update block position in the grid
        Cell block = grid[number / grid[0].length][number % grid[0].length];
        grid[emptyX][emptyY] = block;
        grid[number / grid[0].length][number % grid[0].length] = new Cell(0, "Red", 30);

        emptyX = newX;
        emptyY = newY;
    }

    public boolean isGoalState() {
        // Check if current state is the goal state
        int[] flatGrid = flattenGrid();
        int[] sortedGrid = Arrays.copyOf(flatGrid, flatGrid.length);
        Arrays.sort(sortedGrid);

        for (int i = 0; i < sortedGrid.length; i++) {
            if (sortedGrid[i] != i + 1) {
                return false;
            }
        }
        return true;
    }

    private int[] flattenGrid() {
        int numRows = grid.length;
        int numCols = grid[0].length;
        int[] result = new int[numRows * numCols];
        int index = 0;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                result[index++] = grid[i][j].getNumber();
            }
        }

        return result;
    }
}
