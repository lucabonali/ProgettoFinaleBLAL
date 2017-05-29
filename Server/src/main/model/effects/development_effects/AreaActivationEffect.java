package main.model.effects.development_effects;

import main.game_server.exceptions.NewActionException;
import main.game_server.AbstractPlayer;

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

    /**
     * Permette l' attivazione dell' azione
     * @param player il giocatore che sta attivando l'effetto
     * @throws RemoteException
     * @throws NewActionException
     */
    @Override
    public void active(AbstractPlayer player) throws RemoteException, NewActionException {
        if (player.getPersonalBoard().getCurrentAction().getValue() >= minValue)
            effect.active(player);
    }


    /**
     * metodo che crea un effetto in base al tipo di area produzione false e harvest true
     * @param c
     * @param substring
     * @return
     */
    public static Effect createInstance(char c, String substring) {
        int minValue = Integer.parseInt(c+"");
        return new AreaActivationEffect(FixedIncrementEffect.createInstance(substring), minValue);
    }

    /**
     * overloading del metodo precedente che viene chiamato quando ho un effetto permanente che incrementa
     * le risorse in base al numero di carte
     * @param cod
     * @return
     */
    public static Effect createInstance(String cod) {
        int minValue = Integer.parseInt(cod.charAt(0)+"");
        return new AreaActivationEffect(VariableIncrementEffect.createInstance(cod.substring(2)),minValue);
    }


    /**
     * ulteriore overloading del metodo precedente che crea l' effetto di conversione di due o più risorse,
     * quando richiamato da una zona produzione o raccolta
     * @param increment
     * @param decrement
     * @return
     */
    public static AreaActivationEffect createInstance(String increment, String decrement) {
        int minValue = Integer.parseInt(increment.charAt(0)+"");
        return new AreaActivationEffect(ConvertionEffect.createInstance(increment.substring(1),decrement), minValue);
    }

}
