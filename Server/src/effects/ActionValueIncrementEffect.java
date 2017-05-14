package effects;

import actionSpaces.ActionSpaceInterface;
import board.PersonalBoard;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica un effetto che mi aumenta il valore
 * di un'azione di un preciso tipo
 */
public class ActionValueIncrementEffect implements Effect{
    //incremento di valore dell'azione
    private int value;
    //spazio azione sulla quale viene eseguita l'azione da incrementare
    private ActionSpaceInterface actionSpace;

    public ActionValueIncrementEffect(ActionSpaceInterface actionSpace, int value) {
        this.actionSpace = actionSpace;
        this.value = value;
    }

    /**
     * metodo che fa a verificare la azione corrente che si stà per eseguire dalla
     * plancia ricevuta come parametro, e verifica se sono dello stesso tipo
     * attraverso 'instanceof' e se combacia allora incrementa/decrementa il suo valore.
     * @param personalBoard la plancia sulla quale eseguire
     */
    @Override
    public void active(PersonalBoard personalBoard) {
        //da implementare

    }
}
