package game;

import api.ClientInterface;
import api.Message;
import api.PlayerInterface;
import board.FamilyMember;
import board.PersonalBoard;
import api.LorenzoException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


/**
 * @author Luca
 * @author Andrea
 *
 * Classe che identifica il giocatore connesso tramite RMI
 */
public class PlayerRmi extends UnicastRemoteObject implements PlayerInterface {
    private ClientInterface clientInterface;

    private PersonalBoard personalBoard;
    private int idPlayer;
    private Game game;

    public PlayerRmi(Game game, ClientInterface clientInterface) throws RemoteException {
        this.game = game;
        this.clientInterface = clientInterface;
        this.idPlayer = game.getId(this);

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
