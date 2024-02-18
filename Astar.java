import java.util.Hashtable;
import java.util.PriorityQueue;

/*
 * The A* algorithm selects the node with the lowest f value from the open list for expansion at each iteration. 
 * This ensures that the most promising (i.e., lowest-cost) nodes are explored first, potentially leading to faster convergence towards the goal.
 */
public class Astar {
    public static int col = Ex1.col;
    public static int row = Ex1.row;



    /**
     * The A* algorithm selects the node with the lowest f value from the open list
     * for expansion at each iteration.
     * This ensures that the most promising (i.e., lowest-cost) nodes are explored
     * first, potentially leading to faster convergence towards the goal.
     * 
     * @param start the starting node
     * @param goal  the goal node
     */
    public static void astar(Block[] start, Block[] goal) {

        Node head = new Node(start); // the head Node
        Node ans;
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<>(new NodeComparator());
        Hashtable<String, Node> open_list = new Hashtable<>();
        Hashtable<String, Node> close_list = new Hashtable<>();
        // The open list - stores nodes that have been generated but not yet explored.
        // The close list - keeps track of the nodes that have already been explored.
        nodePriorityQueue.add(head);
        open_list.put(head.toString(), head);
        while (!nodePriorityQueue.isEmpty()) {
            Node curr = nodePriorityQueue.remove();
            open_list.remove(curr);
            if (Block.CompareBlocks(curr.blocks, goal)) { // check of the blocks are equals
                Ex1.path = curr.path;
                Ex1.costOfResult = curr.cost;
                return;
            }
            close_list.put(curr.toString(), curr);
            int emptyBlock = Node.findEmpty(curr);


            for (String direction : Ex1.directions) {
                ans = Node.move(curr, emptyBlock, direction);
                if (ans == null || open_list.contains(ans) || close_list.contains(ans)) {
                    continue;
                }
                ans.f = ans.cost + Node.manhattan(ans);
                ans.creationIteration = Ex1.count;
                nodePriorityQueue.add(ans);
                open_list.put(ans.toString(), ans);
                Ex1.count++; // how much Nodes was created

            }
            if (Ex1.openList.equalsIgnoreCase("with open")) {
                System.out.println(open_list.toString());

            }
        }

    }

}
