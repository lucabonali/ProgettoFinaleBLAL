package main.model.action_spaces.singleActionSpaces;

import main.api.exceptions.NewActionException;
import main.model.action_spaces.Action;
import main.model.action_spaces.ActionSpaceInterface;
import main.model.board.FamilyMember;
import main.model.effects.development_effects.Effect;
import main.api.exceptions.LorenzoException;

import java.rmi.RemoteException;


/**
 * @author Luca
 * @author Andrea
 *
 * Generalizza gli spazi azione singoli
 */
public abstract class ActionSpace implements ActionSpaceInterface {
    private FamilyMember familyMember;
    private int minValue;
    private Effect effect;

    public ActionSpace(int minValue){
        this.minValue = minValue;
        familyMember = null;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public FamilyMember getFamilyMember(){
        return familyMember;
    }

    public void setFamilyMember(FamilyMember familyMember) throws LorenzoException {
        if (this.familyMember != null)
            throw new LorenzoException("lo spazio azione è già occupato");
        this.familyMember = familyMember;
    }

    public void removeFamilyMember(){
        this.familyMember=null;
    }

     public int getMinValue() {
         return minValue;
     }

     public abstract void doAction(Action action) throws LorenzoException, RemoteException, NewActionException;
}
