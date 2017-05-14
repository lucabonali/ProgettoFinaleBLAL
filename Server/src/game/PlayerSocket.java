package game;

import board.PersonalBoard;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica il giocatore connesso tramite Socket
 */
public class PlayerSocket implements Player {
    private PersonalBoard personalBoard;
    private ServerSocket server = null;
    private Socket socketClient = null;

    private int idPlayer;
    private int port ;

    public PlayerSocket(Socket socketClient, int idPlayer, PersonalBoard personalBoard){
        this.socketClient = socketClient;
        this.idPlayer = idPlayer;
        this.personalBoard = personalBoard;
    }





}
