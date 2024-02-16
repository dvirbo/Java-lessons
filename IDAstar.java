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
        int cost = Node.manhattan(head);

        while (cost != Integer.MAX_VALUE) {
            int minF = Integer.MAX_VALUE;
            stack.push(head);
            open_list.put(head.toString(), head);

            while (!stack.isEmpty()) {
                Node curr = stack.pop();

                if (curr.mark.equals("out")) {
                    //curr.mark = null;
                    open_list.remove(curr);
                } else {
                    curr.mark = "out";
                    stack.push(curr);
                    int emptyBlock = Node.findEmpty(curr);

                    String[] directions = { "left", "up", "right", "down" };

                    for (String direction : directions) {
                        ans = Node.move(curr, emptyBlock, direction);
                        if (ans == null) {
                            continue;
                        }
                        ans.f = ans.cost + Node.manhattan(ans); // init the f value
                        if (ans.f > cost) {
                            minF = Math.min(minF, ans.f);
                            continue;
                        }
                        for (Node node : open_list.values()) {
                            if (Block.CompareBlocks(node.blocks, ans.blocks)) {
                                if (node.mark.equals("out"))
                                    continue;
                                else {
                                    if (node.f > ans.f) {
                                        stack.remove(node);
                                        open_list.remove(node.toString());
                                    } else {
                                        continue;
                                    }
                                }

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
        }

    }
}
