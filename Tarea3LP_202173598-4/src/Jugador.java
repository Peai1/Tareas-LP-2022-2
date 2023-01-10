import java.util.ArrayList;
import java.util.List;

public class Jugador extends Personaje {    
    private List <Item> items_aplicados;

    /**
    * Constructor que inicializa el jugador con los atributos dados, haciendo uso del constructor de la clase padre Personaje
    * y además le crea una lista vacia de items
    * 
    * @param String nombre: nombre del personaje    
    * @param int dinero: dinero que posee el personaje al inicio
    * @param int hp_actual: vida que posee el personaje al inicio
    * @param int hp_total: vida total del personaje
    * @param int danio: daño del personaje
    * @param int defensa: defensa que posee el personaje
    *      
    */ 

    public Jugador(String nombre, int dinero, int hp_actual, int hp_total, int danio, int defensa){
        super(nombre,dinero,hp_actual,hp_total,danio,defensa);
        this.items_aplicados = new ArrayList<>();
    }

    //GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    public List<Item> getItems_aplicados(){
        return this.items_aplicados;
    }
    
    public void setItems_aplicados(List<Item> items_aplicados){
        this.items_aplicados = items_aplicados;
    }

    //FIN GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    /**
    * Funcion que permite al usuario/jugador ver sus estadisticas actuales              
    */ 

    void verEstado(){
        System.out.printf("--------------------------------%n");
        System.out.printf("---    ATRIBUTOS ACTUALES    ---%n");
        System.out.printf("--------------------------------%n");
        System.out.println("Nombre: "+getNombre());
        System.out.println("Dinero: "+getDinero());
        System.out.println("Vida actual del personaje: "+getHp_actual());
        System.out.println("Vida maxima del personaje: "+getHp_total());
        System.out.println("Daño: "+getDanio());
        System.out.println("Defensa: "+getDefensa());
    }

    /**
    * Funcion que permite al jugador/usuario ver los items que posee en su lista items_aplicados
    */ 

    void verItems(){
        if(getItems_aplicados().size() == 0)
            System.out.println("No tiene ningun item en su inventario");
        else {
            System.out.printf("--------------------------------%n");
            System.out.printf("---        INVENTARIO        ---%n");
            System.out.printf("--------------------------------%n");

            System.out.printf("----------------------------------------------------------------%n");
            System.out.printf("| %-4s | %-8s | %-8s | %-6s | %-6s |%n", "ITEM", " + VIDA ACTUAL "," + VIDA TOTAL ","+ DAñO","+ DEFENSA");
            System.out.printf("----------------------------------------------------------------%n");

            for(int i = 0; i < getItems_aplicados().size(); i++){
                System.out.printf("|  %01d   |        %02d       |       %02d       |   %02d   |     %02d    |%n", i+1, 
                                                                                            items_aplicados.get(i).getRecuperar_hp(), 
                                                                                            items_aplicados.get(i).getAumentar_hp_total()
                                                                                            ,items_aplicados.get(i).getAumentar_danio()
                                                                                            ,items_aplicados.get(i).getAumentar_defensa());
            }
        }
    }
}
