package main.servergame;

import main.api.ClientInterface;
import main.api.exceptions.NewActionException;
import main.api.types.FamilyMemberType;
import main.api.exceptions.LorenzoException;
import main.api.PlayerInterface;
import main.controller.board.FamilyMember;
import main.controller.board.PersonalBoard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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

    /// metodi implementatti della PlayerInterface

    @Override
    public void shotDice() throws RemoteException, LorenzoException {
        getGame().shotDice(this);
    }


    @Override
    public void addClientInterface(ClientInterface clientInterface) throws RemoteException {
        setClientInterface(clientInterface);
    }

}
