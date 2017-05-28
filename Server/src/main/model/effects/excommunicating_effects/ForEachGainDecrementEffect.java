package main.model.effects.excommunicating_effects;

import main.game_server.exceptions.NewActionException;
import main.api.types.ResourceType;
import main.model.effects.development_effects.Effect;
import main.model.effects.development_effects.EffectsCreator;
import main.model.fields.Field;
import main.model.fields.Resource;
import main.game_server.AbstractPlayer;

import java.rmi.RemoteException;

/**
 * @author Luca
 * @author Andrea
 */
public class ForEachGainDecrementEffect implements Effect {
    private Field resource;

    public ForEachGainDecrementEffect(ResourceType resourceType){
        this.resource = new Resource(-1, resourceType);
    }

    @Override
    public void active(AbstractPlayer player) throws RemoteException, NewActionException {

    }

    public static Effect createExcomInstance(String codEffect) {
        switch (codEffect.charAt(0)){
            case EffectsCreator.CHAR_COIN:
                return new ForEachGainDecrementEffect(ResourceType.COINS);
            case EffectsCreator.CHAR_MILITARY:
                return new ForEachGainDecrementEffect(ResourceType.MILITARY);
            case EffectsCreator.CHAR_SERVANT:
                return new ForEachGainDecrementEffect(ResourceType.SERVANTS);
        }
        return null;
    }
}
