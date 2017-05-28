package main.client;

import main.api.messages.MessageAction;
import main.api.messages.MessageNewAction;
import main.api.messages.SocketProtocol;

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
            out.writeObject(SocketProtocol.LOGIN);
            out.flush();
            out.writeObject(getUsername());
            out.flush();
            out.writeObject(getPassword());
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
    public void doAction(MessageAction msg, int servantsToPay) {
        try {
            out.writeObject(msg);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doNewAction(MessageNewAction msg, int servantsToPay) throws RemoteException {

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

    }

    @Override
    public void run() {
        try{
            while (true) {
                try {
                    Object msg = in.readObject();
                    if (msg instanceof SocketProtocol){
                        switch ((SocketProtocol) msg){
                            case INFORMATION:
                                String response = (String)in.readObject();
                        }
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
