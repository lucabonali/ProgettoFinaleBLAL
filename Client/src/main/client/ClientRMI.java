package main.client;

import main.api.PlayerInterface;
import main.api.ServerInterface;
import main.servergame.exceptions.LorenzoException;

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
    public void doAction() throws RemoteException {
        //creo l'azione
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
        serverGame = (PlayerInterface) server.startGame(getUsername(), gameMode, this);
    }


    /**
     * metyodo che viene chiamato dal client che notifica al server che la mossa è finita, chiamando
     * il metodo endMove sull' interfacci di tipo PlayerInterface (serverGame)
     * @throws RemoteException
     */
    @Override
    public void endMove() throws RemoteException {
        serverGame.endMove();
    }

    /**
     * metodo che invia al server i risutlati del lancio dei dadi, il quale si occuperà di inviarlo
     * a tutti gli altri giocatori
     * @param orange dado arancione
     * @param white bianco
     * @param black nero
     * @throws RemoteException
     * @throws LorenzoException
     */
    @Override
    public void shotDice(int orange, int white, int black) throws RemoteException{
        serverGame.shotDice(orange, white, black);
    }

    /**
     * metodo che rappresenta la scelta riguardante la decisione di farsi scomunicare
     * oppure dare sostegno alla chiesa
     * @param choice true accetto la scomunica, false do sostegno
     * @throws RemoteException
     */
    @Override
    public void excommunicationChoice(boolean choice) throws RemoteException {
        serverGame.excommunicationChoice(choice);
    }


    public void printString(String name) throws RemoteException {
        System.out.println(name);
    }

}
