package main.api.messages;

import java.io.Serializable;

/**
 * @author Luca
 * @author Andrea
 */
public enum MessageGameType implements Serializable{
    ACTION, ACTION_RESULT, INFORMATION, NEW_ACTION;
}
