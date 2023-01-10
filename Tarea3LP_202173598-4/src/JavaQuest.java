import java.util.Scanner; 
import java.util.SortedSet;

public class JavaQuest {
    public static void main(String[] args) throws Exception {

        Scanner scannerString = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
        int opcion, prof;
        String name;

        System.out.println("### ¡BIENVENIDO/A A JAVA QUEST ### \n");
        System.out.println("Para comenzar elige el nombre de tu personaje:");
        name = scannerString.nextLine();
        System.out.println("Ingresa el numero de cuanto quieres que sea la profundidad del mapa:");
        prof = scannerInt.nextInt();

        SortedSet <Edge> edges = GraphGenerator.Generar(prof);

        Jugador jugador = new Jugador(name,500,20,20,5,1);
        Mapa map = new Mapa(edges.size(),edges);

        map.getNodo_actual().interactuar(jugador,scannerInt);

        while (jugador.getHp_actual() > 0) {
            System.out.println("\n1) Ver Mapa");
            System.out.println("2) Ver Estadisticas");
            System.out.println("3) Ver Items");
            System.out.println("4) Avanzar");

            System.out.println("\nEscoja una opción:");
            opcion = scannerInt.nextInt();

            if (opcion == 1)
                map.verMapa();            
            else if (opcion == 2)
                jugador.verEstado();            
            else if (opcion == 3)
                jugador.verItems();        
            else if (opcion == 4)                          
                map.avanzar(jugador,scannerInt);            
            else
                System.out.println("Por favor ingrese una opción válida");                       
        }
        scannerInt.close();
        scannerString.close();
    }
}


