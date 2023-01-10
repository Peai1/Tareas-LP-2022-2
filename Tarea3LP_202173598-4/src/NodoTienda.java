import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class NodoTienda extends Nodo {
    private List <Item> inventario;

    /**
    * Constructor que inicializa el Nodo con una id unica haciendo uso del constructor de la clase padre.
    * Crea en un rango de 5 a 8 items aleatorios, los cuales los guarda en el inventario del NodoTienda para hacer su 
    * posterior uso al momento del usuario querer hacer la compra
    * 
    * @param int id: id del nodo
    *      
    */ 

    public NodoTienda(int id){
        super(id);
        int cantidadItems = (int)Math.floor(Math.random()*(8-5+1)+5);
        this.inventario = new ArrayList<>();
        for(int i = 0; i < cantidadItems; i++){
            Random rand = new Random();
            int precio = (int)Math.floor(Math.random()*(600-250+1)+250);  // (MAX-MIN+1)+MIN
            int recuperar_hp = rand.nextInt(10);
            int aumentar_hp_total = rand.nextInt(10); // bound: 0 a 20
            int aumentar_danio = rand.nextInt(5);
            int aumentar_defensa = rand.nextInt(5);

            Item it = new Item(precio,recuperar_hp,aumentar_hp_total,aumentar_danio,aumentar_defensa);
            inventario.add(it);
        }
    }

    //GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    public List<Item> getInventario(){
        return this.inventario;
    }

    public void setInventario(List<Item> inventario){
        this.inventario = inventario;
    }

    //FIN GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    /**
    * Función que aplica el item que compra el usuario en la posición indicada de la tienda
    * 
    * @param Jugador jugador: Usuario/jugador el cual compra el item  
    * @param int posicion: Entero con la posición de la tienda del item que se desea comprar   
    * 
    */ 

    void comprar(int posicion, Jugador jugador){
        
        Item ap = inventario.get(posicion-1);
        ap.aplicar(jugador);
        
    }

    /**
    * Función la cual le presenta al usuario una cantidad de items de entre 5 a 8, para que el jugador compre cuantos
      quiera dependiendo de su oro disponible y con ayuda de la función comprar y la función aplicar (de la clase Item), 
      aplica las estadisticas de los items que compra
    * 
    * @param Jugador jugador: Corresponde al usuario/jugador que se encuentra jugando el juego e interactua con el nodo tienda,
                              comprando los items
    * @param Scanner scannerInt: Scanner que se utilizará para obtener los inputs del usuario/jugador en los casos que sea necesario     
    * 
    */ 

    void interactuar(Jugador jugador, Scanner scannerInt){

        System.out.printf("---------------------------------%n");
        System.out.printf("     HAS LLEGADO A LA TIENDA     %n");
        System.out.printf("---        BIENVENIDO         ---%n");
        System.out.printf("---------------------------------%n");

        System.out.println("TIENE A SU DISPOSICION "+jugador.getDinero()+" DE ORO");

        System.out.printf("-------------------------------------------------------------------------%n");
        System.out.printf("| %-4s | %-6s | %-8s | %-8s | %-6s | %-6s |%n", "ITEM", "PRECIO", " + VIDA ACTUAL "," + VIDA TOTAL ","+ DAñO","+ DEFENSA");
        System.out.printf("-------------------------------------------------------------------------%n");

        for(int i = 0; i < inventario.size(); i++){

            System.out.printf("|  %01d   |   %01d   |        %02d       |       %02d       |   %02d   |     %02d    |%n", i+1, inventario.get(i).getPrecio(), inventario.get(i).getRecuperar_hp()
                                                                                        ,inventario.get(i).getAumentar_hp_total()
                                                                                        ,inventario.get(i).getAumentar_danio(),inventario.get(i).getAumentar_defensa());
            System.out.printf("-------------------------------------------------------------------------%n");
        }
        boolean compra = true;
        int pos;

        while (compra){
            System.out.println("SI DESEA COMPRAR ALGUN ITEM, INGRESE SU NUMERO, SI DESEA SALIR DE LA TIENDA INGRESE 0");
            pos = scannerInt.nextInt();
            if(pos == 0)
                compra = false;
            else if (inventario.get(pos-1).getPrecio() > jugador.getDinero()){
                System.out.println("NO TIENES SUFICIENTE DINERO PARA COMPRAR ESE ITEM");
            }
            else {
                comprar(pos,jugador);
                System.out.println("COMPRA REALIZADA CON EXITO, LE QUEDAN "+jugador.getDinero()+" DE ORO");
            }
        }
    
    }

}
