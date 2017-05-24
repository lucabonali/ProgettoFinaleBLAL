package main.model.action_spaces.largeActionSpaces;

import main.servergame.exceptions.NewActionException;
import main.model.action_spaces.Action;
import main.model.action_spaces.ActionSpaceInterface;
import main.model.board.FamilyMember;
import main.model.effects.development_effects.Effect;
import main.servergame.exceptions.LorenzoException;

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
