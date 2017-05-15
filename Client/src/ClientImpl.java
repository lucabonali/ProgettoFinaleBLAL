import api.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author lampa
 */
public class ClientImpl extends UnicastRemoteObject implements ClientInterface {

    public ClientImpl() throws RemoteException {

    }

    public void printString(String name) throws RemoteException {
        System.out.println(name);
    }
}
