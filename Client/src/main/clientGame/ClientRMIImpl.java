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




}
