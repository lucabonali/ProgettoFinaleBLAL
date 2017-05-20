package main.servergame;

import main.MainServer;
import main.api.ClientInterface;
import main.api.ServerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea
 * @author Luca
 */
public abstract class AbstractServer extends UnicastRemoteObject implements ServerInterface{

    protected AbstractServer() throws RemoteException {

    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        if (MainServer.playersMap.containsKey(username)) {
            return MainServer.playersMap.get(username).equals(password);
        }
        else {
            MainServer.playersMap.put(username, password);
            return true;
        }
    }

    /**
     * metodo richiamato dal clientGame nella classe clientRMIImpl che serve al serverRMI per ricevere un' istanza
     * del clientGame RMI, e poter quindi chiamare i metodi su di essa
     * @param clientInterface
     */
    @Override
    public void addPlayerRMI(ClientInterface clientInterface) throws RemoteException {   }

    public Game getFreeGame() {
        List<Game> games = new ArrayList<>(MainServer.gamesMap.values());
        for (Game g : games) {
            if(!g.isFull()){
                //non appena ne trovo una non piena la setto ed esco da ciclo
                return g;
            }
        }
        Game game = new Game();
        MainServer.gamesMap.put(MainServer.counter, game);
        MainServer.counter++;
        return game;
    }
}
