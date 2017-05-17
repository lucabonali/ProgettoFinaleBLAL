package main.controller.actionSpaces;

import main.api.exceptions.NewActionException;
import main.controller.board.FamilyMember;
import main.api.exceptions.LorenzoException;
import main.game.AbstractPlayer;

import java.rmi.RemoteException;

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
    private AbstractPlayer player;

    public Action(ActionSpaceInterface actionSpace, int value, FamilyMember familyMember, AbstractPlayer player){
        this.actionSpace = actionSpace;
        this.value = value;
        this.familyMember = familyMember;
        this.player = player;
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

    public AbstractPlayer getPlayer() {
        return player;
    }

    /**
     * metodo che mi esegue l'azione, chiamando il metodo sullo spazio
     * azione che ha come attributo.
     */
    public void commitAction() throws LorenzoException, RemoteException, NewActionException {
        familyMember.getPersonalBoard().activeCharacterEffects(this);
        //dopo che ho attivato gli effetti delle carte personaggio
        this.actionSpace.doAction(this);
    }
}
