package game;

import api.ClientInterface;
import api.PlayerInterface;
import api.ServerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca, Andrea on 16/05/2017.
 */
public class ServerRMI extends UnicastRemoteObject implements ServerInterface{
    public ServerRMI() throws RemoteException {
    }

    public PlayerInterface login(ClientInterface clientInterface) throws RemoteException {
        PlayerInterface player = new PlayerRmi(clientInterface);
        return player;
    }

}
