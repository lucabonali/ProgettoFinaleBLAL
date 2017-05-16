package game;

import api.ClientInterface;
import api.FamilyMemberType;
import api.LorenzoException;
import api.PlayerInterface;
import controller.board.FamilyMember;
import controller.board.PersonalBoard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author lampa
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

    public int calculateVictoryPoints(){
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

    public abstract void gameIsStarted() throws RemoteException;

    public abstract void isYourTurn() throws RemoteException;

    public abstract void youWin() throws RemoteException;

    public abstract void youLose() throws RemoteException;

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