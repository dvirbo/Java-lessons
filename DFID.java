import java.util.Hashtable;

public class DFID {
    public static int col = Ex1.col;
    public static int row = Ex1.row;

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

        if (Block.CompareBlocks(currentNode.blocks, goal)) { // check if we find the desired block
            Ex1.path = currentNode.path; // goalequalBlocks(currentNode.blocks, goal)
            Ex1.costOfResult = currentNode.cost;
            return currentNode.path;
        } else if (limit == 0) {
            return "cutoff";
        } else {
            open_list.put(currentNode.toString(), currentNode);
            boolean isCutoff = false;
            int emptyBlock = Node.findEmpty(currentNode);

            for (String direction : Ex1.directions) {
                Node ans = Node.move(currentNode, emptyBlock, direction);
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


            if (Ex1.isOpen) {
                Node.printHashtable(open_list);
            }
            open_list.remove(currentNode.toString());

            if (isCutoff) {
                return "cutoff";
            } else {
                return "fail";
            }
        }
    }

}