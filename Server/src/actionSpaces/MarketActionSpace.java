package actionSpaces;

/**
 * Created by Luca, Andrea on 11/05/2017.
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
