package game;

import api.*;
import board.FamilyMember;
import board.PersonalBoard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Luca
 * @author Andrea
 *
 * Classe che identifica il giocatore connesso tramite RMI
 */
public class PlayerRmi extends UnicastRemoteObject implements PlayerInterface {
    private ClientInterface clientInterface;
    private String userName;
    private PersonalBoard personalBoard;
    private int idPlayer;
    private Game game;

    public PlayerRmi( ClientInterface clientInterface) throws RemoteException {
        this.userName = clientInterface.getUserName();
        this.clientInterface = clientInterface;
        this.idPlayer = game.getId(this);

    }

    @Override
    public boolean setGame(int idGame) throws RemoteException {
        Game game = MainServer.gameMap.get(idGame);
        if(game.isFull())
            return false;
        this.game = game;
        game.addPlayer(this);
        return true;
    }

    @Override
    public List<Integer> getGamesMap() throws RemoteException {
        return new ArrayList<>(MainServer.gameMap.keySet());
    }

    @Override
    public void gameIsStarted() throws RemoteException {
        clientInterface.notifyMessage("La partita è iniziata");
    }

    @Override
    public void isYourTurn() throws RemoteException {
        clientInterface.notifyMessage("Tocca a te!!");
    }

    @Override
    public void setDiceValues(int orange, int white, int black) {
        personalBoard.setDiceValues(orange,white,black);
    }

    @Override
    public FamilyMember getFamilyMember(FamilyMemberType type) {
        return personalBoard.getFamilyMember(type);
    }

    @Override
    public void doAction(Message msg) throws RemoteException {
        FamilyMember familyMember = personalBoard.getFamilyMember(msg.getFamilyMemberType());
        try {
            game.doAction(this, msg, familyMember);
            clientInterface.updateResources(personalBoard.getQtaResources());
        } catch (LorenzoException e) {
            clientInterface.notifyMessage(e.getMessage());
        }
    }

    @Override
    public void createPersonalBoard(int id) {
        personalBoard = new PersonalBoard(id);
    }
}
