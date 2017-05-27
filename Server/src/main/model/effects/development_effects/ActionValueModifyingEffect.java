package main.model.effects.development_effects;

import main.api.types.CardType;
import main.model.action_spaces.ActionSpaceInterface;
import main.model.action_spaces.large_action_spaces.LargeHarvestActionSpace;
import main.model.action_spaces.large_action_spaces.LargeProductionActionSpace;
import main.model.action_spaces.single_action_spaces.FloorActionSpace;
import main.model.action_spaces.single_action_spaces.HarvestActionSpace;
import main.model.action_spaces.single_action_spaces.ProductionActionSpace;
import main.servergame.AbstractPlayer;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica un effetto che mi aumenta il valore
 * di un'azione di un preciso tipo
 */
public class ActionValueModifyingEffect implements Effect {
    //incremento/decremento di valore dell'azione
    private int changeValue;
    //spazio azione sulla quale viene eseguita l'azione da incrementare
    private ActionSpaceInterface actionSpace;

    public ActionValueModifyingEffect(ActionSpaceInterface actionSpace, int changeValue) {
        this.actionSpace = actionSpace;
        this.changeValue = changeValue;
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
                    player.getPersonalBoard().getCurrentAction().modifyValue(changeValue);
                }
            }
        }
        else if (HarvestActionSpace.class.isInstance(actionSpace)){
            if(HarvestActionSpace.class.isInstance(player.getPersonalBoard().getCurrentAction().getActionSpace()) ||
                    LargeHarvestActionSpace.class.isInstance(player.getPersonalBoard().getCurrentAction().getActionSpace())){
                player.getPersonalBoard().getCurrentAction().modifyValue(changeValue);
            }
        }
        else if (ProductionActionSpace.class.isInstance(actionSpace)){
            if(ProductionActionSpace.class.isInstance(player.getPersonalBoard().getCurrentAction().getActionSpace()) ||
                    LargeProductionActionSpace.class.isInstance(player.getPersonalBoard().getCurrentAction().getActionSpace())){
                player.getPersonalBoard().getCurrentAction().modifyValue(changeValue);
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
        return new ActionValueModifyingEffect(actionSpace,value);
    }

    public static Effect createExcomInstance(String cod){
        ActionSpaceInterface actionSpace = null;
        switch (cod.charAt(0)){
            case EffectsCreator.CHAR_HARVEST:
                return new ActionValueModifyingEffect(new HarvestActionSpace(1), -3);
            case EffectsCreator.CHAR_PRODUCTION:
                return new ActionValueModifyingEffect(new ProductionActionSpace(1), -3);
            case EffectsCreator.CHAR_BUILDINGS:
                actionSpace = new FloorActionSpace(1,CardType.BUILDING, null);
                break;
            case EffectsCreator.CHAR_CHARACTERS:
                actionSpace = new FloorActionSpace(1,CardType.CHARACTER, null);
                break;
            case EffectsCreator.CHAR_TERRITORY:
                actionSpace = new FloorActionSpace(1,CardType.TERRITORY, null);
                break;
            case EffectsCreator.CHAR_VENTURES:
                actionSpace = new FloorActionSpace(1,CardType.VENTURES, null);
                break;
        }
        return new ActionValueModifyingEffect(actionSpace, -4);
    }
}
