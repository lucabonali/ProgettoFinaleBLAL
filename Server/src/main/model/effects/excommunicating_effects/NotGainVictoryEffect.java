package main.model.effects.excommunicating_effects;

import main.api.exceptions.NewActionException;
import main.api.types.CardType;
import main.model.effects.development_effects.Effect;
import main.model.effects.development_effects.EffectsCreator;
import main.servergame.AbstractPlayer;

import java.rmi.RemoteException;

/**
 * @author lampa
 */
public class NotGainVictoryEffect implements Effect {
    private CardType cardType;

    private NotGainVictoryEffect(CardType cardType){
        this.cardType = cardType;
    }

    @Override
    public void active(AbstractPlayer player) throws RemoteException, NewActionException {

    }

    public static Effect createExcomInstance(String codEffect) {
        switch (codEffect.charAt(0)){
            case EffectsCreator.CHAR_TERRITORY:
                return new NotGainVictoryEffect(CardType.TERRITORY);
            case EffectsCreator.CHAR_CHARACTERS:
                return new NotGainVictoryEffect(CardType.CHARACTER);
            case EffectsCreator.CHAR_VENTURES:
                return new NotGainVictoryEffect(CardType.VENTURES);
        }
        return null;
    }
}
