package main.api.messages;

import main.api.types.*;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Luca
 * @author Andrea
 */
public class MessageAction implements Message, Serializable{
    private FamilyMemberType familyMemberType;
    private ActionSpacesType actionSpacesType;
    private CardType cardType;
    private int numFloor;
    private MarketActionType marketActionType;
    // WOOD , STONE , SERVANTS , COINS , VICTORY , FAITH , MILITARY
    private Map<ResourceType,Integer> qtaMap;
    private int value;

    public MessageAction(ActionSpacesType actionSpacesType, CardType cardType, int numFloor, MarketActionType marketActionType, FamilyMemberType familyMemberType) {
        this.actionSpacesType = actionSpacesType;
        this.cardType = cardType;
        this.numFloor = numFloor;
        this.marketActionType = marketActionType;
        this.familyMemberType = familyMemberType;
        setValue(0);
    }

    /**
     * se voglio fare un'azione negli spazi raccolta/produzione/consiglio
     * @param actionSpacesType codice spazio azione
     * @param familyMemberType codice familiare
     */
    public MessageAction(ActionSpacesType actionSpacesType, FamilyMemberType familyMemberType) {
        this(actionSpacesType, null, 0, null, familyMemberType);
    }

    /**
     * se voglio fare un'azione sulle torri
     * @param actionSpacesType codice spazio azione
     * @param cardType codice torre(tipo carta)
     * @param numFloor numero piano
     * @param familyMemberType codice familiare
     */
    public MessageAction(ActionSpacesType actionSpacesType, CardType cardType, int numFloor, FamilyMemberType familyMemberType) {
        this(actionSpacesType, cardType, numFloor, null, familyMemberType);
    }

    /**
     * se voglio fare un'azione negli spazi raccolta/produzione/consiglio
     * @param actionSpacesType codice spazio azione
     * @param marketActionType codice mercato
     * @param familyMemberType codice familiare
     */
    public MessageAction(ActionSpacesType actionSpacesType, MarketActionType marketActionType, FamilyMemberType familyMemberType) {
        this(actionSpacesType, null, 0, marketActionType, familyMemberType);
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

    public FamilyMemberType getFamilyMemberType() {
        return familyMemberType;
    }

    public Map<ResourceType,Integer> getQtaMap() {
        return qtaMap;
    }


}
