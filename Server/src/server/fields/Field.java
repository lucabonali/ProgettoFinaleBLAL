package server.fields;

/**
 * Created by Luca on 10/05/2017.
 */
public interface Field {

    /**
     * ritorna la quantità della risorsa in oggetto
     * @return
     */
    int getQta();

    /**
     * modifica il valore dell' attributo desiderato in base a n
     */
    void modify(Field resourceEffect);

    /**
     * metodo che mi sottrae il costo alla risorsa sulla quale
     * viene eseguito
     * @param cost la risorsa che mi rappresenta il costo
     */
    void subtract(Field cost);

    /**
     * mi ritorna il tipo della mia risorsa
     * @return ResourceType
     */
    ResourceType getType();

    /**
     * metodo che mi setta il tipo della mia risorsa
     * @param type tipo
     */
    void setType(ResourceType type);

}
