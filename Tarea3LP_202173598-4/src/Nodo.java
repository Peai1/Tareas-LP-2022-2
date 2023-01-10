import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract public class Nodo {
    private int id;
    private List <Nodo> siguientes_nodos;

    /**
    * Constructor que inicializa el Nodo con una lista vacia de nodos correspondientes a los siguientes que puede avanzar desde el mismo,
    * como tambien el id del nodo
    * 
    * @param int id: id del nodo
    *      
    */ 

    public Nodo(int id){
        this.siguientes_nodos = new ArrayList<>();
        this.id = id;
    }

    //GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public List <Nodo> getSiguientes_nodos(){
        return this.siguientes_nodos;
    }

    public void setSiguientes_nodos(List <Nodo> siguientes_nodos){
        this.siguientes_nodos = siguientes_nodos;
    }

    //FIN GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    /**
    * Función que será escrita en las clases hijo de esta clase abstracta, para hacer que interactue segun el nodo al cual avanza el jugador.
    * 
    * @param Jugador jugador: Usuario/jugador el cual interactuará con el nodo en cuestión
    * @param Scanner scannerInt: Scanner que se utilizará para obtener los inputs del usuario/jugador en los casos que sea necesario
    * 
    */ 

    abstract void interactuar(Jugador jugador, Scanner scannerInt);

    /**
    * Función que agrega un nodo a la lista de siguientes nodos
    * 
    * @param Nodo nodo: Corresponde al nodo que sera agregado a la lista de siguientes nodos    
    *  
    */ 

    void agregarNodo(Nodo nodo){

        getSiguientes_nodos().add(nodo);

    }


}
