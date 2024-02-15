import java.util.Hashtable;

import javax.swing.plaf.TreeUI;

public class DFID {
    public static int col = Ex1.col;
    public static int row = Ex1.row;
    static int staticCounter = 0;

    public static String dfid(Block[] start, Block[] goal) {
        String result = "";
        String ans = "fail";
        int depth;
        Node head = new Node(start); // the head Node
        boolean flag = false;

        for (depth = 1; !flag; depth++) {
            Hashtable<String, Node> open_list = new Hashtable<>();

            result = Limited_DFS(head, goal, depth, open_list);

            if (result != "cutoff") {
                flag = true;

                return result;

            }

        }
        return ans;
    }

    /**
     * This method implements the Limited Depth First Search algorithm to find the
     * shortest path between two states a puzzle. The algorithm
     * uses a set data structure to keep track of the nodes that have been visited
     * and those that are still to be visited. The algorithm also uses a cost
     * variable to keep track of the total cost of the moves made so far.
     * 
     * @param currentNode the current node being visited
     * @param goal        the goal node
     * @param limit       the depth limit
     * @param open        the set of open nodes
     * @return the result of the search, which can be "cutoff" or "fail"
     */
    public static String Limited_DFS(Node currentNode, Block[] goal, int limit, Hashtable<String, Node> open_list) {

        if (CompareBlocks(currentNode.blocks, goal)) { // check if we find the desired block
            Ex1.path = currentNode.path; // goalequalBlocks(currentNode.blocks, goal)
            Ex1.costOfResult = currentNode.cost;
            return currentNode.path;
        } else if (limit == 0) {
            return "cutoff";
        } else {
            open_list.put(currentNode.toString(), currentNode);
            boolean isCutoff = false;
            int emptyBlock = findEmpty(currentNode);

            String[] directions = { "left", "up", "right", "down" };

            for (String direction : directions) {
                Node ans = move(currentNode, emptyBlock, direction);
                if (ans == null || open_list.contains(ans)) {
                    continue;
                }
                Ex1.count++; // how much Nodes was created
                String result = Limited_DFS(ans, goal, limit - 1, open_list);
                if (result == "cutoff") {
                    isCutoff = true;
                } else if (result != "fail") {
                    return result;
                }

            }

            if (Ex1.openList.equalsIgnoreCase("with open")) {
                System.out.println(open_list.toString());
            }

            open_list.remove(currentNode.toString());

            if (isCutoff) {
                return "cutoff";
            } else {
                return "fail";
            }
        }
    }

    public static boolean CompareBlocks(Block[] current, Block[] goal) {
        for (int i = 0; i < goal.length; i++) {
            if (current[i].num != goal[i].num) {
                return false;
            }
        }
        return true;
    }
    

    public static Node move(Node currentNode, int emptyBlock, String direction) {
        Node ans = null;
        if (direction.equals("left") && (currentNode.father == 'X' || currentNode.father != 'R')) {
            ans = moveLeft(currentNode, emptyBlock);
        } else if (direction.equals("up") && (currentNode.father == 'X' || currentNode.father != 'D')) {
            ans = moveUp(currentNode, emptyBlock);
        } else if (direction.equals("right") && (currentNode.father == 'X' || currentNode.father != 'L')) {
            ans = moveRight(currentNode, emptyBlock);
        } else if (direction.equals("down") && (currentNode.father == 'X' || currentNode.father != 'U')) {
            ans = moveDown(currentNode, emptyBlock);
        }
        return ans;
    }

    public static Node moveLeft(Node currentState, int position) {
        // Check if the position is within the valid range
        if ((position + 1) % col == 0) {
            return null;
        }

        // Calculate the destination position
        int from = position + 1;

        // check if the bolck is white and if he have enough moves
        if (currentState.blocks[from].color.equals("white") && currentState.blocks[from].moves == 0) {
            return null;
        }

        // // Create a copy of the current state
        Node newState = performSwap(currentState, position, from);

        // Calculate the cost of the move
        int cost = 0;
        if (newState.blocks[position].color.equals("white")) {
            cost = 1; // White cost 1
        } else {
            cost = 30; // Red cost 30
        }

        // Update the new state
        newState.cost += cost;
        newState.father = 'L';
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

        // check if the bolck is white and if he have enough moves
        if (currentState.blocks[from].color.equals("white") && currentState.blocks[from].moves == 0) {
            return null;
        }
        // // Create a copy of the current state
        Node newState = performSwap(currentState, position, from);
        // Calculate the cost of the move
        int cost = 0;
        if (newState.blocks[position].color.equals("white")) {
            cost = 1; // White cost 1
        } else {
            cost = 30; // Red cost 30
        }

        // Update the new state
        newState.cost += cost;
        newState.father = 'U';
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

        if (currentState.blocks[from].color.equals("white") && currentState.blocks[from].moves == 0) {
            return null;
        }

        // // Create a copy of the current state
        Node newState = performSwap(currentState, position, from);

        // Calculate the cost of the move
        int cost = 0;
        if (newState.blocks[position].color.equals("white")) {
            cost = 1; // White cost 1
        } else {
            cost = 30; // Red cost 30
        }

        // Update the new state
        newState.cost += cost;
        newState.father = 'R';
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

        if (currentState.blocks[from].color.equals("white") && currentState.blocks[from].moves == 0) {
            return null;
        }
        // // Create a copy of the current state
        Node newState = performSwap(currentState, position, from);
        // Calculate the cost of the move
        int cost = 0;
        if (newState.blocks[position].color.equals("white")) {
            cost = 1; // White cost 1
        } else {
            cost = 30; // Red cost 30
        }

        // Update the new state
        newState.cost += cost;
        newState.father = 'D';
        if (newState.path == null) {
            newState.path = newState.blocks[position].num + "D";
        } else {
            newState.path += "-" + newState.blocks[position].num + "D";
        }

        return newState;

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

}