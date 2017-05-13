package actionSpaces;

import board.FamilyMember;

/**
 * Created by Luca on 12/05/2017.
 */
public class Action {

    private final FamilyMember familyMember;
    private final ActionSpace actionSpace;

    public Action(FamilyMember familyMember, ActionSpace actionSpace){
        this.familyMember = familyMember;
        this.actionSpace = actionSpace;
    }

    public FamilyMember getFamilyMember() {
        return familyMember;
    }

    public ActionSpace getActionSpace() {
        return actionSpace;
    }

    public void commitAction(){
        this.actionSpace.doAction(this);
    }


}
