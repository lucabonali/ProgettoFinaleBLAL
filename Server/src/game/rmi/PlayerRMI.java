package game.rmi;

import api.ClientInterface;
import api.LorenzoException;
import api.MessageGame;
import controller.board.FamilyMember;
import game.AbstractPlayer;
import game.MainServer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Luca
 * @author Andrea
 *
 * Classe che identifica il giocatore connesso tramite RMI
 */
public class PlayerRMI extends AbstractPlayer {

    public PlayerRMI(String username) throws RemoteException {
        super(username);

    }

    @Override
    public List<Integer> getGamesMap() throws RemoteException {
        return new ArrayList<>(MainServer.gamesMap.keySet());
    }

    @Override
    public void addClientInterface(ClientInterface clientInterface) throws RemoteException {
        setClientInterface(clientInterface);
    }


    @Override
    public void doAction(MessageGame msg) throws RemoteException {
        FamilyMember familyMember = getPersonalBoard().getFamilyMember(msg.getFamilyMemberType());
        try {
            getGame().doAction(this, msg, familyMember);
            getClientInterface().updateResources(getPersonalBoard().getQtaResources());
        } catch (LorenzoException e) {
            getClientInterface().notifyMessage(e.getMessage());
        }
    }
}
