package main.controller.actionSpaces.singleActionSpaces;

import main.api.exceptions.LorenzoException;
import main.api.exceptions.NewActionException;
import main.api.types.CardType;
import main.api.types.ResourceType;
import main.controller.actionSpaces.Action;
import main.controller.board.Card;
import main.controller.effects.FixedIncrementEffect;
import main.controller.fields.Resource;

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

    public FloorActionSpace(int value, CardType cardType, ResourceType resourceTypeQuickEffect) {
        super(value);
        this.cardType = cardType;
        Resource resource = null;
        if (value == 2)
            resource = new Resource(1, resourceTypeQuickEffect);
        else if (value == 3)
            resource = new Resource(2, resourceTypeQuickEffect);
        super.setEffect(new FixedIncrementEffect(resource)); //eventualmente null
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
        if (getMinValue() > action.getValue())
            throw new LorenzoException("non hai abbastanza forza per eseguire l'azione!!");

        card.setPlayer(action.getPlayer());
        setFamilyMember(action.getFamilyMember());
        if (getEffect() != null)
            getEffect().active(action.getPlayer());
        card.activeQuickEffects();
    }





}
