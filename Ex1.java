import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class Ex1 {

  static String searchType; // what type of search to perform
  static String printTime;
  static String openList;
  static int row;
  static int col;
  static String[] start;
  static Block[] blocks;
  static Block[] goal;
  static HashMap<Integer, Integer> whiteCells = new HashMap<>();
  public static int count = 0;
  public static String path;
  public static int costOfResult;
  public static long startTime;
  public static long endTime;
  public static double totalTime;

  public static void run() throws IOException {

    File file = new File("input.txt");

    try (Scanner scanner = new Scanner(file)) {
      searchType = scanner.nextLine(); // switch for to kind search
      printTime = scanner.nextLine();

      openList = scanner.nextLine();
      String stringSize = scanner.nextLine();
      String split[] = stringSize.split("x");

      row = Integer.parseInt(split[0]);
      col = Integer.parseInt(split[1]);

      // Read white cells
      String line = scanner.nextLine();
      if (line.startsWith("White: ")) {
        String tuplesStr = line.substring(7).trim();
        String[] tupleStrings = tuplesStr.split("\\),");

        for (String tupleString : tupleStrings) {
          tupleString = tupleString.trim();
          if (tupleString.startsWith("(")) {
            tupleString = tupleString.substring(1);
          }
          if (tupleString.endsWith(")")) {
            tupleString = tupleString.substring(0, tupleString.length() - 1);
          }

          String[] parts = tupleString.split(",");

          if (parts.length >= 2) {
            int key = Integer.parseInt(parts[0]);
            int value = Integer.parseInt(parts[1]);

            whiteCells.put(key, value);
          } else {
            System.out.println("Invalid tuple format: " + tupleString);
          }
        }
      }

      line = null;
      int rowCounter = 0;
      start = new String[row * col];
      // read the grid
      while (rowCounter < row && (line = scanner.nextLine()) != null) {
        String[] values = line.split(",");
        if (values.length != col) {
          System.out.println("Invalid number of elements in the row");
          // Handle the error as required, for example, skip the row or show an error
          // message
          continue;
        }

        for (int j = 0; j < col; j++) {
          start[rowCounter * col + j] = values[j];
        }

        rowCounter++;
      }

      blocks = new Block[row * col]; // init the blocks of the given grid
      for (int counter = 0; counter < start.length; counter++) {
        Block b;
        if (start[counter].equals("_")) {
          b = new Block(0); // init the empty block with 0
          blocks[counter] = b;
          continue;
        }
        int num = Integer.parseInt(start[counter]); // the number in the string

        if (whiteCells.containsKey(num)) { // need to check if this is a white cell
          int moves = whiteCells.get(num); // get the moves from the HashMap
          b = new Block(num, "White", moves);
          blocks[counter] = b;
        } else {
          b = new Block(num, "Red");
          blocks[counter] = b;
        }

      }

      // Initialize the goal state
      goal = new Block[col * row];

      for (int i = 0; i < blocks.length; i++) {
        Block block = blocks[i];
        if (block.num != 0) {
          // Place the block in its correct position in the goal state
          goal[block.num - 1] = block;
        } else {
          // Place the empty block in the last position of the goal state
          goal[col * row - 1] = new Block(0);
        }
      }

      scanner.close();
    } catch (NumberFormatException e1) {
      e1.printStackTrace();
    }

    switch (searchType) {
      case "DFID":
        startTime = System.currentTimeMillis();
        DFID.dfid(blocks, goal);
        endTime = System.currentTimeMillis();
        totalTime = (endTime - startTime) / 1000.0;
        break;
      case "A*":
        startTime = System.currentTimeMillis();
        Astar.astar(blocks, goal);
        endTime = System.currentTimeMillis();
        totalTime = (endTime - startTime) / 1000.0;
        break;
      case "IDA*":
      startTime = System.currentTimeMillis();
      IDAstar.idastar(blocks, goal);
      endTime = System.currentTimeMillis();
      totalTime = (endTime - startTime) / 1000.0;
      break;
      // case "DFBnB":
      // startTime = System.currentTimeMillis();
      // dfbnb.DFBnB(blocks, goal);
      // endTime = System.currentTimeMillis();
      // totalTime = (endTime - startTime) / 1000.0;
      // break;

      default:
        break;
    }

    try {
      File myObj = new File("output.txt");

      System.out.println("File created: " + myObj.getName());

      PrintWriter writer = new PrintWriter("output.txt");
      if (path.isEmpty()) {
        writer.println("no path");
        writer.println("Num: " + count);
      } else {
        writer.println(path);
        writer.println("Num: " + count);
        writer.println("Cost: " + costOfResult);
        if (printTime.equalsIgnoreCase("with time")) {
          writer.println(totalTime + " seconds");
        }

      }

      writer.close();

    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    run();
  }
}
