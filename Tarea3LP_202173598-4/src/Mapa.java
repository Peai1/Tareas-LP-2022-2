import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.SortedSet;

public class Mapa {
    private int profundidad;
    private NodoInicial nodo_inicial;
    private Nodo nodo_actual;
    private SortedSet <Edge> edges;

    /**
    * Constructor que inicializa el mapa dado unos nodos de la variable edges, creando nodos segun el porcentaje que debeen poseer de acorde al enunciado
    * de la tarea.
    * 
    * @param int prof: profundidad del mapa 
    * @param SortedSet <Edge> edges: sorted set de edge que corresponden a los nodos del mapa
    *      
    */ 

    public Mapa (int prof, SortedSet <Edge> edges){
        Random rand = new Random();
        int cantNodos = 2, comparar = 0, j = 0;
        List <Nodo> Nodos = new ArrayList<>();
        Nodo nodo;

        for (Edge e : edges) {  // Ciclo para contar cuantos nodos hay en total
            if (e.x != comparar){
                cantNodos++;
            }
            comparar = e.x;
        }

        for(int i = 0; i < cantNodos; i++){  // Se crean el tipo de nodo aleatoriamente
            if (i == 0) {
                nodo = new NodoInicial(i);
                Nodos.add(nodo);
            }
            else if (i == cantNodos-1){
                nodo = new NodoJefeFinal(i);
                Nodos.add(nodo);
            }
            else{
                int prob = rand.nextInt(100);
                if (prob <= 10){
                    nodo = new NodoTienda(i);
                    Nodos.add(nodo);                   
                }
                else if (prob > 10 && prob <= 30){
                    nodo = new NodoEvento(i);
                    Nodos.add(nodo); 
                }
                else {    
                    nodo = new NodoCombate(i);
                    Nodos.add(nodo); 
                }
            }
        }

        while (j < cantNodos-1){  // Asigno los nodos a los que se puede ir 
            for (Edge e : edges){                
                if (e.x == j){
                    Nodos.get(j).agregarNodo(Nodos.get(e.y));
                    //System.out.println("Del nodo" + Nodos.get(j).getId() + " voy al " + Nodos.get(e.y).getId());
                }                
            }
            j++;
        }
        
        // Inicializo el nodo inicial y el nodo actual (que sera el inicial)
        profundidad = prof;
        this.edges = edges;
        nodo_inicial = new NodoInicial(0);
        nodo_inicial.setSiguientes_nodos(Nodos.get(0).getSiguientes_nodos());
        nodo_actual = nodo_inicial;                    
    }

    //GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    public int getProfundidad(){
        return this.profundidad;
    }

    public void setProfundidad(int profundidad){
        this.profundidad = profundidad;
    }

    public NodoInicial getNodo_inicial(){
        return this.nodo_inicial;
    }

    public void setNodo_inicial(NodoInicial nodo_inicial){
        this.nodo_inicial = nodo_inicial;
    }

    public Nodo getNodo_actual(){
        return this.nodo_actual;
    }

    public void setNodo_actual(Nodo nodo_actual){
        this.nodo_actual = nodo_actual;
    }

    public SortedSet <Edge> getEdges(){
        return this.edges;
    }

    public void setEdges(SortedSet <Edge> edges){
        this.edges = edges;
    }

    //FIN GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    /**
    * Funcion que permite al jugador ver el mapa completo de nodos del juego
    */ 

    void verMapa(){

        System.out.printf("--------------------------------%n");
        System.out.printf("              MAPA              %n");
        System.out.printf("--------------------------------%n");

        for (Edge e : edges) {
            System.out.printf("           (%d) -> (%d)\n", e.x, e.y);
        }

    }

    /**
    * Función que le muestra los nodos a los cuales puede avanzar el usuario, haciendo que el nodo actual sea el cual decida avanzar,
      para luego hacer que el jugador interactue con el nodo al cual avanzó

      @param Jugador jugador: Corresponde al usuario/jugador que se encuentra jugando el juego y avanza a traves del mapa
      @param Scanner scannerInt: variable scanner para poder leer el input del usuario 

    */ 

    void avanzar(Jugador jugador, Scanner scannerInt){
        int nodo;
        System.out.println("Te encuentras en el nodo: "+ getNodo_actual().getId());
        System.out.println("Selecciona a cual de los siguientes nodos quieres avanzar: ");

        for (int z = 0; z < getNodo_actual().getSiguientes_nodos().size(); z++){
            System.out.println(getNodo_actual().getSiguientes_nodos().get(z).getId()); 
        }
        nodo = scannerInt.nextInt();

        for (int i = 0; i < getNodo_actual().getSiguientes_nodos().size(); i++){
            if(getNodo_actual().getSiguientes_nodos().get(i).getId() == nodo){
                setNodo_actual(getNodo_actual().getSiguientes_nodos().get(i));
            }
        }
        
        getNodo_actual().interactuar(jugador, scannerInt);
        
    }
}
