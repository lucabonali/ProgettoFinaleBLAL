package board;

import api.FamilyMemberType;

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
    private final FamilyMemberType diceColor;


    /**
     * I quattro familiari di un giocatore avranno un riferimento alla stessa Plancia, passata del Costruttore
     * @param personalBoard
     */
    public FamilyMember(PersonalBoard personalBoard , FamilyMemberType diceColor){
        this.personalBoard = personalBoard;
        this.diceColor = diceColor;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public int getValue() {
        return value;
    }

    /**
     * Richiamato a inizio di ogni turno quando vengono tirati i dadi
     * @param value
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

    public FamilyMemberType getDiceColor() {
        return diceColor;
    }
}
