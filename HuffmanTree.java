/*HuffmanTree.java
 * @author: Jackelyn Girón
 */
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree implements Comparable<HuffmanTree> {
    Node root;
    int totalWeight;/* Es la suma de las frecuencias */

    public HuffmanTree(Node e) {
        root = e;
        totalWeight = e.frequency;
    }

    /*Constructor para combinar dos arboles
     * @param left: el arbol izquierdo
     * @param right: el arbol derecho
     */
    public HuffmanTree(HuffmanTree left, HuffmanTree right) {
        this.root = new Node(left.totalWeight + right.totalWeight);
        this.root.left = left.root;
        this.root.right = right.root;
        this.totalWeight = left.totalWeight + right.totalWeight;
    }

    @Override
    public int compareTo(HuffmanTree other) {
        return this.totalWeight - other.totalWeight;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        HuffmanTree other = (HuffmanTree) that;
        return totalWeight == other.totalWeight && root.equals(other.root);
    }

    /*Para los codigos de Huffman */
    public void printCodes() {
        printCodes(root, "");
    }

    private void printCodes(Node node, String code) {
        if (node.isLeaf()) {
            System.out.println(node.ch + ": " + code);
            return;
        }
        printCodes(node.left, code + "0");
        printCodes(node.right, code + "1");
    }

    public Map<Character, String> getCodeMap() {
        Map<Character, String> codeMap = new HashMap<>();
        buildCodeMap(root, "", codeMap);
        return codeMap;
    }

    private void buildCodeMap(Node node, String code, Map<Character, String> codeMap) {
        if (node.isLeaf()) {
            codeMap.put(node.ch, code);
            return;
        }
        buildCodeMap(node.left, code + "0", codeMap);
        buildCodeMap(node.right, code + "1", codeMap);
    }

    /*Metodo para crear arbol de huffman de un texto */
    public static HuffmanTree buildHuffmanTree(String text) {
        /*Frecuencias de los caracteres */
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        /*Crea lista prioritaria de los arbioles */
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            Node node = new Node(entry.getKey());
            node.frequency = entry.getValue();
            trees.add(new HuffmanTree(node));
        }

        /*Combina los arboles */
        while (trees.size() > 1) { 
            HuffmanTree left = trees.poll();
            HuffmanTree right = trees.poll();
            trees.add(new HuffmanTree(left, right));
        }
        return trees.poll();
    }

    public static String compress(String text, Map<Character, String> huffmanCodes) {
        StringBuilder compressed = new StringBuilder();
        for (char c : text.toCharArray()) {
            compressed.append(huffmanCodes.get(c));
        }
        return compressed.toString();
    }

    /*Metodo para reconstruir el arlbor de huffman basado en los codigos */
    public static Node rebuildHuffmanTree(Map<Character, String> huffmanCodes) {
        Node root = new Node(0);
        
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            Node current = root;
            String code = entry.getValue();
            
            for (int i = 0; i < code.length(); i++) {
                char bit = code.charAt(i);
                
                if (bit == '0') {
                    if (current.left == null) {
                        current.left = new Node(0);
                    }
                    current = current.left;
                } else {
                    if (current.right == null) {
                        current.right = new Node(0);
                    }
                    current = current.right;
                }
            }
            current.ch = entry.getKey();
        }
        return root;
    }

    public static String decompress(String compressedBits, Node root) {
        StringBuilder decompressed = new StringBuilder();
        Node current = root;
        
        for (int i = 0; i < compressedBits.length(); i++) {
            char bit = compressedBits.charAt(i);
            
            if (bit == '0') {
                current = current.left;
            } else {
                current = current.right;
            }
            
            if (current.isLeaf()) {
                decompressed.append(current.ch);
                current = root;
            }
        }
        return decompressed.toString();
    }
}
