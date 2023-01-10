import java.util.Random;
import java.util.Scanner;

public class NodoCombate extends Nodo {   
    private Personaje enemigo;

    /**
    * Constructor que inicializa el Nodo con una id unica haciendo uso del constructor de la clase padre.
    * Crea un enemigo aleatorio y lo asigna al nodo combate
    * 
    * @param int id: id del nodo
    *      
    */ 
 
    public NodoCombate (int id){
        super(id);
        Random rand = new Random();
        int enem = rand.nextInt(7);
        int dinero =(int)Math.floor(Math.random()*(90-50+1)+50);  // (MAX-MIN+1)+MIN
        int hp_actual = (int)Math.floor(Math.random()*(10-4+1)+4);  // (MAX-MIN+1)+MIN
        int hp_total = hp_actual;  
        int danio = (int)Math.floor(Math.random()*(5-2+1)+2);  // (MAX-MIN+1)+MIN
        int defensa = (int)Math.floor(Math.random()*(3-1+1)+1);  // (MAX-MIN+1)+MIN
        
        String[] enemigos = {"DELKOTEX", "Zancky", "ZoderxX","TM Frozty","Uppu","BELTRUM","ZANAX","FIXAPILAR"};
        
        this.enemigo = new Personaje(enemigos[enem],dinero,hp_actual,hp_total,danio,defensa);
    }

    //GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    public Personaje getEnemigo(){
        return this.enemigo;
    }

    public void setEnemigo(Personaje enemigo){
        this.enemigo = enemigo;
    }

    //FIN GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    /**
    * Función que informa al usuario el enemigo al cual se enfrenta, junto con sus atributos, y con ayuda de la función
    * combate, (de la clase Jugador) realiza el combate entre el usuario y enemigo, para luego mostrar por pantalla
    * si ganó el usuario o el enemigo
    * 
    * @param Jugador jugador: Corresponde al usuario/jugador que se encuentra jugando el juego y hace el combate contra el enemigo
    * @param Scanner scannerInt: Scanner que se utilizará para obtener los inputs del usuario/jugador en los casos que sea necesario
    *     
    */ 

    void interactuar(Jugador jugador, Scanner scannerInt){
        
        Jugador enemigo = new Jugador(getEnemigo().getNombre(),getEnemigo().getDinero(),getEnemigo().getHp_actual(),getEnemigo().getHp_actual(),getEnemigo().getDanio(),getEnemigo().getDefensa());
        System.out.println("AVANZAS AL SIGUIENTE NODO Y DEBES COMBATIR CON "+ getEnemigo().getNombre());
        System.out.println("SUS ESTADISTICAS SON LAS SIGUIENTES: ");
        System.out.println("VIDA: "+ getEnemigo().getHp_actual() + "/" + getEnemigo().getHp_total());
        System.out.println("DAÑO: "+ getEnemigo().getDanio());
        System.out.println("DEFENSA: "+ getEnemigo().getDefensa());
        
        jugador.combate(enemigo);

        if (enemigo.getHp_actual() <= 0){
            System.out.println("\nHAS DERROTADO A "+ enemigo.getNombre() + ", PUEDES CONTINUAR");
            System.out.println("TIENES " + jugador.getHp_actual() + "/" + jugador.getHp_total() + " DE VIDA");
            System.out.println("OBTIENES " + enemigo.getDinero() + " DE ORO DEL ENEMIGO");
            jugador.setDinero(jugador.getDinero() + enemigo.getDinero());
        }
        else {
            System.out.println("\nHAS SIDO DERROTADO POR "+ enemigo.getNombre());
            System.out.println("GAME OVER");
        }
    }
}
