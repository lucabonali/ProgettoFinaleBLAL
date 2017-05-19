package main.model.actionSpaces.largeActionSpaces;

import main.api.exceptions.NewActionException;
import main.model.actionSpaces.Action;
import main.model.actionSpaces.ActionSpaceInterface;
import main.model.board.FamilyMember;
import main.model.effects.Effect;
import main.api.exceptions.LorenzoException;

import java.rmi.RemoteException;
import java.util.ArrayList;
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
    private List<Effect> effects;

    public LargeActionSpace(int value){
        this.value = value;
        this.familyMembers = new ArrayList<>();
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
        this.familyMembers = new ArrayList<>();
    }

    public int getValue() {
        return value;
    }

    public abstract void doAction(Action action) throws LorenzoException, RemoteException, NewActionException;
}
