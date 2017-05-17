package main.api.messages;

import main.api.types.ActionSpacesType;
import main.api.types.CardType;
import main.api.types.FamilyMemberType;
import main.api.types.MarketActionType;

import java.io.Serializable;
import java.util.List;

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
    private List<Integer> qtaList;

    public MessageGame(MessageGameType messageGameType) {
        this.messageGameType = messageGameType;
    }

    public void setQtaList(List<Integer> qtaList) {
        this.qtaList = qtaList;
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

    public List<Integer> getQtaList() {
        return qtaList;
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
