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
        int threshold = Node.manhattan(head); // init the threshold

        stack.push(head);
        open_list.put(head.toString(), head);

        while (!stack.isEmpty()) {
            Node curr = stack.pop();

            if (curr.mark) {
                open_list.remove(curr.toString());
            } else {
                curr.mark = true;

                int emptyBlock = Node.findEmpty(curr);

                for (String direction : Ex1.directions) {
                    ans = Node.move(curr, emptyBlock, direction);
                    if (ans == null) {
                        continue;
                    }
                    ans.f = ans.cost + Node.manhattan(ans);

                    if (ans.f > threshold) {
                        // Determine minF here if needed
                    } else {
                        if (curr.mark && open_list.containsKey(ans.toString())) {
                            continue;
                        }

                        if (!curr.mark && open_list.containsKey(ans.toString())) {
                            Node existingNode = open_list.get(ans.toString());
                            if (existingNode.f > ans.f) {
                                stack.remove(existingNode);
                                open_list.remove(ans.toString());
                            } else {
                                continue;
                            }
                        }

                        if (Block.CompareBlocks(ans.blocks, goal)) {
                            Ex1.path = ans.path;
                            Ex1.costOfResult = ans.cost;
                            return;
                        }

                        Ex1.count++;
                        stack.push(ans);
                        open_list.put(ans.toString(), ans);
                    }
                }
            }
            if (Ex1.openList.equalsIgnoreCase("with open")) {
                System.out.println(open_list.toString());
            }
        }
    }
}
