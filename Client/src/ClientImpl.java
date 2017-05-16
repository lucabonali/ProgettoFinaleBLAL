import main.api.ClientInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * @author lampa
 */
public class ClientImpl extends UnicastRemoteObject implements ClientInterface, Serializable {
    private String username, password;

    public ClientImpl(String username, String password) throws RemoteException {
        this.username = username;
        this.password = password;
    }

    public void printString(String name) throws RemoteException {
        System.out.println(name);
    }

    @Override
    public void updateResources(List<Integer> qtaResourcesList) throws RemoteException {

    }

    @Override
    public void notifyMessage(String msg) throws RemoteException {

    }


    @Override
    public void setDiceValues(int orange, int white, int black) throws RemoteException {

    }
}
