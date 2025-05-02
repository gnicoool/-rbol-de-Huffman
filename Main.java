/*
 * @author: Jackelyn Nicolle Girón Villancida 
 * Carné 24737
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        boolean continuar=true;
        
        while (continuar) {
            System.out.println("\n----- Menu  -----");
            System.out.println("1. Comprimir archivo");
            System.out.println("2. Descomprimir archivo");
            System.out.println("3. Salir");
            System.out.print("Ingrese la opcion que desea: ");

            int opcion = sc.nextInt();
            sc.nextLine(); 
            switch (opcion) {
                case 1:
                    comprimirArchivo();
                    break;
                case 2:
                    descomprimirArchivo();
                    break;
                case 3:
                    System.out.println("Saliendo del programa");
                    continuar = false;
                    return;
                default:
                    System.out.println("Ingrese una opcion valida, entre 1 y 6");
                    break;
            }
        }
        sc.close();
    }
    
    private static void comprimirArchivo() throws IOException {
        System.out.println("\n----- Comprimir archivo -----");
        System.out.print("Ingrese nombre del archivo a comprimir, incluyendo extension: ");
        String filename = sc.nextLine();

        String content = readTextFile(filename);
        HuffmanTree huffmanTree = HuffmanTree.buildHuffmanTree(content);
        Map<Character, String> huffmanCodes = huffmanTree.getCodeMap();/*Codigos huffman */
        String compressed = HuffmanTree.compress(content, huffmanCodes);
        String outputFilename = filename.replace(".txt", ".huff");
        saveCompressedFile(outputFilename, compressed, huffmanCodes);

       /*Tabla con los codigos */
        System.out.println("\nTabla de códigos Huffman:");
        huffmanTree.printCodes();

        System.out.println("\nArchivo comprimido guardado como: " + outputFilename);
    }

    private static void descomprimirArchivo() throws IOException, ClassNotFoundException {
        System.out.println("\n----- Descomprimir archivo -----");
        System.out.print("Ingrese lnombre del archivo (incluyendo la extension) a descomprimir: ");
        String filename = sc.nextLine();

        if (!filename.endsWith(".huff")) {
            throw new IOException("El archivo debe tener extensión .huff");
        }
        
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        Map<Character, String> huffmanCodes = (Map<Character, String>) ois.readObject();
        String compressedBits = (String) ois.readObject();
        ois.close();
        
        Node root = HuffmanTree.rebuildHuffmanTree(huffmanCodes);
        String decompressed = HuffmanTree.decompress(compressedBits, root);
        
        /*Guardar el archivo descomproimido */
        String outputFilename = filename.replace(".huff", ".decompressed.txt");
        try (PrintWriter out = new PrintWriter(outputFilename)) {
            out.print(decompressed);
        }

        System.out.println("El archivo descomprimido es: " + decompressed);
        
        System.out.println("\nSe guardo el archivo descomprimido como: " + outputFilename);
    }

    private static String readTextFile(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private static void saveCompressedFile(String filename, String compressedBits, 
                                         Map<Character, String> huffmanCodes) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(huffmanCodes);
        oos.writeObject(compressedBits);
        oos.close();
    }

}
