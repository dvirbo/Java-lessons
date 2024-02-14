import java.util.HashSet;

import java.util.Set;

public class DFID {
    public static int col = Ex1.col;
    public static int row = Ex1.row;

    public static String dfid(Block[] start, Block[] goal) {
        String result = "";
        Set<Node> open = new HashSet<>(); // open list
        Node head = new Node(start); // the head Node
        boolean flag = false;

        for (int depth = 1; !flag; depth++) {

            result = Limited_DFS(head, goal, depth, open);

            if (result != "cutoff") {
                flag = true;

                return result;

            }

        }
        return "fail";

    }

    public static String Limited_DFS(Node start, Block[] goal, int limit, Set<Node> open) {
        String result = "";
        boolean isCutoff;
        Node ans;

        if (equalArrays(start.arrBlock, goal)) {
            Ex1.path = start.path;
            Ex1.costOfResult = start.cost;
            return start.path;
        } else if (limit == 0) {
            return "cutoff";
        } else {
            open.add(start);
            isCutoff = false;
            int emptyBlock = where0(start);

            String[] directions = { "left", "up", "right", "down" };

            for (String direction : directions) {
                ans = move(start, emptyBlock, direction);
                if (ans != null) {
                    if (!open.contains(ans)) {
                        Ex1.count++;
                    }
                    result = Limited_DFS(ans, goal, limit - 1, open);
                    if (result == "cutoff") {
                        isCutoff = true;
                    } else if (result != "fail") {
                        return result;
                    }
                }
            }

            open.remove(start);

            if (Ex1.openList.equalsIgnoreCase("with open")) {
                System.out.println(open.toString());
            }

            if (isCutoff) {
                return "cutoff";
            } else {
                return "fail";
            }
        }

    }

    /*
     * In order to get as uniform an output as possible, it was determined that the
     * order of creating the vertices with a common parent will be according to the
     * operator that created them in next order:
     * left, up, right, down.
     */
    public static Node move(Node start, int emptyBlock, String direction) {
        Node ans = null;
        if (direction == "left" && start.pather != 'L') {
            ans = moveLeft(start, emptyBlock);
        } else if (direction == "up" && start.pather != 'U') {
            ans = moveUp(start, emptyBlock);
        } else if (direction == "right" && start.pather != 'R') {
            ans = moveRight(start, emptyBlock);
        } else if (direction == "down" && start.pather != 'D') {
            ans = moveDown(start, emptyBlock);
        }
        return ans;
    }

    public static Node moveLeft(Node currentState, int position) {
        // Check if the position is within the valid range
        if (position % col == col - 1) {
            return null;
        }

        // Calculate the destination position
        int from = position + 1;

        // check if the bolck is white and if he have enough moves
        if (currentState.arrBlock[from].color.equals("white") && currentState.arrBlock[from].moves == 0) {
            return null;
        }

        // Create a copy of the current state
        Node newState = new Node(currentState);

        // Move the block left usuing temp bolck
        Block tempBlock = newState.arrBlock[position];
        newState.arrBlock[position] = newState.arrBlock[from];
        newState.arrBlock[from] = tempBlock;

        // Calculate the cost of the move
        int cost = 0;
        if (newState.arrBlock[position].color.equals("white")) {
            cost = 1; // White cost 1
        } else {
            cost = 30; // Red cost 30
        }

        // Update the new state
        newState.cost += cost;
        newState.pather = 'L';
        if (newState.path == null) {
            newState.path = newState.arrBlock[position].num + "L";
        } else {
            newState.path += "-" + newState.arrBlock[position].num + "L";
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
        if (currentState.arrBlock[from].color.equals("white") && currentState.arrBlock[from].moves == 0) {
            return null;
        }
        // Create a copy of the current state
        Node newState = new Node(currentState);

        // Move the block up
        Block tempBlock = newState.arrBlock[position];
        newState.arrBlock[position] = newState.arrBlock[from];
        newState.arrBlock[from] = tempBlock;

        // Calculate the cost of the move
        int cost = 0;
        if (newState.arrBlock[position].color.equals("white")) {
            cost = 1; // White cost 1
        } else {
            cost = 30; // Red cost 30
        }

        // Update the new state
        newState.cost += cost;
        newState.pather = 'U';
        if (newState.path == null) {
            newState.path = newState.arrBlock[position].num + "U";
        } else {
            newState.path += "-" + newState.arrBlock[position].num + "U";
        }

        return newState;

    }

    public static Node moveRight(Node currentState, int position) {
        // Check if the position is within the valid range
        if ((position + 1) % col == 0) {
            return null;
        }
        // Calculate the destination position
        int from = position - 1;

        if (currentState.arrBlock[from].color.equals("white") && currentState.arrBlock[from].moves == 0) {
            return null;
        }

        // Create a copy of the current state
        Node newState = new Node(currentState);

        // Move the block up
        Block tempBlock = newState.arrBlock[position];
        newState.arrBlock[position] = newState.arrBlock[from];
        newState.arrBlock[from] = tempBlock;

        // Calculate the cost of the move
        int cost = 0;
        if (newState.arrBlock[position].color.equals("white")) {
            cost = 1; // White cost 1
        } else {
            cost = 30; // Red cost 30
        }

        // Update the new state
        newState.cost += cost;
        newState.pather = 'R';
        if (newState.path == null) {
            newState.path = newState.arrBlock[position].num + "R";
        } else {
            newState.path += "-" + newState.arrBlock[position].num + "R";
        }

        return newState;
    }

    

    public static Node moveDown(Node currentState, int position) {
        // Check if the position is within the valid range
        if (position < col ) {
            return null;
        }
        // Calculate the destination position
        int from = position - col;

        if (currentState.arrBlock[from].color.equals("white") && currentState.arrBlock[from].moves == 0) {
            return null;
        }
        // Create a copy of the current state
        Node newState = new Node(currentState);

        // Move the block down
        Block tempBlock = newState.arrBlock[position];
        newState.arrBlock[position] = newState.arrBlock[from];
        newState.arrBlock[from] = tempBlock;

        // Calculate the cost of the move
        int cost = 0;
        if (newState.arrBlock[position].color.equals("white")) {
            cost = 1; // White cost 1
        } else {
            cost = 30; // Red cost 30
        }

        // Update the new state
        newState.cost += cost;
        newState.pather = 'D';
        if (newState.path == null) {
            newState.path = newState.arrBlock[position].num + "D";
        } else {
            newState.path += "-" + newState.arrBlock[position].num + "D";
        }

        return newState;

    }

    public static int where0(Node a) {
        int pos = 0;
        for (int i = 0; i < a.arrBlock.length; i++) {
            if (a.arrBlock[i].num == 0) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    public static boolean equalArrays(Block[] a, Block[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i].num != b[i].num)
                return false;
        }
        return true;
    }

}