package actionSpaces;

import board.Card;
import types.CardType;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi rappresenta un singolo piano della torre, essa
 * è una dei 5 tipi diversi di spazi azione
 */
public class FloorActionSpace extends ActionSpace {
    private Card card;
    private CardType cardType;

    public FloorActionSpace(int value, CardType cardType) {
        super(value);
        this.cardType = cardType;
    }

    public void setCard(Card card){
        this.card = card;
    }

    public Card getCard(){
        return card;
    }

    public void removeCard(){
        this.card = null;
    }

    public CardType getCardType() {
        return cardType;
    }

    @Override
    public void doAction(Action action){
        // dai implementare
    }





}
