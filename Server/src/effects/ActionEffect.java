package effects;

import actionSpaces.Action;
import actionSpaces.ActionSpaceInterface;
import board.PersonalBoard;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi rappresenta un effetto che mi consente
 * di eseguire una nuova azione senza dover spostare un
 * mio familiare.
 */
public class ActionEffect implements Effect{
    private ActionSpaceInterface actionSpace;
    private int value;
    private Action action;

    public ActionEffect(ActionSpaceInterface actionSpace, int value) {
        this.actionSpace = actionSpace;
        this.value = value;
    }


    /**
     * andrà richiamato il metodo doAction passandogli una azione che verrà settata nel costruttore
     * @param personalBoard la plancia sulla quale eseguire
     */
    @Override
    public void active(PersonalBoard personalBoard) {
        //actionSpace.doAction();
    }
}
