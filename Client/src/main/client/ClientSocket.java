package main.client;

import main.api.messages.MessageAction;
import main.api.messages.MessageNewAction;
import main.api.messages.SocketProtocol;
import main.api.types.CardType;
import main.api.types.ResourceType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * Classe che identifica i messaggi dal server tramite il socket e che modifica l' interfaccia utente
 * @author Andrea
 * @author Luca
 */
public class ClientSocket extends AbstractClient implements Runnable{
    private static ClientSocket instanceSocket;
    private static final int PORT = 4000;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientSocket(String username, String password) throws RemoteException {
        super(username, password);
    }

    @Override
    public boolean login() {
        try {
            this.socket = new Socket("localhost", PORT);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(SocketProtocol.LOGIN);
            out.flush();
            out.writeObject(getUsername());
            out.flush();
            out.writeObject(getPassword());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            boolean resp = in.readBoolean();
//            System.out.println(resp);
            return resp;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void startGame(int gameMode) throws RemoteException {
        try{
            out.writeObject(SocketProtocol.START_GAME);
            out.flush();
            out.writeObject(getUsername());
            out.flush();
            out.writeInt(gameMode);
            out.flush();
            new Thread(this).start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doAction(MessageAction msg, int servantsToPay) throws RemoteException {
        if (servantsToPay <= getQtaResource(ResourceType.SERVANTS)) {
            msg.setValue(servantsToPay);
            try {
                out.writeObject(SocketProtocol.ACTION);
                out.flush();
                out.writeObject(msg);
                out.flush();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            notifyMessage("Non hai abbastanza servitori");

    }

    @Override
    public void doNewAction(MessageNewAction msg, int servantsToPay) throws RemoteException {
        if (servantsToPay <= getQtaResource(ResourceType.SERVANTS)) {
            msg.setAdditionalValue(servantsToPay);
            try {
                out.writeObject(SocketProtocol.NEW_ACTION);
                out.flush();
                out.writeObject(msg);
                out.flush();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            notifyMessage("Non hai abbastanza servitori");
    }

    @Override
    public void shotDice(int orange, int white, int black) throws RemoteException{
        try {
            out.writeObject(SocketProtocol.SHOT_DICE);
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

    /**
     * metodo che rappresenta la scelta riguardante la decisione di farsi scomunicare
     * oppure dare sostegno alla chiesa
     * @param choice true accetto la scomunica, false do sostegno
     * @throws RemoteException
     */
    @Override
    public void excommunicationChoice(boolean choice) throws RemoteException {
        try {
            out.writeObject(SocketProtocol.EXCOMMUNICATION_CHOICE);
            out.flush();
            out.writeBoolean(choice);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endMove() throws RemoteException {
        try {
            out.writeObject(SocketProtocol.END_MOVE);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void convertPrivilege(int qta, ResourceType type) throws RemoteException {
        try {
            out.writeObject(SocketProtocol.CONVERT_PRIVILEGE);
            out.flush();
            out.writeInt(qta);
            out.flush();
            out.writeObject(type);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surrender() throws RemoteException {
        try {
            out.writeObject(SocketProtocol.SURRENDER);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exit() throws RemoteException {
        try {
            surrender();
            out.writeObject(SocketProtocol.EXIT);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String response;
        boolean connect = true;
        try{
            while (connect) {
                try {
                    SocketProtocol msg = (SocketProtocol) in.readObject();
                    switch (msg){
                        case INFORMATION:
                            response = (String)in.readObject();
                            notifyMessage(response);
                            break;
                        case GAME_ENDED:
                            response = (String)in.readObject();
                            gameEnded(response);
                            break;
                        case EXCOMMUNICATE:
                            int id = in.readInt();
                            int period = in.readInt();
                            excommunicate(id, period);
                            break;
                        case END_MOVE:
                            notifyEndMove();
                            break;
                        case YOUR_EXCOMMUNICATION_TURN:
                            notifyYourExcommunicationTurn();
                            break;
                        case YOUR_TURN:
                            notifyYourTurn();
                            break;
                        case NEW_ACTION:
                            int value = in.readInt();
                            char codeAction = in.readChar();
                            notifyNewAction(value, codeAction);
                            break;
                        case PRIVILEGE:
                            notifyPrivilege();
                            break;
                        case HAVE_TO_SHOT:
                            notifyHaveToShotDice();
                            break;
                        case DICE_VALUES:
                            int orange = in.readInt();
                            int white = in.readInt();
                            int black = in.readInt();
                            setDiceValues(orange, white, black);
                            break;
                        case OPPONENT_MOVE:
                            int opponentId = in.readInt();
                            //dovrei fare un check su instanceof
                            Map<CardType, List<String>> cardsMap = (Map<CardType, List<String>>) in.readObject();
                            Map<ResourceType, Integer> qtaResourcesMap = (Map<ResourceType, Integer>) in.readObject();
                            opponentMove(opponentId, cardsMap, qtaResourcesMap);
                            break;
                        case OPPONENT_MEMBER_MOVE:
                            int opId = in.readInt();
                            MessageAction msgAction = (MessageAction) in.readObject();
                            opponentFamilyMemberMove(opId, msgAction);
                            break;
                        case UPDATE_PERSONAL_CARDS:
                            Map<CardType, List<String>> personalCardsMap = (Map<CardType, List<String>>) in.readObject();
                            updatePersonalCards(personalCardsMap);
                            break;
                        case UPDATE_RESOURCES:
                            Map<ResourceType, Integer> personalQtaResourcesMap = (Map<ResourceType, Integer>) in.readObject();
                            updateResources(personalQtaResourcesMap);
                            break;
                        case TOWERS_CARDS:
                            List<String> boardCards = (List<String>) in.readObject();
                            setTowersCards(boardCards);
                            break;
                        case ORDER:
                            List<Integer> orderList = (List<Integer>) in.readObject();
                            notifyTurnOrder(orderList);
                            break;
                        case IS_GAME_STARTED:
                            int myId = in.readInt();
                            Map<Integer, String> opponents = (Map<Integer, String>) in.readObject();
                            List<String> codeExcomList = (List<String>) in.readObject();
                            isGameStarted(myId, opponents, codeExcomList);
                            break;
                        case OPPONENT_SURRENDER:
                            int surrendId = in.readInt();
                            notifyOpponentSurrender(surrendId);
                            break;
                        case EXIT:
                            connect = false;
                            System.exit(0);
                            break;
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
