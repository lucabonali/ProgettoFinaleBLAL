package api;

import java.io.Serializable;
import java.util.List;

/**
 * @author Luca
 * @author Andrea
 */
public class Message implements Serializable{
    private MessageType messageType;
    private String cardName;
    private FamilyMemberType familyMemberType;
    private String actionSpaceCode;
    private String content;
    // WOOD , STONE , SERVANTS , COINS , VICTORY , FAITH , MILITARY
    private List<Integer> qtaList;

    public Message(MessageType messageType) {
        this.messageType = messageType;
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

    public void setActionSpaceCode(String actionSpaceCode) {
        this.actionSpaceCode = actionSpaceCode;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getCardName() {
        return cardName;
    }

    public FamilyMemberType getFamilyMemberType() {
        return familyMemberType;
    }

    public String getActionSpaceCode() {
        return actionSpaceCode;
    }

    public String getContent() {
        return content;
    }

    public List<Integer> getQtaList() {
        return qtaList;
    }

}
