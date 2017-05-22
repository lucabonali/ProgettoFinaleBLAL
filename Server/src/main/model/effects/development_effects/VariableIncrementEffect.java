package main.model.effects.development_effects;

import main.model.board.DevelopmentCard;
import main.model.fields.Field;
import main.model.fields.Resource;
import main.api.types.CardType;
import main.api.types.ResourceType;
import main.servergame.AbstractPlayer;

import java.util.List;

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
        List<DevelopmentCard> list = player.getPersonalBoard().getCardsList(cardType);
        int qta = field.getQta()*list.size();
        Resource newRes = new Resource(qta, field.getType());
        player.getPersonalBoard().modifyResources(newRes);
    }

    /**
     * @param cod
     * @return
     */
    public static VariableIncrementEffect createInstance(String cod) {
        int qtaToIncrement = Integer.parseInt(cod.substring(0,1));
        char resToIncrement = cod.charAt(1);
        int qtaToCheck = Integer.parseInt(cod.substring(2,3));
        char toCheck = cod.charAt(3);
        CardType cardType;
        switch (toCheck){
            case EffectsCreator.CHAR_BUILDINGS:
                cardType = CardType.BUILDING;
                break;
            case EffectsCreator.CHAR_CHARACTERS:
                cardType = CardType.CHARACTER;
                break;
            case EffectsCreator.CHAR_TERRITORY:
                cardType = CardType.TERRITORY;
                break;
            default: //ventures
                cardType = CardType.VENTURES;
                break;
        }
        Resource res;
        switch (resToIncrement){
            case EffectsCreator.CHAR_VICTORY:
                res = new Resource(qtaToIncrement, ResourceType.VICTORY);
                break;
            default: //coins
                res = new Resource(qtaToIncrement, ResourceType.COINS);
                break;

        }
        return new VariableIncrementEffect(res, cardType);
    }
}
