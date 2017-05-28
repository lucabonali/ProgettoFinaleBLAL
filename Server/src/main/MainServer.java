package main;

import main.model.Game;
import main.game_server.rmi.ServerRMI;
import main.game_server.socket.SocketServer;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Luca
 * @author Andrea
 */
public class MainServer {
    public static final int RANDOM = 1;
    public static final int TWO_PLAYERS = 2;
    public static final int THREE_PLAYERS = 3;
    public static final int FOUR_PLAYERS = 4;
    public static int counterRandom = 0;
    public static int counter2Players = 0;
    public static int counter3Players = 0;
    public static int counter4Players = 0;
    public static Map<Integer,Game> randomGamesMap = Collections.synchronizedMap(new HashMap<>());
    public static Map<Integer,Game> twoPlayerGamesMap = Collections.synchronizedMap(new HashMap<>());
    public static Map<Integer,Game> threePlayersGamesMap = Collections.synchronizedMap(new HashMap<>());
    public static Map<Integer,Game> fourPlayersGamesMap = Collections.synchronizedMap(new HashMap<>());
    public static Map<String,String> playersMap = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(1099);
        SocketServer socketServer = new SocketServer();
        ServerRMI serverRMI = new ServerRMI();
        registry.bind("serverRMI", serverRMI);
    }
}
