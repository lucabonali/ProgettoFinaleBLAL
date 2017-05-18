package main.controller.board;

import main.api.exceptions.NewActionException;
import main.controller.effects.Effect;
import main.api.exceptions.LorenzoException;
import main.controller.fields.Field;
import main.api.types.CardType;
import main.servergame.AbstractPlayer;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi rappresenta una singola carta sviluppo
 */

public class Card {
    private AbstractPlayer player;
    private final CardType type;
    private final String name;
    private final List<Field> costs;
    private final List<Effect> quickEffects;
    private final List<Effect> permanentEffects;
    private final int period;

    public Card(CardType type, String name, List<Field> costs,
                List<Effect> qeffs, List<Effect> peffs, int period){
        this.type = type;
        this.name = name;
        this.costs = costs;
        this.quickEffects = qeffs;
        this.permanentEffects = peffs;
        this.period = period;
    }

    public CardType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<Field> getCosts() {
        return costs;
    }

    public List<Effect> getQuickEffects() {
        return quickEffects;
    }

    public List<Effect> getPermanentEffects() {
        return permanentEffects;
    }

    public AbstractPlayer getPlayer() {
        return player;
    }

    public void setPlayer(AbstractPlayer player) throws LorenzoException {
        for (Field cost : costs) {
            if (!player.getPersonalBoard().checkResources(cost))
                throw new LorenzoException("non hai abbastanza fondi per eseguire l'azione");
        }
        //se ho abbastanza risorse posso pescare e quindi pago il costo e attivo l'effetto immediato
        this.player = player;
        activeCosts();
    }

    public int getPeriod() {
        return period;
    }

    /**
     * chiama il metodo che modifica le risorse nella player
     * passandogli la lista dei costi che sarà tutto negativo
     */
    public void activeCosts(){
        if (costs != null) {
            for (Field res : costs)
                player.getPersonalBoard().modifyResources(res);
        }
    }

    /**
     * metodo che attiva tutti gli effetti immediati chiamando il metodo
     * active(PersonalBoard) di ciascun effetto.
     */
    public void activeQuickEffects() throws RemoteException, NewActionException {
        if (quickEffects != null) {
            for (Effect effect : quickEffects)
                effect.active(player);
        }
    }

    public void activePermanentEffects() throws RemoteException, NewActionException {
        if (quickEffects != null) {
            for (Effect effect : permanentEffects)
                effect.active(player);
        }
    }
}
