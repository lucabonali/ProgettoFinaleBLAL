package main.game.socket;

import main.api.exceptions.LorenzoException;
import main.api.messages.MessageGame;
import main.api.messages.MessageGameType;
import main.controller.board.FamilyMember;
import main.game.AbstractPlayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;


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

    // metodi ereditati e da implementare da ABSTRACT PLAYER //////////////////////////////////////

    @Override
    public void gameIsStarted() throws RemoteException {
        printMsgToClient("la partita è iniziata");
    }

    @Override
    public void isYourTurn() throws RemoteException {
        printMsgToClient("è il tuo turno!");
    }

    @Override
    public void youWin() throws RemoteException {
        printMsgToClient("Hai vinto, complimenti!!");
    }

    @Override
    public void youLose() throws RemoteException {
        printMsgToClient("Hai perso :(");
    }

    @Override
    public void notifyNewAction(int value, char codeAction) throws RemoteException {

    }

    private void printMsgToClient(String content){
        MessageGame outMsg = new MessageGame(MessageGameType.INFORMATION);
        outMsg.setContent(content);
        try {
            out.writeObject(outMsg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSocketConnection(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        this.socketClient = socket;
        this.in = in;
        this.out = out;
    }

    // metodi eredita dalla PLAYER INTERFACE ////////////////////////////////////////

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
