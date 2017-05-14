package fields;

import types.ResourceType;

/**
 * @author  Luca
 * @author  Andrea
 *
 * classe che mi identifica le risorse di ciascun giocatore, ciascun
 * risorsa è resa diversa dall'attributo ResourceType.
 */

public class Resource implements Field {
    //caratteri che uso come codici per identificare le risorse
    private static final char CHAR_COIN ='a';
    private static final char CHAR_STONE ='b';
    private static final char CHAR_WOOD ='c';
    private static final char CHAR_SERVANT ='d';
    private static final char CHAR_MILITARY ='e';
    private static final char CHAR_FAITH ='f';
    private static final char CHAR_VICTORY ='g';

    private int qta;
    private ResourceType type;

    public Resource(int qta) {
        this(qta, null);
    }

    public Resource(int qta, ResourceType type){
        this.qta=qta;
        this.type=type;
    }

    @Override
    public int getQta() {
        return qta;
    }

    @Override
    public ResourceType getType() {
        return type;
    }

    @Override
    public void modify(Field resourceEffect) {
        if (resourceEffect.getType().getCode().equals(this.type.getCode())) {
            //sono dello stesso tipo
            this.qta += resourceEffect.getQta();
        }
    }

    @Override
    public void subtract(Field cost) {
        if (cost.getType().getCode().equals(this.type.getCode())) {
            //sono dello stesso tipo
            this.qta = this.qta - cost.getQta();
        }
    }

    @Override
    public void setType(ResourceType type) {
        if(type==null)
            this.type = type;
        return;
    }

    /**
     * mi crea la risorsa in base al codice preso dal db e che il metodo
     * riceve come parametro
     * codice = int1*char*int2[*int1*char*int2]
     * dove int1 -> indica la quantità di risorsa
     *      char -> il carattere che mi identifica la risorsa secondo
     *      int2 -> numero del periodo
     * @param cod codice
     * @return la risorsa creata in base al codice
     */
    public static Resource createResource(String cod) {
        //il primo carattere mi indica il numero intero del costo, e lo rendo subito negativo
        int qta = Integer.parseInt(cod.substring(0,1));
        //il secondo carattere mi indica la risorsa
        char charCod = cod.charAt(1);
        //inizializzo la risorsa e il punto passandogli solo la qta che sarà negativa
        Resource res = new Resource(qta);
        //in base al codice dell'effetto mi setta il tipo della risorsa
        //ancora da implementare
        switch (charCod) {
            case CHAR_COIN:
                res.setType(ResourceType.COINS);
                break;
            case CHAR_STONE:
                break;
            case CHAR_WOOD:
                break;
            case CHAR_SERVANT:
                break;
            case CHAR_MILITARY:
                break;
            case CHAR_FAITH:
                break;
            case CHAR_VICTORY:
                break;
            default:
                break;
        }

        return res;
    }
}
