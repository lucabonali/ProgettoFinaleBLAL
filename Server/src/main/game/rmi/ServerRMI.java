package main.game.rmi;

import main.api.PlayerInterface;
import main.game.AbstractServer;
import main.game.Game;
import main.game.socket.PlayerSocket;

import java.rmi.RemoteException;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica l'oggetto remoto che il client RMI userà direttamente per la connessione
 * scaricando l'oggetto dal registro.
 */
public class ServerRMI extends AbstractServer {
    public ServerRMI() throws RemoteException {

    }

    @Override
    public PlayerInterface startGame(String username) throws RemoteException {
        Game game = getFreeGame(); //la prima partita libera trovata
        PlayerRMI playerRMI = new PlayerRMI(username);
        playerRMI.setGame(getFreeGame());
        game.addPlayer(playerRMI);
        return playerRMI;
    }
}
