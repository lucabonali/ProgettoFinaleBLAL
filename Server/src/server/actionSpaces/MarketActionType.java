package server.actionSpaces;

/**
 * Contiene i 4 tipi di spazi azione Mercato
 * Created by Luca, Andrea on 11/05/2017.
 */
public enum MarketActionType {
    YELLOW("YELLOW"),PURPLE("PURPLE"),BLUE("BLUE"),GRAY("GRAY");

    private String code;

    private MarketActionType(String s){
        this.code = s;
    }

    public String getType(){
        return code;
    }


}
