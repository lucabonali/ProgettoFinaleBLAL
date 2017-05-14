package actionSpaces;

import board.FamilyMember;
import effects.Effect;


 /**
 * @author Luca
 * @author Andrea
 *
 * Generalizza gli spazi azione singoli
 */
public abstract class ActionSpace implements ActionSpaceInterface {
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

    public void setFamilyMember(FamilyMember familyMember){
        this.familyMember = familyMember;
    }

    public void removeFamilyMember(){
        this.familyMember=null;
    }

    public abstract void doAction(Action action);



}
