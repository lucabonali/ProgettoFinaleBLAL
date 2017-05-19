package main.servergame;

import main.api.ClientInterface;
import main.api.PlayerInterface;
import main.api.exceptions.LorenzoException;
import main.api.exceptions.NewActionException;
import main.api.messages.MessageGame;
import main.api.types.FamilyMemberType;
import main.model.board.Card;
import main.model.board.FamilyMember;
import main.model.board.PersonalBoard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * @author Andrea,Luca
 */
public abstract class AbstractPlayer extends UnicastRemoteObject implements PlayerInterface {
    private ClientInterface clientInterface;
    private String userName;
    private PersonalBoard personalBoard;
    private int idPlayer;
    private Game game;

    public AbstractPlayer(String userName) throws RemoteException {
        this.userName = userName;
    }

    public void setClientInterface(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    public void setGame(Game game) {
        this.game = game;
        idPlayer = game.getId(this);
    }

    public int calculateVictoryPoints() throws RemoteException, NewActionException {
        return personalBoard.calculateVictoryPoints();
    }

    /**
     * metodo che mi setta il valore a tutti i miei familiari
     * @param orange valore dell'arancione
     * @param white valore del bianco
     * @param black valore del nero
     */
    public void setDiceValues(int orange, int white, int black) {
        personalBoard.setDiceValues(orange,white,black);
    }

    public FamilyMember getFamilyMember(FamilyMemberType type) {
        return personalBoard.getFamilyMember(type);
    }

    public void createPersonalBoard(int id) {
        personalBoard = new PersonalBoard(id);
    }

    public PersonalBoard getPersonalBoard() {
        return this.personalBoard;
    }

    public ClientInterface getClientInterface() {
        return this.clientInterface;
    }

    public Game getGame() {
        return this.game;
    }

    /**
     * metodo che mi rimuove tutti i familiari dalle loro postazione
     * cioè mi setterà a false il boolean isPositioned di ciascuno.
     */
    public void removeAllFamilyMembers(){
        personalBoard.removeAllFamilyMembers();
    };

    /**
     * mi notifica che la partita è cominciata
     * @throws RemoteException
     */
    public abstract void gameIsStarted() throws RemoteException;

    /**
     * mi notifica che è il mio turno
     * @throws RemoteException
     */
    public abstract void isYourTurn() throws RemoteException;

    /**
     * mi notifica che ho vinto
     * @throws RemoteException
     */
    public abstract void youWin() throws RemoteException;

    /**
     * mi notifica che ho perso
     * @throws RemoteException
     */
    public abstract void youLose() throws RemoteException;

    /**
     * mi notifica che devo fare un'altra azione
     * @param value valore dell'azione
     * @param codeAction codice che mi identifica che tipo di azione posso fare
     * @throws RemoteException
     */
    public abstract void notifyNewAction(int value, char codeAction) throws RemoteException;

    /**
     * mi notifica un messaggio di errore
     * @param message messaggio
     * @throws RemoteException
     */
    public abstract void notifyError(String message) throws RemoteException;

    /**
     * mi notifica i cambiamenti nella mia plancia in seguito alla mia mossa
     * @throws RemoteException
     */
    public abstract void updateResources() throws RemoteException;

    /**
     * metodo che viene chiamato per inizializzare il turno, cioè mi invia al
     * client tutte le carte che sono state pescate in questo turno
     * @param towersCardsList lista di stringhe che mi indica i nomi delle carte pescate
     * @throws RemoteException
     */
    public abstract void initializeBoard(List<Card> towersCardsList) throws RemoteException;

    /// metodi implementatti della PlayerInterface

    @Override
    public void shotDice() throws RemoteException, LorenzoException {
        getGame().shotDice(this);
    }


    @Override
    public void addClientInterface(ClientInterface clientInterface) throws RemoteException {
        setClientInterface(clientInterface);
    }

    @Override
    public void doAction(MessageGame msg) throws RemoteException {
        FamilyMember familyMember = getPersonalBoard().getFamilyMember(msg.getFamilyMemberType());
        getGame().doAction(this, msg, familyMember);
    }

}
