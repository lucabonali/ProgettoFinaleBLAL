package main.api.messages;

import main.api.types.*;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Luca
 * @author Andrea
 */
public class MessageGame implements Serializable{
    private MessageGameType messageGameType;
    private String cardName;
    private FamilyMemberType familyMemberType;
    private ActionSpacesType actionSpacesType;
    private CardType cardType;
    private int numFloor;
    private MarketActionType marketActionType;
    private String content;
    // WOOD , STONE , SERVANTS , COINS , VICTORY , FAITH , MILITARY
    private Map<ResourceType,Integer> qtaMap;

    public MessageGame(MessageGameType messageGameType) {
        this.messageGameType = messageGameType;
    }

    public void setQtaMap(Map<ResourceType,Integer> qtaMap) {
        this.qtaMap = qtaMap;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setFamilyMemberType(FamilyMemberType familyMemberType) {
        this.familyMemberType = familyMemberType;
    }

    public void setActionSpacesType(ActionSpacesType actionSpacesType) {
        this.actionSpacesType = actionSpacesType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageGameType getMessageGameType() {
        return messageGameType;
    }

    public String getCardName() {
        return cardName;
    }

    public FamilyMemberType getFamilyMemberType() {
        return familyMemberType;
    }

    public ActionSpacesType getActionSpacesType() {
        return actionSpacesType;
    }

    public String getContent() {
        return content;
    }

    public Map<ResourceType,Integer> getQtaMap() {
        return qtaMap;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public int getNumFloor() {
        return numFloor;
    }

    public void setNumFloor(int numFloor) {
        this.numFloor = numFloor;
    }

    public MarketActionType getMarketActionType() {
        return marketActionType;
    }

    public void setMarketActionType(MarketActionType marketActionType) {
        this.marketActionType = marketActionType;
    }
}
