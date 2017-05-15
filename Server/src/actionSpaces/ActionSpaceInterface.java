package actionSpaces;

import api.LorenzoException;

/**
 * @author Luca
 * @author Andrea
 *
 * interfaccia comune a tutti gli spazi azione
 * sia singoli che multipli
 */
public interface ActionSpaceInterface {

    /**
     * ciascun spazio azione dovrà implementare questo metodo
     * che non fa altro che eseguirmi su lui stesso l'azione passata
     * come parametro.
     * @param action azione da eseguire
     */
    void doAction(Action action) throws LorenzoException;
}
