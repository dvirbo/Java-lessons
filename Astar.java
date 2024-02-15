import java.util.Hashtable;
import java.util.PriorityQueue;

public class Astar {
    public static int col = Ex1.col;
    public static int row = Ex1.row;

    public static int manhattan(Node node) {
        int sum = 0;
        Node.initMat(node);

        for (int i = 0; i < row * col; i++) {
            if (node.blocks[i].num == 0) {
                continue;
            }
            if (node.blocks[i].num != (i + 1)) {
                int tmpRow = node.blocks[i].num / col;
                int tmpCol = node.blocks[i].num % col;
                int currRow = i / col;
                int currCol = i % col;

                int x = tmpRow - currRow;
                int y = tmpCol - currCol;

                int distance = Math.abs(x) + Math.abs(y);
                int cost = (node.blocks[i].color.trim().equalsIgnoreCase("Red")) ? distance * 30 : distance;
                sum += cost;
            }
        }

        return sum;
    }

    public static void astar(Block[] start, Block[] goal) {

        Node head = new Node(start); // the head Node
        Node ans;
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<>(new NodeComparator());
        Hashtable<String, Node> open_list = new Hashtable<>();
        Hashtable<String, Node> close_list = new Hashtable<>();

        nodePriorityQueue.add(head);
        open_list.put(head.toString(), head);
        while (!nodePriorityQueue.isEmpty()) {
            Node curr = nodePriorityQueue.remove();
            open_list.remove(curr);
            if (Block.CompareBlocks(curr.blocks, goal)) {
                Ex1.path = curr.path;
                Ex1.costOfResult = curr.cost;
                return;
            }
            close_list.put(curr.toString(), curr);
            int emptyBlock = Node.findEmpty(curr);

            String[] directions = { "left", "up", "right", "down" };

            for (String direction : directions) {
                ans = Node.move(curr, emptyBlock, direction);
                if (ans == null || open_list.contains(ans) || close_list.contains(ans)) {
                    continue;
                }
                ans.f = ans.cost + manhattan(ans);
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
