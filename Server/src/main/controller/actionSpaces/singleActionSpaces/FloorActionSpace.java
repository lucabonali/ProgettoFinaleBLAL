package main.controller.actionSpaces.singleActionSpaces;

import main.api.exceptions.NewActionException;
import main.controller.actionSpaces.Action;
import main.controller.board.Card;
import main.api.exceptions.LorenzoException;
import main.api.types.CardType;

import java.rmi.RemoteException;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi rappresenta un singolo piano della torre, essa
 * è una dei 5 tipi diversi di spazi azione
 */
public class FloorActionSpace extends ActionSpace {
    private Card card;
    private CardType cardType;

    public FloorActionSpace(int value, CardType cardType) {
        super(value);
        this.cardType = cardType;
    }

    public void setCard(Card card){
        this.card = card;
    }

    public Card getCard(){
        return card;
    }

    public void removeCard(){
        this.card = null;
    }

    public CardType getCardType() {
        return cardType;
    }

    @Override
    public void doAction(Action action) throws LorenzoException, RemoteException, NewActionException {
        if (getActionValue() > action.getValue())
            throw new LorenzoException("non hai abbastanza forza per eseguire l'azione!!");

        card.setPlayer(action.getPlayer());
        setFamilyMember(action.getFamilyMember());
        getEffect().active(action.getPlayer());
        card.activeQuickEffects();
    }





}
