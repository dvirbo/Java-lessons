
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Game {
    private String algorithm;
    private boolean withTime;
    private boolean noOpen;

    private Cell[][] grid;

    public Game(String algorithm, boolean withTime, boolean noOpen) {
        this.algorithm = algorithm;
        this.withTime = withTime;
        this.noOpen = noOpen;
    }

    public void readInputFromFile(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));

            // Read initial grid dimensions
            int numRows = scanner.nextInt();
            int numCols = scanner.nextInt();
            grid = new Cell[numRows][numCols];

            // Read white cells
            String whiteLine = scanner.nextLine().trim();
            String[] tuples = whiteLine.split("\\),\\(");

            // Read board cells
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    int number = scanner.nextInt();

                    if (grid[i][j] == null) { // check if the cell is not an exist one
                        grid[i][j] = new Cell(number, null, -1);
                    }
                }
            }

            for (String tuple : tuples) {
                String[] data = tuple.replace("(", "").replace(")", "").split(",");
                int cellNumber = Integer.parseInt(data[0]);
                int maxSteps = Integer.parseInt(data[1]);

                // Find the cell number within the already initialized grid
                boolean found = false;
                int i, j = 0;
                for (i = 0; i < numRows; i++) {
                    for (j = 0; j < numCols; j++) {
                        if (grid[i][j].getNumber() == cellNumber) {
                            grid[i][j].setMoves(maxSteps);
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }
                grid[i][j].setColor("grey"); // init the empty block
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Input file not found!");
        }
    }
    
    

    public void play() {
        // Game logic
    }

}
