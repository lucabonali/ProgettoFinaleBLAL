package main.model.effects;

import main.api.types.CardType;
import main.model.actionSpaces.ActionSpaceInterface;
import main.model.actionSpaces.largeActionSpaces.LargeHarvestActionSpace;
import main.model.actionSpaces.largeActionSpaces.LargeProductionActionSpace;
import main.model.actionSpaces.singleActionSpaces.FloorActionSpace;
import main.model.actionSpaces.singleActionSpaces.HarvestActionSpace;
import main.model.actionSpaces.singleActionSpaces.ProductionActionSpace;
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

    /**
     * Metodo creatore di un istanza di questa classe, che restituisce l' action space corrispondente e il valore
     * necessario per attivarlo
     * @param cod
     * @return
     */
    public static Effect createInstance(String cod) {
        int value = Integer.parseInt(cod.substring(1,2));
        ActionSpaceInterface actionSpace = null;
        switch(cod.charAt(2)){
            case EffectsCreator.CHAR_BUILDINGS:
                actionSpace = new FloorActionSpace(1, CardType.BUILDING,null);
                break;
            case EffectsCreator.CHAR_CHARACTERS:
                actionSpace = new FloorActionSpace(1, CardType.CHARACTER,null);
                break;
            case EffectsCreator.CHAR_TERRITORY:
                actionSpace = new FloorActionSpace(1, CardType.TERRITORY,null);
                break;
            case EffectsCreator.CHAR_VENTURES:
                actionSpace = new FloorActionSpace(1, CardType.VENTURES,null);
                break;
            case EffectsCreator.CHAR_HARVEST:
                actionSpace = new HarvestActionSpace(1);
                break;
            case EffectsCreator.CHAR_PRODUCTION:
                actionSpace = new ProductionActionSpace(1);
                break;
        }
        return new ActionValueIncrementEffect(actionSpace,value);

    }
}
