package actionSpaces;

import board.FamilyMember;
import effects.Effect;

import java.util.List;

/**
 * @author Luca
 * @author Andrea
 *
 * Generalizza gli spazi azione multipli
 */
public abstract class LargeActionSpace implements ActionSpaceInterface {
    private int value;
    private List<FamilyMember> familyMembers;
    private List<Effect> effect;

    public LargeActionSpace(int value){
        this.value = value;
    }

    public List<Effect> getEffect() {
        return effect;
    }

    public void setEffect(List<Effect> effect) {
        this.effect = effect;
    }

    public void getFamilyMembers(){}

    public void addFamilyMember(FamilyMember familyMember){
        this.familyMembers.add(familyMember);
    }

    public void removeFamilyMembers(){
        //Da implementare, rimuove tutti i familiari presenti nello spazio azione (da utilizzare a fine turno)
    }

    public abstract void doAction(Action action);


}
