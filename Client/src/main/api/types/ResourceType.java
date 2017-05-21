package main.api.types;

import java.io.Serializable;

/**
 * @author Luca
 * @author Andrea
 *
 * enumerazione dei tipi possibili delle mie risorse, che sono
 * in ordine: legna, pietre, servitori, monete, punti fede, punti militari,
 *            punti vittoria.
 */

public enum ResourceType implements Serializable {
    WOOD("WOOD"),STONE("STONE"),SERVANTS("SERVANTS"),COINS("COINS"),
    FAITH("FAITH"),MILITARY("MILITARY"),VICTORY("VICTORY"), PRIVILEGE("PRIVILEGE");

    private String code;

    ResourceType(String s){
        this.code = s;
    }

    public String getCode(){
        return code;
    }

}
