package controller.effects;

import controller.types.CardType;
import controller.board.PersonalBoard;
import controller.fields.Field;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi rappresenta un effetto che mi incrementa una specifica
 * risorsa in base al numero di carte (di un certo tipo) in possesso
 */
public class VariableIncrementEffect implements Effect{
    private Field field;
    private CardType cardType;

    /**
     * mi incrementa la risorsa field, di una quantità pari al numero
     * di carte del tipo cardType in possesso al giocatore
     * @param field risorsa da modificare
     * @param typeCard tipo di carta da controllarne la quantità in possesso
     */
    public VariableIncrementEffect(Field field, CardType typeCard) {
        this.field = field;
        this.cardType = typeCard;
    }

    @Override
    public void active(PersonalBoard personalBoard) {
        //da implemetare
    }
}
