import java.util.Random;

public class Personaje {    
    private String nombre;
    private int dinero;
    private int hp_actual;
    private int hp_total;
    private int danio;
    private int defensa;

    /**
    * Constructor que inicializa el personaje con los atributos dados
    * 
    * @param String nombre: nombre del personaje    
    * @param int dinero: dinero que posee el personaje al inicio
    * @param int hp_actual: vida que posee el personaje al inicio
    * @param int hp_total: vida total del personaje
    * @param int danio: daño del personaje
    * @param int defensa: defensa que posee el personaje
    *      
    */ 

    public Personaje(String nombre, int dinero, int hp_actual, int hp_total, int danio, int defensa){
        this.nombre = nombre;
        this.dinero = dinero;
        this.hp_actual = hp_actual;
        this.hp_total = hp_total;
        this.danio = danio;
        this.defensa = defensa;
    }

    //GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public int getDinero(){
        return this.dinero;
    }

    public void setDinero(int dinero){
        this.dinero = dinero;
    }

    public int getHp_actual(){
        return this.hp_actual;
    }

    public void setHp_actual(int hp_actual){
        this.hp_actual = hp_actual;
    }

    public int getHp_total(){
        return this.hp_total;
    }

    public void setHp_total(int hp_total){
        this.hp_total = hp_total;
    }

    public int getDanio(){
        return this.danio;
    }

    public void setDanio(int danio){
        this.danio = danio;
    }

    public int getDefensa(){
        return this.defensa;
    }

    public void setDefensa(int defensa){
        this.defensa = defensa;
    }

    //FIN GETTERS Y SETTERS

    /**
    * Funcion que realiza el combate entre dos personajes cuando el jugador se encuentre en un nodo Combate o nodo JefeFinal
    * 
    * @param Jugador jugador: Corresponde al enemigo contra el cual se realiza el combate con el jugador/usuario
    * @param Scanner scannerInt: Scanner que se utilizará para obtener los inputs del usuario/jugador en los casos que sea necesario
    *      
    */ 

    void combate(Jugador jugador){ // jugador es enemigo

        Random rand = new Random();
        int prob = rand.nextInt(100);  // un numero entre 0 y 100
        int perdida;
        Boolean usuario = true, enemigo = true;
   
        if (prob <= 50){
            enemigo = false; // ataca usuario
            System.out.println("ERES EL PRIMERO EN ATACAR\n");
        }
        else {
            usuario = false; // ataca enemigo
            System.out.println(jugador.getNombre() + " ES EL PRIMERO EN ATACAR\n");
        }

        while ( getHp_actual() > 0 && jugador.getHp_actual() > 0){

            if (usuario){ // Ataca usuario

                System.out.println("##### TU TURNO DE ATACAR #####");
                perdida = (getDanio() - jugador.getDefensa());
                if (perdida < 0){
                    perdida = 0;
                }
                jugador.setHp_actual(jugador.getHp_actual() - (perdida));;

                System.out.println("ATACAS Y EL ENEMIGO PIERDE "+ (perdida) + " DE VIDA");

                if (jugador.getHp_actual() < 0)
                    System.out.println("** VIDA DEL ENEMIGO "+ 0 + "/" + jugador.getHp_total() +  " **");                
                else
                    System.out.println("** VIDA DEL ENEMIGO "+ jugador.getHp_actual() + "/" + jugador.getHp_total() +" **\n");

                usuario = false;
                enemigo = true;
            }    
            else if (enemigo) { // Ataca enemigo
                System.out.println("##### TURNO DE ATAQUE DE "+ jugador.getNombre() + " #####");
                perdida = (jugador.getDanio() - getDefensa());
                if (perdida < 0){
                    perdida = 0;
                }
                setHp_actual(getHp_actual() - (perdida));
                System.out.println( jugador.getNombre()+ " ATACA Y PIERDES "+ (perdida) + " DE VIDA ");
                
                if (getHp_actual() < 0)
                    System.out.println("** VIDA ACTUAL "+ 0 + "/" + getHp_total() + " **");                
                else
                    System.out.println("** VIDA ACTUAL "+ getHp_actual() + "/" + getHp_total() + " **\n");
                
                enemigo = false;
                usuario = true;
            }
        }
    }
}
