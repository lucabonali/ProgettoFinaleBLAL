package game.rmi;

import api.PlayerInterface;
import game.AbstractServer;
import game.Game;
import game.MainServer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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
        List<Game> games = new ArrayList<>(MainServer.gamesMap.values());
        for (Game g : games) {
            if(!g.isFull()){
                PlayerRMI playerRMI = new PlayerRMI(username);
                playerRMI.setGame(g);
                g.addPlayer(playerRMI);
                return playerRMI;
            }
        }
        return null;
    }
}
