package main.clientGame;

import main.api.messages.MessageLogin;
import main.api.messages.MessageLoginType;

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
    public void doAction() {
        //da implementare
    }

    @Override
    public boolean login() {
        return false;
    }

    @Override
    public void startGame() throws RemoteException {
        //da implementare
    }

    @Override
    public void addClientInterfaceToServer() throws RemoteException {
        //da implementare
    }

    @Override
    public void run() {
        try {
            this.socket = new Socket("localhost", PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            MessageLogin msgToLogin = new MessageLogin(MessageLoginType.LOGIN);
            msgToLogin.setUsername("andrea");
            msgToLogin.setPassword("ciao");
            out.writeObject(msgToLogin);
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            boolean resp = in.readBoolean();
            System.out.println(resp);

            /*while (true) {
                Message msg = (Message) in.readObject();
                switch (msg.getMessageType()) {
                    case ACTION_RESULT:
                        List<Integer> qtaResourcesList = msg.getQtaList();
                        break;
                    case INFORMATION:
                        System.out.println(msg.getContent());
                        break;
                }
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
