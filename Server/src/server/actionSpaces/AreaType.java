package server.actionSpaces;

/**
 * Created by Luca on 12/05/2017.
 */
public enum AreaType {
    PRODUCTION("PRODUCTION"),HARVEST("HARVEST");

    private String code;

    private AreaType(String s){
        this.code = s;
    }

    public String getType(){
        return code;
    }

}
