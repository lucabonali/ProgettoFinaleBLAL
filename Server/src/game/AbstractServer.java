package game;

import api.ServerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author lampa
 */
public abstract class AbstractServer extends UnicastRemoteObject implements ServerInterface{

    protected AbstractServer() throws RemoteException {

    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        if (MainServer.playersMap.containsKey(username)) {
            if (MainServer.playersMap.get(username).equals(password))
                return true;
            return false;
        }
        else {
            MainServer.playersMap.put(username, password);
            return true;
        }
    }
}
