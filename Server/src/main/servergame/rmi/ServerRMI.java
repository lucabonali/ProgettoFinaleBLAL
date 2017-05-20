package main.servergame.rmi;

import main.api.ClientInterface;
import main.api.PlayerInterface;
import main.servergame.AbstractServer;
import main.servergame.Game;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica l'oggetto remoto che il clientGame RMI userà direttamente per la connessione
 * scaricando l'oggetto dal registro.
 */
public class ServerRMI extends AbstractServer {
    //Lista dei giocatorei connessi tramite RMI
    private static List<ClientInterface> playerRMIList; // Interfaccia del client su cui posso richiamare i metodi

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

    /**
     * aggiunge l' interfaccia del clientGame nella lista di giocatori RMI
     * @param clientInterface
     */
    public void addPlayerRMI(ClientInterface clientInterface){
        playerRMIList.add(clientInterface);
    }
}
