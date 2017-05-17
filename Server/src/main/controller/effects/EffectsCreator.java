package main.controller.effects;

/**
 * @author Luca
 * @author Andrea
 *
 * è una classe di factory che non fa altro che, in base
 * al codice (recuperato dal db), generarmi l'istanza dell'effetto
 * corretta.
 */
public class EffectsCreator {
    //caratteri che uso come codici per identificare le risorse
    public static final char CHAR_COIN ='c';
    public static final char CHAR_STONE ='s';
    public static final char CHAR_WOOD ='w';
    public static final char CHAR_SERVANT ='z';
    public static final char CHAR_MILITARY ='m';
    public static final char CHAR_FAITH ='f';
    public static final char CHAR_VICTORY ='v';
    public static final char CHAR_PRIVILEGE ='p';
    public static final char CHAR_TERRITORY = 't';
    public static final char CHAR_CHARACTERS = 'y';
    public static final char CHAR_BUILDINGS = 'b';
    public static final char CHAR_VENTURES = 'x';
    public static final char CHAR_PRODUCTION = 'e';
    public static final char CHAR_HARVEST = 'h';
    public static final char CHAR_TOWER_ACTION = 'a';

    /**
     * metodo statico che in base al codice mi crea l'effetto
     * corretto tra tutti quelli che implementano l'interfaccia
     * Effect
     * @param cod codice fatto da due caratteri, il primo intero e il secondo è il tipo di risorsa/azione
     * @return l'effetto
     */
    public static Effect createEffect(String cod) {
        char effectType = cod.charAt(1);
        switch (effectType){
            case CHAR_TOWER_ACTION:
            case CHAR_TERRITORY:
            case CHAR_BUILDINGS:
            case CHAR_CHARACTERS:
            case CHAR_VENTURES:
            case CHAR_PRODUCTION:
            case CHAR_HARVEST:
                return ActionEffect.createInstance(cod);
            default:
                return FixedIncrementEffect.createInstance(cod);
        }
    }
}
