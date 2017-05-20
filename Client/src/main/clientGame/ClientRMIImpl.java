package main.clientGame;

import main.api.ClientInterface;
import main.api.types.ResourceType;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

/**
 * Classe che sarà condivisa i cui metodi saranno chiamati dal server per notificare e modificare l' interfaccia utente
 * @author Andrea
 * @author Luca
 */
public class ClientRMIImpl extends AbstractClient implements  Serializable {
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

    /**
     * metodo che serve per notificare al server di lanciare i dadi
     * @param orange
     * @param white
     * @param black
     * @throws RemoteException
     */
    @Override
    public void setDiceValues(int orange, int white, int black) throws RemoteException {

    }

    @Override
    public void notifyNewAction(int value, char codeAction) throws RemoteException {

    }
}
