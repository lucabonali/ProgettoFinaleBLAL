package main.api.messages;

import main.api.types.ActionSpacesType;
import main.api.types.CardType;
import main.api.types.MarketActionType;

import java.io.Serializable;

/**
 * @author Andrea
 * @author Luca
 *
 * rappresenta una nuova azione ottenuta in seguito ad un effetto di qualche carta, è caratterizzata
 * dal fatto che è senza familiare
 */
public class MessageNewAction implements Message, Serializable{
    private ActionSpacesType actionSpacesType;
    private CardType cardType;
    private int numFloor;
    private MarketActionType marketActionType;
    private int value;
    private int additionalValue;

    public MessageNewAction(ActionSpacesType actionSpacesType, CardType cardType, int numFloor, MarketActionType marketActionType, int value) {
        this.actionSpacesType = actionSpacesType;
        this.cardType = cardType;
        this.numFloor = numFloor;
        this.marketActionType = marketActionType;
        setValue(value);
    }

    /**
     * se voglio fare un'azione negli spazi raccolta/produzione/consiglio
     * @param actionSpacesType codice spazio azione
     * @param value valore dell'azione
     */
    public MessageNewAction(ActionSpacesType actionSpacesType, int value) {
        this(actionSpacesType, null, 0, null, value);
    }

    /**
     * se voglio fare un'azione sulle torri
     * @param actionSpacesType codice spazio azione
     * @param cardType codice torre(tipo carta)
     * @param numFloor numero piano
     * @param value valore dell'azione
     */
    public MessageNewAction(ActionSpacesType actionSpacesType, CardType cardType, int numFloor, int value) {
        this(actionSpacesType, cardType, numFloor, null, value);
    }

    /**
     * se voglio fare un'azione negli spazi raccolta/produzione/consiglio
     * @param actionSpacesType codice spazio azione
     * @param marketActionType codice mercato
     * @param value valore dell'azione
     */
    public MessageNewAction(ActionSpacesType actionSpacesType, MarketActionType marketActionType, int value) {
        this(actionSpacesType, null, 0, marketActionType, value);
    }

    @Override
    public ActionSpacesType getActionSpacesType() {
        return actionSpacesType;
    }

    @Override
    public CardType getCardType() {
        return cardType;
    }

    @Override
    public int getNumFloor() {
        return numFloor;
    }

    @Override
    public MarketActionType getMarketActionType() {
        return marketActionType;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    public int getAdditionalValue() {
        return additionalValue;
    }

    public void setAdditionalValue(int additionalValue) {
        this.additionalValue = additionalValue;
    }
}
