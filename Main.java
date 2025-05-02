/*
 * @author: Jackelyn Nicolle Girón Villancida 
 * Carné 24737
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean continuar=true;
        Scanner sc = new Scanner(System.in);
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
                    //comprimir
                    break;
                case 2:
                    //descomprimir 
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
}
