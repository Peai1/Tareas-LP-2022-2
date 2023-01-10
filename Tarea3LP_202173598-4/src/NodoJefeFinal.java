import java.util.Random;
import java.util.Scanner;

public class NodoJefeFinal extends Nodo{
    private Personaje jefe;

    /**
    * Constructor que inicializa el Nodo con una id unica haciendo uso del constructor de la clase padre.
    * Crea un jefe final aleatorio, que es mas poderoso que los de los nodos combate, y lo asigna a jefe.
    * 
    * @param int id: id del nodo
    *      
    */ 

    public NodoJefeFinal(int id){
        
        super(id);
        Random rand = new Random();
        int enem = rand.nextInt(4);
        int hp_actual = (int)Math.floor(Math.random()*(19-12+1)+12);  // (MAX-MIN+1)+MIN
        int hp_total = hp_actual;  
        int danio = (int)Math.floor(Math.random()*(8-4+1)+4);  // (MAX-MIN+1)+MIN
        int defensa = (int)Math.floor(Math.random()*(4-3+1)+3);  // (MAX-MIN+1)+MIN
        String[] jefesfinales = {"ZANKY","FROZT","FIXAPILAR","ELJORDAN23","PAILITA"};
        this.jefe = new Personaje(jefesfinales[enem],0,hp_actual,hp_total,danio,defensa);   
    }

    //GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    public Personaje getJefe(){
        return this.jefe;
    }

    public void setResultado1(Personaje jefe){
        this.jefe = jefe;
    }

    //FIN GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    /**
    * Función que informa al usuario el jefe final al cual se enfrenta, junto con sus atributos, y con ayuda de la función
    * combate, (de la clase Jugador) realiza el combate entre el usuario y el jefe, para luego mostrar por pantalla
    * si ganó el usuario, es decir, ganó el juego, o el jefe final lo derrotó y perdió el juego.
    * 
    * @param Jugador jugador: Corresponde al usuario/jugador que se encuentra jugando el juego y hace el combate contra el enemigo
    * @param Scanner scannerInt: Scanner que se utilizará para obtener los inputs del usuario/jugador en los casos que sea necesario   
    *     
    */ 

    void interactuar(Jugador jugador, Scanner scannerInt){

        Jugador jefeFinal = new Jugador(getJefe().getNombre(),0,getJefe().getHp_actual(),getJefe().getHp_actual(),getJefe().getDanio(),getJefe().getDefensa());
        System.out.println("\nHAS LLEGADO AL JEFE FINAL, EL GRAN Y PODEROSO " + getJefe().getNombre());
        System.out.println("SUS ESTADISTICAS SON LAS SIGUIENTES: ");
        System.out.println("VIDA: "+ getJefe().getHp_actual() + "/" + getJefe().getHp_total());
        System.out.println("DAÑO: "+ getJefe().getDanio());
        System.out.println("DEFENSA: "+ getJefe().getDefensa());
        System.out.println("QUE COMIENZE EL COMBATE ");

        jugador.combate(jefeFinal);

        if (jefeFinal.getHp_actual() <= 0){
            System.out.println("\nHAS DERROTADO AL JEFE FINAL, FELICIDADES !!!");
            System.out.println("HAS GANADO :)");
            System.out.println("GRACIAS POR JUGAR!");
        }
        else{
            System.out.println("\nHAS SIDO DERROTADO POR "+ jefeFinal.getNombre());
            System.out.println("GAME OVER");
        }
        System.exit(0);
    }
}
