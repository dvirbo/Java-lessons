import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

public class DFbnb {

    /**
     * Depth-First Brute-Force Branch and Bound (DFbnb) algorithm for solving the
     * sliding puzzle problem.
     *
     * @param start - the initial configuration of the puzzle
     * @param goal  - the goal configuration of the puzzle
     */
    public static void dfbnb(Block[] start, Block[] goal) {
        Node head = new Node(start); // the head Node
        Stack<Node> stack = new Stack<>();
        Hashtable<String, Node> open_list = new Hashtable<>();
        List<Node> childs = new ArrayList<>();
        int trash_hold = Math.min(Integer.MAX_VALUE, factorial((Ex1.row * Ex1.col) - 1));
        Node ans = null;
        int emptyBlock;
        open_list.put(head.toString(), head);
        stack.push(head);
        while (!stack.empty()) {
            Node curr = stack.pop();
            if (curr.visit) { // if the node already has been inside the stack
                if (Ex1.isOpen) {
                    Node.printHashtable(open_list);
                }
                open_list.remove(curr.toString()); // remove from hash
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
                    ans.creationIteration = Ex1.count;
                    childs.add(ans);
                }
                Ex1.count += childs.size();// update the node ammount that created
                Collections.sort(childs, new NodeComparator());
                List<Node> copy_list = new ArrayList<>(childs); // sort the nodes according to their f values
                for (Node node : childs) {
                    if (node.f >= trash_hold) {
                        childs.subList(childs.indexOf(node), childs.size()).clear();
                    } else if (open_list.containsKey(node.toString())
                            && open_list.get(node.toString()).visit) { // visit == true
                                if (Ex1.isOpen) {
                            Node.printHashtable(open_list);
                        }
                        copy_list.remove(node);
                    } else if (open_list.containsKey(node.toString())
                            && !open_list.get(node.toString()).visit) { // visit == false
                        if (node.f >= open_list.get(node.toString()).f) {
                            copy_list.remove(node);
                        } else {
                            stack.remove(open_list.get(node.toString()));
                        }
                    } else if (Block.CompareBlocks(node.blocks, goal)) {
                        trash_hold = node.f;
                        Ex1.path = node.path;
                        Ex1.costOfResult = curr.cost;
                        return;
                    }

                }

                List<Node> ans_list = new ArrayList<>();
                for (Node node : copy_list) {
                    if (childs.contains(node)) {
                        ans_list.add(node);
                    }
                }
                Collections.reverse(ans_list);
                for (Node node : ans_list) {
                    stack.push(node);
                    open_list.put(node.toString(), node);

                }
            }

        }

    }
    
    public static int factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }
}
