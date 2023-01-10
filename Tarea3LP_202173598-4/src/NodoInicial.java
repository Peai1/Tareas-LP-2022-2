import java.util.Scanner;

public class NodoInicial extends Nodo{

    /**
    * Constructor que inicializa el Nodo con una id unica haciendo uso del constructor de la clase padre.
    * 
    * @param int id: id del nodo
    *      
    */ 

    public NodoInicial(int id){
        super(id);
    }

    /**
    * Función que le muestra una pequeña introducción al jugador, indicando como deberá jugar al juego y cuales son las condiciones
      para poder ganar
    * 
    * @param Jugador jugador: Corresponde al usuario/jugador que jugará el juego e interactua con el nodo inicial    
    * @param Scanner scannerInt: Scanner que se utilizará para obtener los inputs del usuario/jugador en los casos que sea necesario
    *  
    */ 
  

    void interactuar(Jugador jugador, Scanner scannerInt){
        System.out.println("### Bienvenido a JavaQuest " + jugador.getNombre() + " ###");
        System.out.println("   * A partir de este momento se desplegará un menu de opciones, siempre que sea posible, para que escojas una acción a realizar.*");
        System.out.println("   * En este juego deberas avanzar a traves de un mapa de nodos el cual puedes ver con la opcion 1).*");
        System.out.println("   * Existen 4 tipos de nodos a los que puedes llegar una vez decidas avanzar.*");
        System.out.println("   * Puedes llegar a una Tienda en donde podras comprar tantos items como quieras! *");
        System.out.println("   * Tambien en el camino te puedes encontrar a algun digno oponente el cual debes derrotar para continuar con tu camino.*");
        System.out.println("   * Habran algunos nodos especiales en los cuales deberas elegir que accion realizar dado un cierto contexto.*");
        System.out.println("   * Y si sobrevives, llegaras a combatir al Jefe Final, el cual debes derrotar para poder ganar el juego! D: *");
        System.out.println("   * Si tu vida llega a 0, automaticamente pierdes el juego :( *");
        System.out.println("   * Una vez finalizado el la actividad del nodo al que llegaste, puedes ver tus estadisticas, el mapa o tus items*");
        System.out.println("   * Mucha suerte y que disfrutes el juego !! *");        
    }
}
