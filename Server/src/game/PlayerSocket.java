package game;

import api.PlayerInterface;
import board.PersonalBoard;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;


/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica il giocatore connesso tramite Socket
 */
public class PlayerSocket implements PlayerInterface, Runnable {

    private Socket socketClient = null;
    private DataInputStream in;
    private DataOutputStream out;

    private PersonalBoard personalBoard;
    private Game game;

    private int idPlayer;

    public PlayerSocket(Socket socketClient, DataInputStream in, DataOutputStream out, Game game){
        this.socketClient = socketClient;
        this.in = in;
        this.out = out;
        this.game = game;
        this.idPlayer = game.getId(this);
    }

    @Override
    public void setString(String name) throws IOException {
        game.setString(name);
    }

    @Override
    public void writeToClient(String name) throws IOException, RemoteException {
        char[] chars = name.toCharArray();
        out.writeChar(chars[0]);
    }

    @Override
    public void run() {
        try {
            while (true) {
                out.writeChars("S");
                out.flush();
                String cmd = in.readLine();
                setString(cmd);
            }
        }
        catch (IOException e){

        }
    }
}
