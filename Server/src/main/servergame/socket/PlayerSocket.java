package main.servergame.socket;

import main.api.messages.MessageAction;
import main.api.messages.SocketProtocol;
import main.model.board.DevelopmentCard;
import main.servergame.AbstractPlayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
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

    // metodi ereditati e da implementare da ABSTRACT PLAYER //////////////////////////////////////

    @Override
    public void gameIsStarted(List<Integer> idList) throws RemoteException {

    }

    @Override
    public void isYourTurn() throws RemoteException {
        //mandare un messaggio particolare per notificare che è il suo turno
    }

    @Override
    public void isYourExcommunicationTurn() throws RemoteException {
        //da implementare
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
        //devo mandare al giocatore tutte le sue risorse e tutte le sue carte
    }

    @Override
    public void notifyRollDice() throws RemoteException {
        //da implementare
    }

    @Override
    public void sendDicesValues(int orange, int white, int black) throws RemoteException {
        //da implementare
    }

    @Override
    public void initializeBoard(List<DevelopmentCard> towersCardsList) throws RemoteException {
        //da implementare
    }

    @Override
    public void notifyEndMove() throws RemoteException {
        //da implementare
    }

    private void printMsgToClient(String content){
        try {
            out.writeObject(SocketProtocol.INFORMATION);
            out.flush();
            out.writeObject(content);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // metodi eredita dalla PLAYER INTERFACE ////////////////////////////////////////


    public void setSocketConnection(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        this.socketClient = socket;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {

        try{
            while (!socketClient.isClosed()) {
                try {
                    Object msg = in.readObject();
                    if (msg instanceof MessageAction){
                        doAction((MessageAction) msg);
                        break;
                    }
                    else if (msg instanceof SocketProtocol){
                        switch ((SocketProtocol) msg){
                            case SHOT_DICE:
                                int orange = in.readInt();
                                int white = in.readInt();
                                int black = in.readInt();
                                shotDice(orange, white, black);
                                break;
                            case EXCOMMUNICATION_CHOICE:
                                boolean choice = in.readBoolean();
                                excommunicationChoice(choice);
                                break;
                        }
                    }
                }
                catch (IOException | ClassNotFoundException e) {
                    try {
                        in.close();
                        in = null;
                        out.close();
                        out = null;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        finally {
            try {
                if (in != null && out != null){
                    in.close();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
