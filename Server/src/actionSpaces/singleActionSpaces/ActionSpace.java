package actionSpaces.singleActionSpaces;

import actionSpaces.Action;
import actionSpaces.ActionSpaceInterface;
import board.FamilyMember;
import effects.Effect;
import api.LorenzoException;


/**
 * @author Luca
 * @author Andrea
 *
 * Generalizza gli spazi azione singoli
 */
public abstract class ActionSpace implements ActionSpaceInterface {
    public static final char COD_FLOOR = 'f';
    public static final char COD_HAR_PROD = 'z';
    public static final char COD_MARKET = 'm';

    private FamilyMember familyMember;
    private int actionValue;
    private Effect effect;

    public ActionSpace(int actionValue){
        this.actionValue = actionValue;
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
        if (familyMember != null)
            throw new LorenzoException("lo spazio azione è già occupato");
        this.familyMember = familyMember;
    }

    public void removeFamilyMember(){
        this.familyMember=null;
    }

     public int getActionValue() {
         return actionValue;
     }

     public abstract void doAction(Action action) throws LorenzoException;
}
