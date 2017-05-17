package main.controller.effects;

import main.controller.board.Card;
import main.controller.fields.Field;
import main.controller.fields.Resource;
import main.api.types.CardType;
import main.api.types.ResourceType;
import main.game.AbstractPlayer;

import java.util.List;

import static main.controller.effects.EffectsCreator.*;

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
    public void active(AbstractPlayer player) {
        List<Card> list = player.getPersonalBoard().getCardsList(cardType);
        int qta = field.getQta()*list.size();
        Resource newRes = new Resource(qta, field.getType());
        player.getPersonalBoard().modifyResources(newRes);
    }

    public static VariableIncrementEffect createInstance(String cod) {
        int qtaToIncrement = Integer.parseInt(cod.substring(0,1));
        char resToIncrement = cod.charAt(1);
        int qtaToCheck = Integer.parseInt(cod.substring(2,3));
        char toCheck = cod.charAt(3);
        CardType cardType;
        switch (toCheck){
            case CHAR_BUILDINGS:
                cardType = CardType.BUILDING;
                break;
            case CHAR_CHARACTERS:
                cardType = CardType.CHARACTER;
                break;
            case CHAR_TERRITORY:
                cardType = CardType.TERRITORY;
                break;
            default: //ventures
                cardType = CardType.VENTURES;
                break;
        }
        Resource res;
        switch (resToIncrement){
            case CHAR_VICTORY:
                res = new Resource(qtaToIncrement, ResourceType.VICTORY);
                break;
            default: //coins
                res = new Resource(qtaToIncrement, ResourceType.COINS);
                break;

        }
        return new VariableIncrementEffect(res, cardType);
    }
}
