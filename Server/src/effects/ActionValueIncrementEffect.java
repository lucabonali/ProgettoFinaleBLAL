package effects;

import actionSpaces.ActionSpaceInterface;
import actionSpaces.largeActionSpaces.LargeHarvestActionSpace;
import actionSpaces.largeActionSpaces.LargeProductionActionSpace;
import actionSpaces.singleActionSpaces.FloorActionSpace;
import actionSpaces.singleActionSpaces.HarvestActionSpace;
import actionSpaces.singleActionSpaces.ProductionActionSpace;
import board.PersonalBoard;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica un effetto che mi aumenta il valore
 * di un'azione di un preciso tipo
 */
public class ActionValueIncrementEffect implements Effect{
    //incremento di valore dell'azione
    private int value;
    //spazio azione sulla quale viene eseguita l'azione da incrementare
    private ActionSpaceInterface actionSpace;

    public ActionValueIncrementEffect(ActionSpaceInterface actionSpace, int value) {
        this.actionSpace = actionSpace;
        this.value = value;
    }

    /**
     * metodo che fa a verificare la azione corrente che si stà per eseguire dalla
     * plancia ricevuta come parametro, e verifica se sono dello stesso tipo
     * attraverso 'instanceof' e se combacia allora incrementa/decrementa il suo valore.
     * @param personalBoard la plancia sulla quale eseguire
     */
    @Override
    public void active(PersonalBoard personalBoard) {
        if (FloorActionSpace.class.isInstance(actionSpace)) {
            FloorActionSpace floorActionSpace = (FloorActionSpace) actionSpace;
            if (actionSpace.getClass().isInstance(personalBoard.getCurrentAction().getActionSpace())) {
                FloorActionSpace myFloorActionSpace = (FloorActionSpace) personalBoard.getCurrentAction().getActionSpace();
                if (floorActionSpace.getCardType() == myFloorActionSpace.getCardType()){
                    personalBoard.getCurrentAction().modifyValue(value);
                }
            }
        }
        else if (HarvestActionSpace.class.isInstance(actionSpace)){
            if(HarvestActionSpace.class.isInstance(personalBoard.getCurrentAction().getActionSpace()) ||
                    LargeHarvestActionSpace.class.isInstance(personalBoard.getCurrentAction().getActionSpace())){
                personalBoard.getCurrentAction().modifyValue(value);
            }
        }
        else if (ProductionActionSpace.class.isInstance(actionSpace)){
            if(ProductionActionSpace.class.isInstance(personalBoard.getCurrentAction().getActionSpace()) ||
                    LargeProductionActionSpace.class.isInstance(personalBoard.getCurrentAction().getActionSpace())){
                personalBoard.getCurrentAction().modifyValue(value);
            }
        }
    }
}
