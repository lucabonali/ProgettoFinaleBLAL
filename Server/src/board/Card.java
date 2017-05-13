package board;

import effects.Effect;
import fields.Field;

import java.util.List;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi rappresenta una singola carta sviluppo
 */

public class Card {
    private PersonalBoard personalBoard;
    private final CardType type;
    private final String name;
    private final List<Field> costs;
    private final List<Effect> quickEffects;
    private final List<Effect> permanentEffects;
    private final int period;

    public Card(CardType type, String name, List<Field> costs,
                List<Effect> qeffs, List<Effect> peffs, int period){
        this.type = type;
        this.name = name;
        this.costs = costs;
        this.quickEffects = qeffs;
        this.permanentEffects = peffs;
        this.period = period;
    }

    public CardType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<Field> getCosts() {
        return costs;
    }

    public List<Effect> getQuickEffects() {
        return quickEffects;
    }

    public List<Effect> getPermanentEffects() {
        return permanentEffects;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public void setPersonalBoard(PersonalBoard personalBoard) {
        this.personalBoard = personalBoard;
    }

    public int getPeriod() {
        return period;
    }

    /**
     * chiama il metodo che modifica le risorse nella personalBoard
     * passandogli la lista dei costi che sarà tutto negativo
     */
    public void activeCosts(){

    }

    /**
     * metodo che attiva tutti gli effetti immediati chiamando il metodo
     * active(PersonalBoard) di ciascun effetto.
     */
    public void activeQuickEffects() {
        for(Effect effect: quickEffects) {
            effect.active(personalBoard);
        }
    }

    public void activePermanentEffects() {
        for(Effect effect: permanentEffects) {
            effect.active(personalBoard);
        }    }
}
