package game;

import board.PersonalBoard;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica il giocatore connesso tramite Socket
 */
public class PlayerSocket implements PlayerInterface, Runnable {
    private PersonalBoard personalBoard;
    private Game game;
    private ServerSocket server = null;
    private Socket socketClient = null;
    private DataInputStream in;
    private DataOutputStream out;


    private int idPlayer;
    private int port ;

    public PlayerSocket(Socket socketClient, Game game){
        this.socketClient = socketClient;
        this.game = game;
    }


    @Override
    public void run() {
        try {
                in = new DataInputStream(socketClient.getInputStream());
                out = new DataOutputStream(socketClient.getOutputStream());



        }
        catch (IOException e){

        }
    }
}
