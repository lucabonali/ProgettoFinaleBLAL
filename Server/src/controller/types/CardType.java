package controller.types;

/**
 * @author Luca
 * @author Andrea
 *
 * enumerazione dei possibili tipi di carte
 * in ordine: territorio, personaggi, imprese, imprese.
 */

public enum CardType {
    TERRITORY("TERRITORY"),CHARACTER("CHARACTER"),BUILDING("BUILDING"),VENTURES("VENTURES");

    private String code;

    CardType(String s){
        code = s ;
    }

    public String getCode(){
        return code;
    }

}
