package main.clientGame;

import main.api.messages.*;
import main.api.types.ActionSpacesType;
import main.api.types.FamilyMemberType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

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
            MessageLogin msgToLogin = new MessageLogin(MessageLoginType.LOGIN);
            msgToLogin.setUsername(getUsername());
            msgToLogin.setPassword(getPassword());
            out.writeObject(msgToLogin);
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            boolean resp = in.readBoolean();
            System.out.println(resp);
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
            MessageLogin msg = new MessageLogin(MessageLoginType.START_GAME);
            msg.setUsername(getUsername());
            msg.setGameMode(gameMode);
            out.writeObject(msg);
            out.flush();
            new Thread(this).start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doAction() {
        try {
            MessageGame msg = new MessageGame(MessageGameType.ACTION);
            msg.setFamilyMemberType(FamilyMemberType.ORANGE_DICE);
            msg.setActionSpacesType(ActionSpacesType.COUNCIL);
            out.writeObject(msg);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void shotDice(int orange, int white, int black) throws IOException {
        out.writeObject(SocketProtocol.SHOT_DICE);
        out.flush();
        out.writeInt(orange);
        out.flush();
        out.writeInt(white);
        out.flush();
        out.writeInt(black);
        out.flush();
    }

    @Override
    public void endMove() throws RemoteException {

    }

    @Override
    public void run() {
        try{
            //boolean resp = in.readBoolean();
            //System.out.println(resp);
            while (true) {
                try {
                    Object msg = in.readObject();
                    if (msg instanceof MessageGame){
                        MessageGame realMsg = (MessageGame) msg;
                        notifyMessage(realMsg.getContent());
                    }
//                    MessageGame msg = (MessageGame) in.readObject();
//                    switch (msg.getMessageGameType()) {
//                        case ACTION_RESULT:
//                            Map<ResourceType,Integer> qtaResourcesMap = msg.getQtaMap();
//                            break;
//                        case INFORMATION:
//                            notifyMessage(msg.getContent());
//                            break;
//                    }
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
