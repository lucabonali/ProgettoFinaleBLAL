package main.api.messages;

import java.io.Serializable;

/**
 * @author lampa
 */
public class MessageLogin implements Serializable{
    private MessageLoginType type;
    private String username;
    private String password;
    private int gameMode;

    public MessageLogin(MessageLoginType type) {
        this.type = type;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public MessageLoginType getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
