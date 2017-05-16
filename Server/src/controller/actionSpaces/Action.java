package controller.actionSpaces;

import controller.board.FamilyMember;
import api.LorenzoException;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi rappresenta l'azione che possono effettuare
 * i vari giocatori.
 */
public class Action {
    private final FamilyMember familyMember;
    private final ActionSpaceInterface actionSpace;
    private int value;

    public Action(ActionSpaceInterface actionSpace, int value, FamilyMember familyMember){
        this.actionSpace = actionSpace;
        this.value = value;
        this.familyMember = familyMember;
    }

    public FamilyMember getFamilyMember() {
        return familyMember;
    }

    public ActionSpaceInterface getActionSpace() {
        return actionSpace;
    }

    public void modifyValue(int modify) {
        this.value += modify;
    }

    public int getValue() {
        return value;
    }

    /**
     * metodo che mi esegue l'azione, chiamando il metodo sullo spazio
     * azione che ha come attributo.
     */
    public void commitAction() throws LorenzoException {
        familyMember.getPersonalBoard().activeCharacterEffects(this);
        //dopo che ho attivato gli effetti delle carte personaggio
        this.actionSpace.doAction(this);
    }


}
