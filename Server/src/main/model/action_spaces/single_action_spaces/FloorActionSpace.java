package main.model.action_spaces.single_action_spaces;

import main.servergame.exceptions.LorenzoException;
import main.servergame.exceptions.NewActionException;
import main.api.types.CardType;
import main.api.types.ResourceType;
import main.model.action_spaces.Action;
import main.model.board.DevelopmentCard;
import main.model.effects.development_effects.FixedIncrementEffect;
import main.model.fields.Resource;

import java.rmi.RemoteException;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi rappresenta un singolo piano della torre, essa
 * è una dei 5 tipi diversi di spazi azione
 */
public class FloorActionSpace extends ActionSpace {
    private DevelopmentCard DevelopmentCard;
    private CardType cardType;

    public FloorActionSpace(int value, CardType cardType, ResourceType resourceTypeQuickEffect) {
        super(value);
        this.cardType = cardType;
        Resource resource = null;
        if (value == 5)
            resource = new Resource(1, resourceTypeQuickEffect);
        else if (value == 7)
            resource = new Resource(2, resourceTypeQuickEffect);
        super.setEffect(new FixedIncrementEffect(resource)); //eventualmente null
    }

    public void setDevelopmentCard(DevelopmentCard DevelopmentCard){
        this.DevelopmentCard = DevelopmentCard;
    }

    public DevelopmentCard getDevelopmentCard(){
        return DevelopmentCard;
    }

    public void removeCard(){
        this.DevelopmentCard = null;
    }

    public CardType getCardType() {
        return cardType;
    }

    /**
     * Metodo che raccoglie la carta dalla torre , e la assegna al giocatore che ha attivato lo spazio azione
     * attraverso il piazzamento legittimo del familiare
     * @param action l'azione da eseguire
     * @throws LorenzoException in caso di problemi
     * @throws RemoteException in caso di problemi di connessione
     * @throws NewActionException in caso di effetto che mi consente una nuova mossa
     */
    @Override
    public void doAction(Action action) throws LorenzoException, RemoteException, NewActionException {
        if (getMinValue() > action.getValue())
            throw new LorenzoException("non hai abbastanza forza per eseguire l'azione!!");

        DevelopmentCard.setPlayer(action.getPlayer());
        setFamilyMember(action.getFamilyMember());
        if (getEffect() != null)
            getEffect().active(action.getPlayer());
        DevelopmentCard.activeQuickEffects();
    }





}
