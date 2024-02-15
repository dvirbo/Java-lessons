import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
    /**
 * Compares two nodes based on their f values, creation iteration, and direction.
 * If the f values are different, the node with the higher f value is considered to be greater.
 * If the f values are the same, the node with the lower creation iteration is considered to be greater.
 * If the creation iterations are the same, the node with the higher direction is considered to be greater.
 *
 * @param node1 the first node to compare
 * @param node2 the second node to compare
 * @return a negative integer, zero, or a positive integer as the first node is less than, equal to, or greater than the second node
 */
@Override
public int compare(Node node1, Node node2) {
    if (node1.f != node2.f) {
        return Integer.compare(node1.f, node2.f);
    } else if (node1.creationIteration != node2.creationIteration) {
        return Integer.compare(node1.creationIteration, node2.creationIteration);
    } else {
        return Integer.compare(node1.direction, node2.direction);
    }
}


}