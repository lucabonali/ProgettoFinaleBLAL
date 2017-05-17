package main.api.messages;

import java.io.Serializable;

/**
 * @author lampa
 */
public enum MessageGameType implements Serializable{
    ACTION("ACTION"), ACTION_RESULT("ACTION_RESULT"), INFORMATION("INFORMATION"), NEW_ACTION("NEW_ACTION");

    private String code;

    MessageGameType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
