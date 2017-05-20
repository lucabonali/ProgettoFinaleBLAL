package main.clientGame;

import main.api.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Luca on 20/05/2017.
 */
public abstract class AbstractClient extends UnicastRemoteObject implements ClientInterface {
    private String username,password;

    protected AbstractClient() throws RemoteException {
    }


    public AbstractClient(String username, String password) throws RemoteException {
        this.username = username;
        this.password = password;
    }
}
