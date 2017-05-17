package main.controller.board;

import main.api.types.FamilyMemberType;

/**
 * @author Luca
 * @author Andrea
 *
 * rappresenta il familiare
 */
public class FamilyMember {
    private PersonalBoard personalBoard;
    private int value;
    private boolean positioned;
    private final FamilyMemberType type;


    /**
     * rappresenta il singolo familiare del giocatore, viene identificato dagli altr
     * dal tipo.
     * @param personalBoard plancia di riferimento del familiare
     */
    public FamilyMember(PersonalBoard personalBoard , FamilyMemberType type){
        this.personalBoard = personalBoard;
        this.type = type;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public int getValue() {
        return value;
    }

    /**
     * Richiamato a inizio di ogni turno quando vengono tirati i dadi
     * @param value valore del familiare (dipende dal tiro del dado)
     */
    public void setValue(int value) {
        this.value = value;
    }

    public void modifyValue(int n){
        this.value += n;
    }

    public boolean isPositioned() {
        return positioned;
    }

    public void setPositioned(boolean positioned) {
        this.positioned = positioned;
    }

    public FamilyMemberType getType() {
        return type;
    }
}
