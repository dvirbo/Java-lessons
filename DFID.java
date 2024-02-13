import java.util.HashSet;

import java.util.Set;

public class DFID {
    public static int col = Ex1.col;
    public static int row = Ex1.row;

    public static String DFID2(Block[] start, Block[] goal) {
        String result = "";
        Set<Node> open = new HashSet<>(); // open list
        Node stat = new Node(start); // the start Node
        boolean flag = false;

        System.out.println(start.toString());
        System.out.println(goal.toString());
        for (int depth = 1; !flag; depth++) {

            result = Limited_DFS(stat, goal, depth, open);

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

        if (equalArrays(start.arrBlock, goal)) { // TODO: add the BFS methods to a new sepetare class
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
                ans = move(start, emptyBlock, direction); // need to remove the open parameter here
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
            ans = left(start, emptyBlock);
        } else if (direction == "up" && start.pather != 'U') {

            ans = moveUp(start, emptyBlock);
        } else if (direction == "right" && start.pather != 'R') {
            ans = right(start, emptyBlock);
        } else if (direction == "down" && start.pather != 'D') {
            ans = moveDown(start, emptyBlock);
        }
        return ans;
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

        // Create a copy of the current state
        Node newState = new Node(currentState);

        // Calculate the destination position
        int from = position + col;

        // Check if the block can be moved up
        if (from < newState.arrBlock.length &&
                newState.arrBlock[from].color.equals("white") &&
                newState.arrBlock[from].moves > 0) {

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

        return null;
    }

    public static Node moveDown(Node currentState, int position) {
        // Check if the position is within the valid range
        if (position >= col * row + col) {
            return null;
        }

        // Create a copy of the current state
        Node newState = new Node(currentState);

        // Calculate the destination position
        int from = position - col; // TODO: pass the dest

        // Check if the block can be moved up
        if (from < newState.arrBlock.length &&
                newState.arrBlock[from].color.equals("white") &&
                newState.arrBlock[from].moves > 0) {

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
            newState.pather = 'D'; // TODO: pass the char
            if (newState.path == null) {
                newState.path = newState.arrBlock[position].num + "D";
            } else {
                newState.path += "-" + newState.arrBlock[position].num + "D";
            }

            return newState;
        }

        return null;
    }

    public static Node left(Node currentState, int position) {
        // Check if the position is within the valid range
        if (position % col == col - 1) {
            return null;
        }

        // Create a copy of the current state
        Node newState = new Node(currentState);

        // Calculate the destination position
        int from = position + 1; // TODO: pass the dest

        // Check if the block can be moved up
        if (from < newState.arrBlock.length &&
                newState.arrBlock[from].color.equals("white") &&
                newState.arrBlock[from].moves > 0) {

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
            newState.pather = 'L'; // TODO: pass the char
            if (newState.path == null) {
                newState.path = newState.arrBlock[position].num + "L";
            } else {
                newState.path += "-" + newState.arrBlock[position].num + "L";
            }

            return newState;
        }

        return null;

    }

    public static Node right(Node currentState, int position) {
        // Check if the position is within the valid range
        if (position % col == 0) {
            return null;
        }

        // Create a copy of the current state
        Node newState = new Node(currentState);

        // Calculate the destination position
        int from = position - col;

        // Check if the block can be moved up
        if (from < newState.arrBlock.length &&
                newState.arrBlock[from].color.equals("white") &&
                newState.arrBlock[from].moves > 0) {

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
            newState.pather = 'R'; // TODO: pass the char
            if (newState.path == null) {
                newState.path = newState.arrBlock[position].num + "R";
            } else {
                newState.path += "-" + newState.arrBlock[position].num + "R";
            }

            return newState;
        }

        return null;
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