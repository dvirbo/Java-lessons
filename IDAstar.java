import java.util.Hashtable;
import java.util.Stack;

public class IDAstar {
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
        Stack<Node> stack = new Stack<>();
        Hashtable<String, Node> open_list = new Hashtable<>();
        int threshold = Node.manhattan(head); // cost threshold
        Node ans;
        int minF;
        int emptyBlock;

        while (threshold != Integer.MAX_VALUE) {
            minF = Integer.MAX_VALUE;
            stack.push(head);
            open_list.put(head.toString(), head);
            head.visit = false;

            while (!stack.isEmpty()) {
                Node curr = stack.pop();

                if (curr.visit) { // == true
                    open_list.remove(curr.toString()); 
                } else {
                    curr.visit = true;
                    stack.push(curr);

                    emptyBlock = Node.findEmpty(curr);

                    for (String direction : Ex1.directions) {
                        ans = Node.move(curr, emptyBlock, direction);
                        if (ans == null) {
                            continue;
                        }
                        ans.f = ans.cost + Node.manhattan(ans); 

                        if (ans.f > threshold) { 
                            minF = Math.min(minF, ans.f);
                            continue;
                        }

                        Node existingNode = open_list.get(ans.toString());
                        if (existingNode != null && existingNode.visit) { // visit == true
                            continue;
                        }
                        if (existingNode != null && !existingNode.visit ) { // visit == false
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
                if (Ex1.openList.equalsIgnoreCase("with open")) {
                    System.out.println(open_list.toString());
                }
            }
            threshold = minF;
        }
    }
}