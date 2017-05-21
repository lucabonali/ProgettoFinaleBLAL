package main.clientGame;

import main.api.messages.MessageGame;
import main.api.messages.MessageGameType;
import main.api.messages.MessageLogin;
import main.api.messages.MessageLoginType;
import main.api.types.ActionSpacesType;
import main.api.types.FamilyMemberType;
import main.api.types.ResourceType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
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
    public void startGame() throws RemoteException {
        try{
            MessageLogin msg = new MessageLogin(MessageLoginType.START_GAME);
            msg.setUsername(getUsername());
            out.writeObject(msg);
            out.flush();
            new Thread(this).start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addClientInterfaceToServer() throws RemoteException {
        //non fa nulla
    }

    @Override
    public void run() {
        try{
            while (true) {
                try {
                    MessageGame msg = (MessageGame) in.readObject();
                    switch (msg.getMessageGameType()) {
                        case ACTION_RESULT:
                            Map<ResourceType,Integer> qtaResourcesMap = msg.getQtaMap();
                            break;
                        case INFORMATION:
                            notifyMessage(msg.getContent());
                            break;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
