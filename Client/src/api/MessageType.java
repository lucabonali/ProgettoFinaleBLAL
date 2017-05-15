package api;

import java.io.Serializable;

/**
 * @author lampa
 */
public enum MessageType implements Serializable{
    ACTION("ACTION"), ACTION_RESULT("ACTION_RESULT"), INFORMATION("INFORMATION");

    private String code;

    MessageType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
