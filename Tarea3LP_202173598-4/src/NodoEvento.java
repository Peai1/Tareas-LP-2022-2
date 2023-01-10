import java.util.Random;
import java.util.Scanner;

public class NodoEvento extends Nodo{
    private String descripcion;
    private String alternativa1;
    private String alternativa2;
    private Item resultado1;
    private Item resultado2;
    private String resultado1String;
    private String resultado2String;

    /**
    * Constructor que inicializa el Nodo con una id unica haciendo uso del constructor de la clase padre.
    * Elige una descripcion al azar, a lo que le asigna sus correspondientes alternativas, resultados y un string resultado que sirve para
    * imprimir por pantalla al usuario lo que sucedió dada su elección
    * 
    * @param int id: id del nodo
    *      
    */ 

    public NodoEvento(int id){
        super(id);
        Random rand = new Random();
        int opcion = rand.nextInt(6);
        Item statsa, statsb;

        String[] desc = {"Te encuentras a un pequeño tejón herido a mitad de camino con su carroza dañada a una orilla del rio y sientes pena por el", 
        "Te encuentras una indefensa princesa que se encuentra en apuros al ser acorralada por dos ladrones", 
        "Del cielo observas que un meteorito cae a la orilla del camino, puede ser peligroso",
        "Te encuentras con dos bandalos armados que buscan hacerte daño. Si le entregas 100 de oro te dejaran seguir tu camino tranquilo",
        "En tu camino te encuentras a una abuelita indefensa sentada con lo que pareciera ser una lesión en la rodilla pero con una bolsa con una suculenta cantidad de dinero",
        "Estás muerto de hambre y en el camino te encuentras un rico pollo asado con papas fritas, aunque ves a otra persona que de igual forma está deseando comer el plato de comida ya que no ha comido hace dos días",
        "Te encuentra con un bufon el que te invita a jugar un juego el cual debes elegir en cual de su mano se encuentra la moneda, si ganas, recibes una recompensa, pero si pierdes, el buffon quiere algo a cambio",
        };

        String[][] alt = {
            {"Lo ayudas","Sigues de largo"},
            {"La defiendes", "Pasas por un lado"},
            {"Te acercas a investigar","Sigues tu camino"},
            {"Les das 100 de oro","Te enfrentas a ellos"},
            {"Te acercas a ayudarla","Piensas que ese oro te vendria de buena ayuda para tu aventura"},
            {"Le dejas el plato de comida","Te dejas el plato de comida para ti mismo"},
            {"Escoges la Mano izquierda","Eliges la mano derecha"}
        };
      
        String[][] recomp = {
            {"Con tu ayuda logran reparar la carroza pero no te das cuenta que al momento de irse, te roba 50 de oro", "Sigues con tu camino"},
            {"Derrotas a los ladrones y pierdes 3 de vida pero recibes 30 de oro de parte de la princesa","Sigues con tu camino"},
            {"Te encuentras con una armadura que aumenta tu defensa en 2 ","Sigues con tu camino"},
            {"Los ladrones se quedan tranquilos con el oro que les diste y te dejan seguir tu camino","Al enfrentarte a ellos pierdes 4 de vida pero te encuentras con que tenian 20 de oro en sus bolsillos"},
            {"Te das cuenta que todo era una trampa! La abuelita es cinturon negro en karate y te pega con el baston quitandote 3 de vida y robandote 50 de oro :(","Te aprovechas de la abuelita y le robas la bolsa, obteniendo 50 de oro"},
            {"De tanta hambre que tenias pierdes 2 de vida","Te preocupas mas por ti mismo y te comes un buen pollo asado con papas fritas, lo que hace que recuperes 2 de vida"},
            {"Efectivamente se encontraba en la mano que elegiste! recibes un item que aumenta en 2 tu daño y tu defensa","Lamentablemente la moneda se encontraba en la mano izquierda, pierdes 100 de oro :("},  
        };
        
        if (opcion == 0){
            statsa = new Item(50, 0, 0, 0, 0);
            statsb = new Item(0, 0, 0, 0, 0);
        }
        else if (opcion == 1){
            statsa = new Item(-30,-3,0,0,0);
            statsb = new Item(0,0,0,0,0);
        }
        else if (opcion == 2){
            statsa = new Item(0, 0, 0, 0, 2);
            statsb = new Item(0, 0, 0, 0, 0);
        }
        else if (opcion == 3){
            statsa = new Item(100, 0, 0, 0, 0);
            statsb = new Item(-20, -4, 0, 0, 0);
        }
        else if (opcion == 4){
            statsa = new Item(50, -3, 0, 0, 0);
            statsb = new Item(-50, 0, 0, 0, 0);
        }
        else if (opcion == 5){
            statsa = new Item(0, -2, 0, 0, 0);
            statsb = new Item(0, 2, 0, 0, 0);
        }
        else {
            statsa = new Item(0, 0, 0, 2, 2);
            statsb = new Item(100, 0, 0, 0, 0);
        }
        
        this.descripcion = desc[opcion];
        this.alternativa1 = alt[opcion][0];
        this.alternativa2 = alt[opcion][1];
        this.resultado1String = recomp[opcion][0];
        this.resultado2String = recomp[opcion][1];
        this.resultado1 = statsa;
        this.resultado2 = statsb;
    }

