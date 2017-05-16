package game;

import api.Message;
import api.MessageType;
import api.PlayerInterface;
import board.FamilyMember;
import board.PersonalBoard;
import api.LorenzoException;

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
public class PlayerSocket implements PlayerInterface, Runnable {

    private Socket socketClient = null;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private String userName;
    private PersonalBoard personalBoard;
    private Game game;
    private int idPlayer;

    public PlayerSocket(Socket socketClient){
        this.socketClient = socketClient;

    }

    @Override
    public void doAction(Message msg) {
        FamilyMember familyMember = personalBoard.getFamilyMember(msg.getFamilyMemberType());
        try {
            game.doAction(this, msg, familyMember);
            //se arrivo ad eseguire questa parte vuol dire che l'azione è andata
            //a buon fine
            Message response = new Message(MessageType.ACTION_RESULT);
            response.setQtaList(personalBoard.getQtaResources());
            out.writeObject(response);
            out.flush();
        } catch (LorenzoException e) {
            Message response = new Message(MessageType.INFORMATION);
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
    public void createPersonalBoard(int id) {
        personalBoard = new PersonalBoard(id);
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
    public void run() {
        try {
            in = new ObjectInputStream(socketClient.getInputStream());
            userName = (String) in.readObject();
            out = new ObjectOutputStream(socketClient.getOutputStream());
            out.writeBoolean(true);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        while (socketClient.isConnected()) {
            try {
                Message msg = (Message) in.readObject();
                Message response;
                MessageType type = msg.getMessageType();
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
