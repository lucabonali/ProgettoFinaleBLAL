package main.model.effects;

import main.api.exceptions.NewActionException;
import main.servergame.AbstractPlayer;

import java.rmi.RemoteException;

/**
 * @author Luca
 * @author Andrea
 *
 * mi identifa gli effetti permanenti degli edifici e dei territori
 * che vengono attivati solo quando si va in zona raccolto/produzione
 * e solo se l'azione ha un valore superiore al valore minimo
 * di questo effetto.
 */
public class AreaActivationEffect implements Effect{
    private Effect effect;
    private int minValue;

    public AreaActivationEffect(Effect effect, int minValue){
        this.effect = effect;
        this.minValue = minValue;
    }

    @Override
    public void active(AbstractPlayer player) throws RemoteException, NewActionException {
        if (player.getPersonalBoard().getCurrentAction().getValue() >= minValue)
            effect.active(player);
    }
}
