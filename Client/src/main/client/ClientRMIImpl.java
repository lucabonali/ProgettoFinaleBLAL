package main.client;

import main.api.ClientInterface;
import main.api.types.ResourceType;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

/**
 * @author Andrea
 * @author Luca
 */
public class ClientRMIImpl extends UnicastRemoteObject implements ClientInterface, Serializable {
    private String username, password;

    public ClientRMIImpl(String username, String password) throws RemoteException {
        this.username = username;
        this.password = password;
    }

    public void printString(String name) throws RemoteException {
        System.out.println(name);
    }

    //@Override
    //public void updateResources(List<Integer> qtaResourcesList) throws RemoteException {

    //}




    @Override
    public void updateResources(Map<ResourceType, Integer> qtaResourcesMap) throws RemoteException {

    }

    @Override
    public void notifyMessage(String msg) throws RemoteException {

    }


    @Override
    public void setDiceValues(int orange, int white, int black) throws RemoteException {

    }

    @Override
    public void notifyNewAction(int value, char codeAction) throws RemoteException {

    }
}
