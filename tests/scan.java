// import org.junit.*;
// import org.junit.Test;

// public class scan {
//     @Test
//     public void testReadInputFromFile() {
//         // Update the input file path as per your local file location
//         String filename = "path/to/your/input.txt";

//         // Create a new Game instance
//         Game game = new Game("DFID", true, true);

//         // Read input from the file
//         game.readInputFromFile(filename);

//         // Perform assertions to check the state of the game and grid
//         Cell[][] grid = game.getGrid();
//         assertEquals(3, grid.length);
//         assertEquals(4, grid[0].length);

//         // Check white cells
//         assertEquals(-1, grid[1][1].getMoves()); // White cell (7,2)
//         assertEquals(-1, grid[2][2].getMoves()); // White cell (8,3)

//         // Check board cells
//         assertEquals(1, grid[0][0].getNumber());
//         assertEquals(2, grid[0][1].getNumber());
//         assertEquals(3, grid[0][2].getNumber());
//         assertEquals(4, grid[0][3].getNumber());
//         assertEquals(5, grid[1][0].getNumber());
//         assertEquals(6, grid[1][1].getNumber());
//         assertEquals(11, grid[1][2].getNumber());
//         assertEquals(7, grid[1][3].getNumber());
//         assertEquals(9, grid[2][0].getNumber());
//         assertEquals(10, grid[2][1].getNumber());
//         assertEquals(8, grid[2][2].getNumber());

//         // Additional assertions can be added as needed

//     }
// }

// /*
//  * 
//  * 
//  * 
//  * 
//  */