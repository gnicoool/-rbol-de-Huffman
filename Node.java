/*Node.java
 * @author: Jackelyn Girón
 * clase Node para el árbol de Huffman del libro Data Structures in Java for the Principled Programmer
 */
import java.util.Objects;

public class Node {
    int frequency;
    char ch;
    Node left, right;

    public Node(int f) {
        frequency = f;
        ch = '\0';
        left = right = null;
    }

    public Node(char c) {
        frequency = 1;
        ch = c;
        left = right = null;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Node node = (Node) other;
        return ch == node.ch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ch);
    }
}