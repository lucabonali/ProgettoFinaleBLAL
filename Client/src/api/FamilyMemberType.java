package api;

import java.io.Serializable;

/**
 * @author lampa
 */
public enum FamilyMemberType implements Serializable{
    ORANGE_DICE('o'), BLACK_DICE('b'), WHITE_DICE('w'), NEUTRAL_DICE('n');

    private char code;

    FamilyMemberType(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }
}
