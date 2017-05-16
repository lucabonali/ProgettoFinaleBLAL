package api;

/**
 * @author lampa
 */
public enum MessageLoginType {
    LOGIN("LOGIN"), START_GAME("START_GAME");

    private String code;

    MessageLoginType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
