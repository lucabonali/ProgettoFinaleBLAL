package game;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lampa
 */
public class MainServer {
    public static Map<Integer,Game> gameMap = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(1099);
        SocketServer socketServer = new SocketServer();
        ServerRMI serverRMI = new ServerRMI();
        registry.bind("serverRMI", serverRMI);
    }
}
