package main.clientGame;

import main.api.PlayerInterface;
import main.api.ServerInterface;
import main.api.exceptions.LorenzoException;
import main.api.exceptions.NewActionException;
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


    /**
     * costruttore che prende in ingresso username e password del giocatore
     * @param username
     * @param password
     * @throws RemoteException
     */
    public ClientRMI(String username, String password) throws RemoteException {
        super(username, password);
    }

    /**
     * Metodo che notifica al server che l' utente sta compiendo un azione su uno spazio azione, sotto forma
     * di messaggio, chiamando il metodo doAction dell' intefaccia serverGame
     * (Va parametrizzato per scegliere quale azione compiere e con quale familiare)
     * @throws RemoteException
     * @throws LorenzoException
     */
    @Override
    public void doAction() throws RemoteException, LorenzoException {
        MessageGame msg = new MessageGame(MessageGameType.ACTION);
        msg.setFamilyMemberType(FamilyMemberType.ORANGE_DICE);
        msg.setActionSpacesType(ActionSpacesType.COUNCIL);
        serverGame.doAction(msg);
    }


    /**
     * metodo che esegue il login del giocatore, scaricando l' intefaccia del server per poter chiamare il metodo
     * di login su di essa (attraverso getRegistry e Lookup)
     * @return
     * @throws RemoteException
     * @throws NotBoundException
     */
    @Override
    public boolean login() throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(1099);
        server = (ServerInterface) registry.lookup(SERVER);
        return server.login(getUsername(), getPassword());
    }

    /**
     *
     * @throws RemoteException
     */
    @Override
    public void startGame(int gameMode) throws RemoteException {
        serverGame = (PlayerInterface) server.startGame(getUsername(), gameMode);
        addClientInterfaceToServer();
    }

    /**
     * metodo che aggiunge la propria intefaccia client al server, su cui il server potrà chiamare metodi di notifica e modifica
     * dell' interaccia
     * @throws RemoteException
     */
    @Override
    public void addClientInterfaceToServer() throws RemoteException {
        serverGame.addClientInterface(this);
    }

    /**
     * metyodo che viene chiamato dal client che notifica al server che la mossa è finita, chiamando
     * il metodo endMove sull' interfacci di tipo PlayerInterface (serverGame)
     * @throws RemoteException
     */
    @Override
    public void endMove() throws RemoteException, NewActionException {
            serverGame.endMove();
    }

    public void printString(String name) throws RemoteException {
        System.out.println(name);
    }

}
