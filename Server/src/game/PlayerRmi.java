package game;

import api.PlayerClientInterface;
import api.PlayerInterface;

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
    private PlayerClientInterface playerClientInterface;

    public PlayerRmi(Game game, PlayerClientInterface playerClientInterface) throws RemoteException {
        this.game = game;
        this.playerClientInterface = playerClientInterface;
        this.idPlayer = game.getId(this);

    }

    /** Metodo che aggiunge il giocatore alla partita, come playerRequest nella classe ServerPlayer, che verrà richiamato dal client
    *   come primo metodo subito dopo aver scaricato lo stub che implementa PlayerInterface
    */



}
