package actionSpaces;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica il mercato, il quale potrà essere
 * di 4 tipi identificati da una enumerazione
 */
public class MarketActionSpace extends ActionSpace {
    private MarketActionType type;

    public MarketActionSpace(MarketActionType marketActionType){
        super(1);
        this.type = marketActionType;
    }


    @Override
    public void doAction(Action action) {

    }
}
