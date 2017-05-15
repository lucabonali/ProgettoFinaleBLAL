package game;

import board.PersonalBoard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


/**
 * @author Luca
 * @author Andrea
 *
 * Classe che identifica il giocatore connesso tramite RMI
 */
public class PlayerRmi extends UnicastRemoteObject implements PlayerInterface {
    private int idPlayer;
    private Game game;


    public PlayerRmi(Game game) throws RemoteException {
        this.game = game;
        this.idPlayer = game.getId(this);
    }

    /** Metodo che aggiunge il giocatore alla partita, come playerRequest nella classe ServerPlayer, che verrà richiamato dal client
    *   come primo metodo subito dopo aver scaricato lo stub che implementa PlayerInterface
    */



}