    //GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    public String getDescripcion(){
        return this.descripcion;
    }
    
    public void setItems_aplicados(String descripcion){
        this.descripcion = descripcion;
    }

    public String getAlternativa1(){
        return this.alternativa1;
    }

    public void setAlternativa1(String alternativa1){
        this.alternativa1 = alternativa1;
    }

    public String getAlternativa2(){
        return this.alternativa2;
    }

    public void setAlternativa2(String alternativa2){
        this.alternativa2 = alternativa2;
    }

    public Item getResultado1(){
        return this.resultado1;
    }

    public void setResultado1(Item resultado1){
        this.resultado1 = resultado1;
    }

    public Item getResultado2(){
        return this.resultado2;
    }

    public void setResultado2(Item resultado2){
        this.resultado2 = resultado2;
    }

    public String getResultado1String(){
        return this.resultado1String;
    }

    public void setResultado1String(String resultado1){
        this.resultado1String = resultado1;
    }

    public String getResultado2String(){
        return this.resultado2String;
    }

    public void setResultado2String(String resultado2){
        this.resultado2String = resultado2;
    }

    //FIN GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    /**
    * Función la cual elige al azar una descripción de un evento con el que se encontrará el jugador, a lo que este tendrá
      dos opciones para elegir, cada una con su recompensa secreta
    * 
    * @param Jugador jugador: Corresponde al usuario/jugador que se encuentra jugando el juego e interactua con el nodo evento 
    * @param Scanner scannerInt: Scanner que se utilizará para obtener los inputs del usuario/jugador en los casos que sea necesario       
    * 
    */ 

    void interactuar(Jugador jugador, Scanner scannerInt){
        int opcion;
        
        System.out.println("\nEN TU CAMINO A DERROTAR EL JEFE FINAL TE ENCUENTRAS CON UNA SITUACION MUY PECULIAR");

        System.out.println(getDescripcion());

        System.out.println("TIENES DOS OPCIONES: ");

        System.out.println("1) "+getAlternativa1());
        System.out.println("2) "+getAlternativa2());

        System.out.println("¿QUE DESEAS HACER? (ingresa numero de alternativa)");
        opcion = scannerInt.nextInt();

        if (opcion == 1){
            resultado1.aplicar(jugador);
            System.out.println(getResultado1String());           
        }
        else if (opcion == 2){
            resultado2.aplicar(jugador);
            System.out.println(getResultado2String());
        }
    }
}
