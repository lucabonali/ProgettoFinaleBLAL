package actionSpaces;


/**
 * @author Luca
 * @author Andrea
 *
 * Contiene i 4 tipi di spazi azione Mercato
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
