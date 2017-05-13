package server.fields;

/**
 * @author Luca
 * @author Andrea
 *
 * enumerazione dei tipi possibili delle mie risorse, che sono
 * in ordine: legna, pietre, servitori, monete, punti fede, punti militari,
 *            punti vittoria.
 */

public enum ResourceType {
    WOOD("WOOD"),STONE("STONE"),SERVANTS("SERVANTS"),COINS("COINS"),
    FAITH("FAITH"),MILITARY("MILITARY"),VICTORY("VICTORY");

    private String code;

    ResourceType(String s){
        this.code = s;
    }

    public String getCode(){
        return code;
    }

}
