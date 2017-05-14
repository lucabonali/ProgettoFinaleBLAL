package types;

/**
 * @author Luca
 * @author Andrea
 *
 * enumerazione dei due tipi di zone
 */
public enum AreaType {
    PRODUCTION("PRODUCTION"),HARVEST("HARVEST");

    private String code;

    private AreaType(String s){
        this.code = s;
    }

    public String getCode(){
        return code;
    }

}
