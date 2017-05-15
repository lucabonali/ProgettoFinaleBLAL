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

    private PersonalBoard personalBoard;
    private Game game;
    private int idPlayer;

    public PlayerSocket(Socket socketClient, ObjectInputStream in, ObjectOutputStream out, Game game){
        this.socketClient = socketClient;
        this.in = in;
        this.out = out;
        this.game = game;
        this.idPlayer = game.getId(this);
    }

    @Override
    public void doAction(Message msg) {
        FamilyMember familyMember = personalBoard.getFamilyMember(msg.getFamilyMemberType());
        try {
            game.doAction(this, msg, familyMember);
        } catch (LorenzoException e) {
            Message response = new Message(MessageType.INFORMATION);
            response.setContent(e.getMessage());
            try {
                out.writeObject(response);
                out.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void createPersonalBoard(int id) {
        personalBoard = new PersonalBoard(id);
    }


    @Override
    public void run() {
        while (socketClient.isConnected()) {
            try {
                Message msg = (Message) in.readObject();
                Message response;
                MessageType type = msg.getMessageType();
                switch (type) {
                    case ACTION:
                        doAction(msg);
                        //se arrivo ad eseguire questa parte vuol dire che l'azione è andata
                        //a buon fine
                        response = new Message(MessageType.ACTION_RESULT);
                        response.setQtaList(personalBoard.getQtaResources());
                        out.writeObject(response);
                        out.flush();
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
