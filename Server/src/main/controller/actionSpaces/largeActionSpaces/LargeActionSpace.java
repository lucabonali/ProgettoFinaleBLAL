package main.controller.actionSpaces.largeActionSpaces;

import main.controller.actionSpaces.Action;
import main.controller.actionSpaces.ActionSpaceInterface;
import main.controller.board.FamilyMember;
import main.controller.effects.Effect;
import main.api.LorenzoException;

import java.util.List;

/**
 * @author Luca
 * @author Andrea
 *
 * Generalizza gli spazi azione multipli
 */
public abstract class LargeActionSpace implements ActionSpaceInterface {
    public static final char COD_L_HAR_PROD = 'l';
    public static final char COD_COUNCIL = 'c';

    private int value;
    private List<FamilyMember> familyMembers;
    private List<Effect> effects;

    public LargeActionSpace(int value){
        this.value = value;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffect(List<Effect> effect) {
        this.effects = effect;
    }

    public List<FamilyMember> getFamilyMembers(){
        return familyMembers;
    }

    public void addFamilyMember(FamilyMember familyMember){
        this.familyMembers.add(familyMember);
    }

    public void removeFamilyMembers(){
        //Da implementare, rimuove tutti i familiari presenti nello spazio azione (da utilizzare a fine turno)
    }

    public int getValue() {
        return value;
    }

    public abstract void doAction(Action action) throws LorenzoException;
}
