package main.game_server;

import main.MainServer;
import main.api.ServerInterface;
import main.model.Game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import static main.MainServer.*;

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

    @Override
    public boolean logout(String username) throws RemoteException {
        if (MainServer.playersMap.containsKey(username)) {
            MainServer.playersMap.remove(username);
            return true;
        }
        return false;
    }

    /**
     * mi restituisce la prima partita libera, eventualmente appena creata
     * del tipo passato come parametro
     * @param gameMode
     * @return
     */
    public Game getFreeGame(int gameMode) {
        List<Game> games = null;
        switch (gameMode){
            case RANDOM:
                games = new ArrayList<>(MainServer.randomGamesMap.values());
                break;
            case TWO_PLAYERS:
                games = new ArrayList<>(MainServer.twoPlayerGamesMap.values());
                break;
            case THREE_PLAYERS:
                games = new ArrayList<>(MainServer.threePlayersGamesMap.values());
                break;
            case FOUR_PLAYERS:
                games = new ArrayList<>(MainServer.fourPlayersGamesMap.values());
                break;
            default:
                break;
        }
        for (Game g : games) {
            if(!g.isFull()){
                //non appena ne trovo una non piena la setto ed esco da ciclo
                return g;
            }
        }
        //se arrivo qui non c'è nessuna partita libera, la devo creare e ritornarla
        Game game = new Game(gameMode);
        switch (gameMode){
            case RANDOM:
                randomGamesMap.put(counterRandom, game);
                counterRandom++;
                break;
            case TWO_PLAYERS:
                twoPlayerGamesMap.put(counter2Players, game);
                counter2Players++;
                break;
            case THREE_PLAYERS:
                threePlayersGamesMap.put(counter3Players, game);
                counter3Players++;
                break;
            case FOUR_PLAYERS:
                fourPlayersGamesMap.put(counter4Players, game);
                counter4Players++;
                break;
        }
        return game;
    }
}
