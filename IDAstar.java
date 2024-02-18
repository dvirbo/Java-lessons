import java.util.Hashtable;
import java.util.Stack;

public class IDAstar {
    public static int col = Ex1.col;
    public static int row = Ex1.row;

    /**
     * The IDA* search algorithm for solving the sliding tile puzzle.
     *
     * @param start The starting configuration of the puzzle as an array of Block
     *              objects.
     * @param goal  The goal configuration of the puzzle as an array of Block
     *              objects.
     */
    public static void idastar(Block[] start, Block[] goal) {
        Node head = new Node(start); // the head Node
        Node ans;
        Stack<Node> stack = new Stack<>();
        Hashtable<String, Node> open_list = new Hashtable<>();
        int cost = Node.manhattan(head); // cost threshold

        while (cost != Integer.MAX_VALUE) {
            stack.push(head);
            int minF = Integer.MAX_VALUE;

            while (!stack.isEmpty()) {
                Node curr = stack.pop();

                if (curr.mark) { // if we already checked this node
                    open_list.remove(curr.toString());
                } else {
                    curr.mark = true;
                    int emptyBlock = Node.findEmpty(curr);

                    for (String direction : Ex1.directions) {
                        ans = Node.move(curr, emptyBlock, direction);
                        if (ans == null) {
                            continue;
                        }
                        ans.f = ans.cost + Node.manhattan(ans); // init the f value

                        if (ans.f > cost) {
                            minF = Math.min(minF, ans.f);
                            continue;
                        }

                        if (open_list.containsKey(ans.toString())) {
                            Node existingNode = open_list.get(ans.toString());
                            if (existingNode.f > ans.f) {
                                stack.remove(existingNode);
                                open_list.remove(ans.toString());
                            } else {
                                continue;
                            }
                        }

                        Ex1.count++;
                        if (Block.CompareBlocks(ans.blocks, goal)) {
                            Ex1.path = ans.path;
                            Ex1.costOfResult = ans.cost;
                            return;
                        }

                        stack.push(ans);
                        open_list.put(ans.toString(), ans);

                    }
                }
                if (Ex1.openList.equalsIgnoreCase("with open")) {
                    System.out.println(open_list.toString());
                }
            }
            cost = minF;
        }
    }
}
