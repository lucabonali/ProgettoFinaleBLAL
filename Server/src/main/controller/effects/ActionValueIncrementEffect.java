package main.controller.effects;

import main.controller.actionSpaces.ActionSpaceInterface;
import main.controller.actionSpaces.largeActionSpaces.LargeHarvestActionSpace;
import main.controller.actionSpaces.largeActionSpaces.LargeProductionActionSpace;
import main.controller.actionSpaces.singleActionSpaces.FloorActionSpace;
import main.controller.actionSpaces.singleActionSpaces.HarvestActionSpace;
import main.controller.actionSpaces.singleActionSpaces.ProductionActionSpace;
import main.servergame.AbstractPlayer;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica un effetto che mi aumenta il valore
 * di un'azione di un preciso tipo
 */
public class ActionValueIncrementEffect implements Effect{
    //incremento di valore dell'azione
    private int incrementValue;
    //spazio azione sulla quale viene eseguita l'azione da incrementare
    private ActionSpaceInterface actionSpace;

    public ActionValueIncrementEffect(ActionSpaceInterface actionSpace, int incrementValue) {
        this.actionSpace = actionSpace;
        this.incrementValue = incrementValue;
    }

    /**
     * metodo che fa a verificare la azione corrente che si stà per eseguire dalla
     * plancia ricevuta come parametro, e verifica se sono dello stesso tipo
     * attraverso 'instanceof' e se combacia allora incrementa/decrementa il suo valore.
     * @param player giocatore che esegue l'azione
     */
    @Override
    public void active(AbstractPlayer player) {
        if (FloorActionSpace.class.isInstance(actionSpace)) {
            FloorActionSpace floorActionSpace = (FloorActionSpace) actionSpace;
            if (actionSpace.getClass().isInstance(player.getPersonalBoard().getCurrentAction().getActionSpace())) {
                FloorActionSpace myFloorActionSpace = (FloorActionSpace) player.getPersonalBoard().getCurrentAction().getActionSpace();
                if (floorActionSpace.getCardType() == myFloorActionSpace.getCardType()){
                    player.getPersonalBoard().getCurrentAction().modifyValue(incrementValue);
                }
            }
        }
        else if (HarvestActionSpace.class.isInstance(actionSpace)){
            if(HarvestActionSpace.class.isInstance(player.getPersonalBoard().getCurrentAction().getActionSpace()) ||
                    LargeHarvestActionSpace.class.isInstance(player.getPersonalBoard().getCurrentAction().getActionSpace())){
                player.getPersonalBoard().getCurrentAction().modifyValue(incrementValue);
            }
        }
        else if (ProductionActionSpace.class.isInstance(actionSpace)){
            if(ProductionActionSpace.class.isInstance(player.getPersonalBoard().getCurrentAction().getActionSpace()) ||
                    LargeProductionActionSpace.class.isInstance(player.getPersonalBoard().getCurrentAction().getActionSpace())){
                player.getPersonalBoard().getCurrentAction().modifyValue(incrementValue);
            }
        }
    }
}
