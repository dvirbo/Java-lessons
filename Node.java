/*
 * class Node represent a level in the game tree.
 */
public class Node {

    static int col = Ex1.col;
    static int row = Ex1.row;
    Block[] blocks = new Block[col * row];
    int cost; // cost of al the moves
    int direction;
    char father = 'X'; // the move of the "father" (L/R/U/D)
    int f; // huristic function
    Boolean visit = false;
    String path = null;
    int creationIteration; // the iteration in which the vertex was created

    public Node() {

    }

    public Node(Block[] arr) {

        this.blocks = arr;
        this.path = null;
        this.cost = 0;

    }

    public Node(Node other) {
        this.blocks = other.blocks;
        this.cost = other.cost;
        this.f = other.f;
        this.visit = other.visit;
        this.father = other.father;
        this.path = other.path;
    }

    /**
     * This method initializes the matrix variable in each block of the puzzle
     * board.
     * The data represented as a pair of integers (i, j) (row & col)
     * 
     * @param node the node object that contains the game board blocks
     */
    public static void initMat(Node node) {
        int counter = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                node.blocks[counter].coordinates[0] = i;
                node.blocks[counter].coordinates[1] = j;
                counter++;
            }
        }
    }

    public static Node move(Node currentNode, int emptyBlock, String direction) {
        Node ans = null;
        if (direction.equals("left") && (currentNode.father == 'X' || currentNode.father != 'R')) {
            ans = Node.moveLeft(currentNode, emptyBlock);
        } else if (direction.equals("up") && (currentNode.father == 'X' || currentNode.father != 'D')) {
            ans = Node.moveUp(currentNode, emptyBlock);
        } else if (direction.equals("right") && (currentNode.father == 'X' || currentNode.father != 'L')) {
            ans = Node.moveRight(currentNode, emptyBlock);
        } else if (direction.equals("down") && (currentNode.father == 'X' || currentNode.father != 'U')) {
            ans = Node.moveDown(currentNode, emptyBlock);
        }
        return ans;
    }

    /**
     * This method creates a deep copy of the current state and swaps the blocks at
     * the specified positions.
     * 
     * @param currentState the current state of the board
     * @param position     the position of the first block
     * @param from         the position of the second block
     * @return a new state with the swapped blocks
     */
    public static Node performSwap(Node currentState, int position, int from) {
        // Create a deep copy of the current state
        Node newState = new Node();
        newState.cost = currentState.cost;
        newState.father = currentState.father;
        if (currentState.path != null) {
            newState.path = currentState.path.toString();
        }
        newState.blocks = new Block[currentState.blocks.length];
        for (int i = 0; i < currentState.blocks.length; i++) {
            newState.blocks[i] = new Block(currentState.blocks[i].num, currentState.blocks[i].color,
                    currentState.blocks[i].moves);
        }

        // Swap the blocks between position and from
        Block tempBlock = newState.blocks[position];
        newState.blocks[position] = newState.blocks[from];
        newState.blocks[from] = tempBlock;

        return newState;
    }

    /**
     * This method searches for the first empty position in the given state.
     *
     * @param a the state to search in
     * @return the index of the first empty position, or -1 if no empty positions
     *         are found
     */
    public static int findEmpty(Node a) {
        int pos = 0;
        for (int i = 0; i < a.blocks.length; i++) {
            if (a.blocks[i].num == 0) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    public static Node moveLeft(Node currentState, int position) {
        // Check if the position is within the valid range
        if ((position + 1) % col == 0) {
            return null;
        }

        // Calculate the destination position
        int from = position + 1;

        if (currentState.blocks[from].color.trim().equalsIgnoreCase("white") && currentState.blocks[from].moves == 0) {
            return null;
        }

        // // Create a copy of the current state
        Node newState = performSwap(currentState, position, from);

        // Calculate the cost of the move
        int cost = 0;
        if (newState.blocks[position].color.trim().equalsIgnoreCase("white")) {
            cost = 1; // White cost 1
        } else {
            cost = 30; // Red cost 30
        }

        // Update the new state
        newState.cost += cost;
        newState.father = 'L';
        newState.direction = 0;
        if (newState.path == null) {
            newState.path = newState.blocks[position].num + "L";
        } else {
            newState.path += "-" + newState.blocks[position].num + "L";
        }

        return newState;

    }

    /**
     * Moves a block up one position if possible.
     *
     * @param currentState the current state of the board
     * @param position     the position of the empty block
     * @return new state of the board after the move, or null if the move is not
     *         possible
     */
    public static Node moveUp(Node currentState, int position) {
        // Check if the position is within the valid range
        if (position >= col * row - col) {
            return null;
        }
        // Calculate the destination position
        int from = position + col;

        if (currentState.blocks[from].color.trim().equalsIgnoreCase("white") && currentState.blocks[from].moves == 0) {
            return null;
        }

        // // Create a copy of the current state
        Node newState = performSwap(currentState, position, from);

        // Calculate the cost of the move
        int cost = 0;
        if (newState.blocks[position].color.trim().equalsIgnoreCase("white")) {
            cost = 1; // White cost 1
        } else {
            cost = 30; // Red cost 30
        }

        // Update the new state
        newState.cost += cost;
        newState.father = 'U';
        newState.direction = 1;
        if (newState.path == null) {
            newState.path = newState.blocks[position].num + "U";
        } else {
            newState.path += "-" + newState.blocks[position].num + "U";
        }

        return newState;

    }

    public static Node moveRight(Node currentState, int position) {
        // Check if the position is within the valid range
        if (position % col == 0) { // if the empty cell is in the right col
            return null;
        }
        // Calculate the destination position
        int from = position - 1;

        if (currentState.blocks[from].color.trim().equalsIgnoreCase("white") && currentState.blocks[from].moves == 0) {
            return null;
        }

        // // Create a copy of the current state
        Node newState = performSwap(currentState, position, from);

        // Calculate the cost of the move
        int cost = 0;
        if (newState.blocks[position].color.trim().equalsIgnoreCase("white")) {
            cost = 1; // White cost 1
        } else {
            cost = 30; // Red cost 30
        }

        // Update the new state
        newState.cost += cost;
        newState.father = 'R';
        newState.direction = 2;
        if (newState.path == null) {
            newState.path = newState.blocks[position].num + "R";
        } else {
            newState.path += "-" + newState.blocks[position].num + "R";
        }

        return newState;
    }

    public static Node moveDown(Node currentState, int position) {
        // Check if the position is within the valid range
        if (position < col) {
            return null;
        }
        // Calculate the destination position
        int from = position - col;

        if (currentState.blocks[from].color.trim().equalsIgnoreCase("white") && currentState.blocks[from].moves == 0) {
            return null;
        }

        // // Create a copy of the current state
        Node newState = performSwap(currentState, position, from);

        // Calculate the cost of the move
        int cost = 0;
        if (newState.blocks[position].color.trim().equalsIgnoreCase("white")) {
            cost = 1; // White cost 1
        } else {
            cost = 30; // Red cost 30
        }

        // Update the new state
        newState.cost += cost;
        newState.father = 'D';
        newState.direction = 3;
        if (newState.path == null) {
            newState.path = newState.blocks[position].num + "D";
        } else {
            newState.path += "-" + newState.blocks[position].num + "D";
        }

        return newState;

    }

    /**
     * Calculates the Manhattan distance between two nodes.
     * 
     * @param node the node to calculate the distance from
     * @return the Manhattan distance between the two nodes
     */
    public static int manhattan(Node node) {
        int sum = 0;
        Node.initMat(node);

        for (int i = 0; i < row * col; i++) {
            if (node.blocks[i].num == 0) {
                continue;
            }
            if (node.blocks[i].num != (i + 1)) {
                int tmpRow = node.blocks[i].num / col;
                int tmpCol = node.blocks[i].num % col;
                int currRow = i / col;
                int currCol = i % col;

                int x = tmpRow - currRow;
                int y = tmpCol - currCol;

                int distance = Math.abs(x) + Math.abs(y);
                int cost = (node.blocks[i].color.trim().equalsIgnoreCase("Red")) ? distance * 30 : distance;
                sum += cost;
            }
        }

        return sum;
    }

    @Override
    public String toString() {
        String ans = "[";
        for (int i = 0; i < this.blocks.length; i++) {
            ans += this.blocks[i].num + " ";
        }
        ans += "]\n";
        return ans;
    }  
}
