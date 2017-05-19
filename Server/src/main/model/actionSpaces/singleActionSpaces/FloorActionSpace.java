package main.model.actionSpaces.singleActionSpaces;

import main.api.exceptions.LorenzoException;
import main.api.exceptions.NewActionException;
import main.api.types.CardType;
import main.api.types.ResourceType;
import main.model.actionSpaces.Action;
import main.model.board.Card;
import main.model.effects.FixedIncrementEffect;
import main.model.fields.Resource;

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


    /**
     * Metodo che raccoglie la carta dalla torre , e la assegna al giocatore che ha attivato lo spazio azione
     * attraverso il piazzamento legittimo del familiare
     * @param action
     * @throws LorenzoException
     * @throws RemoteException
     * @throws NewActionException
     */
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
