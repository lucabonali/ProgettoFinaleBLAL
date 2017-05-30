package main.game_server.socket;

import main.api.messages.MessageAction;
import main.api.messages.MessageNewAction;
import main.api.messages.SocketProtocol;
import main.api.types.CardType;
import main.api.types.ResourceType;
import main.model.board.DevelopmentCard;
import main.game_server.AbstractPlayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;


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
    public void gameIsStarted(List<Integer> idList, List<String> codeExcomList) throws RemoteException {
        try {
            out.writeObject(SocketProtocol.IS_GAME_STARTED);
            out.flush();
            out.writeInt(getIdPlayer());
            out.flush();
            out.writeObject(idList);
            out.flush();
            out.writeObject(codeExcomList);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void isYourTurn() throws RemoteException {
        try {
            out.writeObject(SocketProtocol.YOUR_TURN);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void isYourExcommunicationTurn() throws RemoteException {
        try {
            out.writeObject(SocketProtocol.YOUR_EXCOMMUNICATION_TURN);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void youWin() throws RemoteException {
        try {
            out.writeObject(SocketProtocol.GAME_ENDED);
            out.flush();
            out.writeObject("Hai vinto, complimenti!!");
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void youLose() throws RemoteException {
        try {
            out.writeObject(SocketProtocol.GAME_ENDED);
            out.flush();
            out.writeObject("Hai perso :(");
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyNewAction(int value, char codeAction) throws RemoteException {
        try {
            out.writeObject(SocketProtocol.NEW_ACTION);
            out.flush();
            out.writeInt(value);
            out.flush();
            out.writeChar(codeAction);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyError(String errorMessage) throws RemoteException {
        printMsgToClient(errorMessage);
    }

    @Override
    public void updateMove() throws RemoteException {
        try {
            out.writeObject(SocketProtocol.UPDATE_RESOURCES);
            out.flush();
            out.writeObject(getPersonalBoard().getQtaResources());
            out.flush();
            out.writeObject(SocketProtocol.UPDATE_PERSONAL_CARDS);
            out.flush();
            out.writeObject(getPersonalBoard().getPersonalCardsMap());
            out.flush();
            getGame().notifyAllPlayers(this, getIdPlayer(), getPersonalBoard().getPersonalCardsMap(), getPersonalBoard().getQtaResources());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOpponentMove(int id, Map<CardType, List<String>> personalcardsMap, Map<ResourceType, Integer> qtaResourcesMap) throws RemoteException {
        try {
            out.writeObject(SocketProtocol.OPPONENT_MOVE);
            out.flush();
            out.writeInt(id);
            out.flush();
            out.writeObject(personalcardsMap);
            out.flush();
            out.writeObject(qtaResourcesMap);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyRollDice() throws RemoteException {
        try {
            out.writeObject(SocketProtocol.HAVE_TO_SHOT);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excommunicate(int id, int period) throws RemoteException {
        getGame().notifyAllPlayers(this, period);
        opponentExcommunicate(id, period);
    }

    @Override
    public void opponentExcommunicate(int idPlayer, int period) throws RemoteException {
        try {
            out.writeObject(SocketProtocol.EXCOMMUNICATE);
            out.flush();
            out.writeInt(idPlayer);
            out.flush();
            out.writeInt(period);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendDicesValues(int orange, int white, int black) throws RemoteException {
        try {
            out.writeObject(SocketProtocol.DICE_VALUES);
            out.flush();
            out.writeInt(orange);
            out.flush();
            out.writeInt(white);
            out.flush();
            out.writeInt(black);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initializeBoard(List<DevelopmentCard> towersCardsList) throws RemoteException {
        try {
            out.writeObject(SocketProtocol.TOWERS_CARDS);
            out.flush();
            out.writeObject(towersCardsList);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyEndMove() throws RemoteException {
        try {
            out.writeObject(SocketProtocol.END_MOVE);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyPrivilege() throws RemoteException {
        try {
            out.writeObject(SocketProtocol.PRIVILEGE);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
                    else if (msg instanceof MessageNewAction) {
                        doNewAction((MessageNewAction) msg);
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
                            case END_MOVE:
                                endMove();
                                break;
                            case SURRENDER:
                                surrender();
                                break;
                            case CONVERT_PRIVILEGE:
                                int qta = in.readInt();
                                ResourceType type = (ResourceType) in.readObject();
                                convertPrivilege(qta, type);
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
