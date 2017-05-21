package main.clientGame;

import main.api.PlayerInterface;
import main.api.ServerInterface;
import main.api.exceptions.LorenzoException;
import main.api.messages.MessageGame;
import main.api.messages.MessageGameType;
import main.api.types.ActionSpacesType;
import main.api.types.FamilyMemberType;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Classe che sarà condivisa i cui metodi saranno chiamati dal server per notificare e modificare l' interfaccia utente
 * @author Andrea
 * @author Luca
 */
public class ClientRMI extends AbstractClient {
    private static ClientRMI instanceRMI;
    private static final String SERVER = "serverRMI";
    private ServerInterface server;
    private PlayerInterface serverGame;
    private Registry registry;

    public ClientRMI(String username, String password) throws RemoteException {
        super(username, password);
    }

    @Override
    public void doAction() throws RemoteException, LorenzoException {
        MessageGame msg = new MessageGame(MessageGameType.ACTION);
        msg.setFamilyMemberType(FamilyMemberType.ORANGE_DICE);
        msg.setActionSpacesType(ActionSpacesType.COUNCIL);
        serverGame.doAction(msg);
    }

    @Override
    public boolean login() throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(1099);
        server = (ServerInterface) registry.lookup(SERVER);
        return server.login(getUsername(), getPassword());
    }

    @Override
    public void startGame() throws RemoteException {
        serverGame = (PlayerInterface) server.startGame(getUsername());
        addClientInterfaceToServer();
    }

    @Override
    public void addClientInterfaceToServer() throws RemoteException {
        serverGame.addClientInterface(this);
    }

    public void printString(String name) throws RemoteException {
        System.out.println(name);
    }
}
