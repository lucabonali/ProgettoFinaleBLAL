package main.servergame.socket;

import main.api.messages.MessageGame;
import main.api.messages.MessageGameType;
import main.controller.board.FamilyMember;
import main.servergame.AbstractPlayer;

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

    @Override
    public void notifyError(String errorMessage) throws RemoteException {
        printMsgToClient(errorMessage);
    }

    @Override
    public void updateResources() throws RemoteException {
        MessageGame response = new MessageGame(MessageGameType.ACTION_RESULT);
        response.setQtaMap(getPersonalBoard().getQtaResources());
        try {
            out.writeObject(response);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    // metodi eredita dalla PLAYER INTERFACE ////////////////////////////////////////

    @Override
    public void doAction(MessageGame msg) throws RemoteException {
        FamilyMember familyMember = getPersonalBoard().getFamilyMember(msg.getFamilyMemberType());
        getGame().doAction(this, msg, familyMember);
    }

    public void setSocketConnection(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        this.socketClient = socket;
        this.in = in;
        this.out = out;
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
