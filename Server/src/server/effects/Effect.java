package server.effects;

import server.actionSpaces.Action;
import server.board.PersonalBoard;
import server.fields.Field;
import server.fields.Resource;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi rappresenta un effetto immediato, in genere
 * viene associato alle carte sviluppo ma può essere creato in qualsiasi
 * momento per effetto di altre azioni, se associato alle carte sviluppo
 * viene attivato non'appena essa viene pescata.
 */

public class Effect {
    private Field field;
    private Action action;

    public Effect(Field field){
        this.field = field;
    }

    public Effect(Action action) {
        this.action = action;
    }

    /**
     * attiva effetto chiamando il metodo dalla plancia passandogli
     * il vettore delle risorse da incrementare/decrementare
     * @param personalBoard la plancia sulla quale eseguire
     */
    public void active(PersonalBoard personalBoard){
        if(field != null)
            personalBoard.modifyResources(this.field);
        else
            personalBoard.doAction(this.action);
    }

    /**
     * metodo statico che mi crea l'effetto in base al codice chiama
     * il metodo statico della classe Resource passandogli come parametro il codice
     * @param cod codice
     * @return l'effetto
     */
    public static Effect createEffect(String cod) {
        return new Effect(Resource.createResource(cod));
    }
}
