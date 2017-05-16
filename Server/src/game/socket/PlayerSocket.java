package game.socket;

import api.ClientInterface;
import api.LorenzoException;
import api.MessageGame;
import api.MessageGameType;
import board.FamilyMember;
import game.AbstractPlayer;
import game.MainServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica il giocatore connesso tramite Socket
 */
public class PlayerSocket extends AbstractPlayer implements Runnable {

    private Socket socketClient = null;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public PlayerSocket(String username) throws RemoteException {
        super(username);

    }

    public void setSocketConnection(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        this.socketClient = socket;
        this.in = in;
        this.out = out;
    }

    @Override
    public void doAction(MessageGame msg) {
        FamilyMember familyMember = getPersonalBoard().getFamilyMember(msg.getFamilyMemberType());
        try {
            getGame().doAction(this, msg, familyMember);
            //se arrivo ad eseguire questa parte vuol dire che l'azione è andata
            //a buon fine
            MessageGame response = new MessageGame(MessageGameType.ACTION_RESULT);
            response.setQtaList(getPersonalBoard().getQtaResources());
            out.writeObject(response);
            out.flush();
            try {
                out.writeObject(response);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (LorenzoException e) {
            MessageGame response = new MessageGame(MessageGameType.INFORMATION);
            response.setContent(e.getMessage());
            try {
                out.writeObject(response);
                out.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void run() {

        while (socketClient.isConnected()) {
            try {
                MessageGame msg = (MessageGame) in.readObject();
                MessageGame response;
                MessageGameType type = msg.getMessageGameType();
                switch (type) {
                    case ACTION:
                        doAction(msg);
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
