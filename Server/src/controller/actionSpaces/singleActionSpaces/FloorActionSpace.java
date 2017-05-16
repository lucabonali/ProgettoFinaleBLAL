package controller.actionSpaces.singleActionSpaces;

import controller.actionSpaces.Action;
import controller.board.Card;
import api.LorenzoException;
import controller.types.CardType;

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
    public void doAction(Action action) throws LorenzoException {
        if (getActionValue() > action.getValue())
            throw new LorenzoException("non hai abbastanza forza per eseguire l'azione!!");

        card.setPersonalBoard(action.getFamilyMember().getPersonalBoard());
        setFamilyMember(action.getFamilyMember());
        getEffect().active(action.getFamilyMember().getPersonalBoard());
    }





}
