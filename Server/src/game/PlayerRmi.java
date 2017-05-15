package game;

import api.ClientInterface;
import api.PlayerInterface;

import java.io.IOException;
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
    private ClientInterface clientInterface;

    public PlayerRmi(Game game, ClientInterface clientInterface) throws RemoteException {
        this.game = game;
        this.clientInterface = clientInterface;
        this.idPlayer = game.getId(this);

    }

    @Override
    public void setString(String name) throws IOException, RemoteException {
        game.setString(name);
    }

    @Override
    public void writeToClient(String name) throws RemoteException {
        clientInterface.printString(name);
    }

    /** Metodo che aggiunge il giocatore alla partita, come playerRequest nella classe Server, che verrà richiamato dal client
    *   come primo metodo subito dopo aver scaricato lo stub che implementa PlayerInterface
    */



}
