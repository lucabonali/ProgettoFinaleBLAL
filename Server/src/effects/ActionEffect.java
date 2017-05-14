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

    public ActionEffect(ActionSpaceInterface actionSpace, int value) {
        this.actionSpace = actionSpace;
        this.value = value;
    }

    @Override
    public void active(PersonalBoard personalBoard) {
        personalBoard.doAction(new Action(actionSpace, value, null));
    }
}
