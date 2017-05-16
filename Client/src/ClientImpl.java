import api.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * @author lampa
 */
public class ClientImpl extends UnicastRemoteObject implements ClientInterface {

    public ClientImpl() throws RemoteException {

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
}
