public class Item {
    private int precio;
    private int recuperar_hp;
    private int aumentar_hp_total;
    private int aumentar_danio;
    private int aumentar_defensa;

    /**
    * Constructor que inicializa el item con sus atributos dados
    * 
    * @param int precio: precio del item   
    * @param int recuperar_hp: hp actual que recupera el item
    * @param int aumentar_hp_total: vida total que le aumenta al personaje
    * @param int aumentar_danio: el daño que aumenta del personaje
    * @param int aumentar_defensa: la defensa que aumenta del personaje
    *      
    */ 
    
    public Item (int precio, int recuperar_hp, int aumentar_hp_total, int aumentar_danio, int aumentar_defensa) {
        this.precio = precio;
        this.recuperar_hp = recuperar_hp;
        this.aumentar_hp_total = aumentar_hp_total;
        this.aumentar_danio = aumentar_danio;
        this.aumentar_defensa = aumentar_defensa;
    }

    //GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    public int getPrecio(){
        return this.precio;
    }

    public void setPrecio(int precio){
        this.precio = precio;
    }

    public int getRecuperar_hp(){
        return this.recuperar_hp;
    }

    public void setRecuperar_hp(int recuperar_hp){
        this.recuperar_hp = recuperar_hp; 
    }

    public int getAumentar_hp_total(){
        return this.aumentar_hp_total;
    }

    public void setAumentar_hp_total(int aumentar_hp_total){
        this.aumentar_hp_total = aumentar_hp_total;
    }

    public int getAumentar_danio(){
        return this.aumentar_danio;
    }

    public void setAumentar_danio(int aumentar_danio){
        this.aumentar_danio = aumentar_danio;
    }

    public int getAumentar_defensa(){
        return this.aumentar_defensa;
    }

    public void setAumentar_defensa(int aumentar_defensa){
        this.aumentar_defensa = aumentar_defensa;
    }

    //FIN GETTERS Y SETTERS DE LOS ATRIBUTOS DE LA CLASE

    /**
    * Funcion que agrega el item a la lista de items aplicados del jugador y aplica sus estadisticas,
      tambien descontando el precio del item al oro del jugador
    * 
    * @param Jugador jugador: usuario/jugador al cual se le aplica el item     
    * 
    */ 

    void aplicar(Jugador jugador){

        Item ap = new Item(getPrecio(),getRecuperar_hp(),getAumentar_hp_total(),getAumentar_danio(),getAumentar_defensa());
        jugador.getItems_aplicados().add(ap);
        jugador.setHp_total(jugador.getHp_total()+ getAumentar_hp_total());
        if (jugador.getHp_actual() + getRecuperar_hp() > jugador.getHp_total()){
            jugador.setHp_actual(jugador.getHp_total());
        }
        else{
            jugador.setHp_actual(jugador.getHp_actual()+ getRecuperar_hp());
        }
        jugador.setDanio(jugador.getDanio()+ getAumentar_danio());
        jugador.setDefensa(jugador.getDefensa()+ getAumentar_defensa());
        if ((jugador.getDinero() - getPrecio()) < 0){
            jugador.setDinero(0);
        }
        else{
            jugador.setDinero(jugador.getDinero() - getPrecio()); 
        }
    }
}
