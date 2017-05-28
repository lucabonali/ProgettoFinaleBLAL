package main.game_server.rmi;

import main.api.ClientInterface;
import main.api.PlayerInterface;
import main.model.Game;
import main.game_server.AbstractServer;

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
    public PlayerInterface startGame(String username, int gameMode, ClientInterface client) throws RemoteException {
        Game game = getFreeGame(gameMode); //la prima partita libera trovata
        PlayerRMI playerRMI = new PlayerRMI(username);
        playerRMI.addClientInterface(client);
        playerRMI.setGame(game);
        game.addPlayer(playerRMI);
        return playerRMI;
    }
}
