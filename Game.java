
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Game {
    private String algorithm;
    private boolean withTime;
    private boolean noOpen;

    private int[][] initialGrid;
    private int[][] whiteCells;
    private int[] boardCells;

    public Game(String algorithm, boolean withTime, boolean noOpen) {
        this.algorithm = algorithm;
        this.withTime = withTime;
        this.noOpen = noOpen;
    }

    public void readInputFromFile(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));

            // Read initial grid
            int numRows = scanner.nextInt();
            int numCols = scanner.nextInt();
            initialGrid = new int[numRows][numCols];
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    initialGrid[i][j] = scanner.nextInt();
                }
            }

            // Read white cells
            int numWhiteCells = scanner.nextInt();
            whiteCells = new int[numWhiteCells][2];
            for (int i = 0; i < numWhiteCells; i++) {
                whiteCells[i][0] = scanner.nextInt();
                whiteCells[i][1] = scanner.nextInt();
            }

            // Read board cells
            int totalCells = numRows * numCols;
            boardCells = new int[totalCells];
            for (int i = 0; i < totalCells; i++) {
                boardCells[i] = scanner.nextInt();
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
