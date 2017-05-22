package main.clientGame;

import main.api.ClientInterface;
import main.api.exceptions.LorenzoException;
import main.api.exceptions.NewActionException;
import main.api.types.ResourceType;
import main.gui.GameController;
import main.gui.GameSelectionController;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

/**
 * Classe che rappresenta il client generico, che avrà metodi in comune tra entrambi i tipi
 * di connessione e metodi che verranno implementati in modo diverso nelle sottoclassi
 * ma con gli stessi risultati( alcuni di questi metodi saranno richiamati dal server attraverso
 * il passaggio di un istanza di playerRMI)
 * @author Andrea
 * @author Luca
 */
public abstract class AbstractClient extends UnicastRemoteObject implements ClientInterface {
    private static boolean logged = false;
    private static AbstractClient instance;
    private String username,password;
    private int id;
    private GameSelectionController gameSelectionController;
    private GameController gameController;

    protected AbstractClient() throws RemoteException {
    }


    public AbstractClient(String username, String password) throws RemoteException {
        this.username = username;
        this.password = password;
    }

    /// METODI EREDITATI DALL'INTERFACCIA CLIENT INTERFACE ////////////////

    /**
     * metodo che aggiorna le mie risorse
     * @param qtaResourcesMap lista di risorse
     * @throws RemoteException
     */
    @Override
    public void updateResources(Map<ResourceType, Integer> qtaResourcesMap) throws RemoteException {

    }

    /**
     * metodo che mi notifica al client un messaggio provenitente dal server
     * @param msg messaggio da notificare
     * @throws RemoteException
     */
    @Override
    public void notifyMessage(String msg) throws RemoteException {
        gameController.showMessage(msg);
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
        //forse si può togliere perché è il client a lanciare i dadi, quindi sa già i valori
    }

    /**
     * notifica al client che deve fare un'altra azione
     * @param value valore dell'azione
     * @param codeAction codice dell'azione (spazio azione)
     * @throws RemoteException
     */
    @Override
    public void notifyNewAction(int value, char codeAction) throws RemoteException {

    }

    /**
     * notifica al client che è il suo turno nella fase azione
     * @throws RemoteException
     */
    @Override
    public void notifyYourTurn() throws RemoteException {

    }

    /**
     * notifica al client che è il suo turno nella fase scomunica
     * @throws RemoteException
     */
    @Override
    public void notifyYourExcommunicationTurn() throws RemoteException {

    }


    /// METODI AGGIUNTI DALLA CLASSE ASTRATTA E GIA' IMPLEMENTATI


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLogged(){
        logged = true;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setGameController(GameController controller) {
        this.gameController = controller;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setGameSelectionController(GameSelectionController gsController) {
        this.gameSelectionController = gsController;
    }


    /// METODI AGGIUNTI DA QUESTA CLASSE CHE VERRANNO IMPLEMENTATI DALLE SOTTO-CLASSI


    /**
     * metodo che invia al server un'azione, verrà implementato nella maniera
     * corretta dalle due sottoclassi.
     */
    public abstract void doAction() throws RemoteException, LorenzoException;

    /**
     * metodo che mi tenta il login al server
     */
    public abstract boolean login() throws RemoteException, NotBoundException;

    /**
     * metodo che manda un messaggio al server dicendo che voglio giocare.
     * @throws RemoteException
     */
    public abstract void startGame(int gameMode) throws RemoteException;

    /**
     * mi aggiunge l'interfaccia remota al server
     * @throws RemoteException
     */
    public abstract void addClientInterfaceToServer() throws RemoteException;


    /**
     * metyodo che viene chiamato dal client che notifica al server che la mossa è finita
     * @throws RemoteException
     */
    public abstract void endMove() throws RemoteException, NewActionException;


    /**
     * metodo utilizzato per ritornarmi ogni volta l'istanza di giocatore corretta
     * @return AbstractClient
     */
    public static AbstractClient getInstance() {
        return instance;
    }

    /**
     * metodo di factory che mi genera un'istanza del giocatore corretto in base alla scelta di connessione
     * @param connection true = RMI, false = Socket.
     * @param username username
     * @param password password
     * @return l'istanza di giocatore corretta
     */
    public static AbstractClient createInstance(boolean connection, String username, String password) throws RemoteException {
        if (!logged){
            if (connection)
                instance = new ClientRMI(username, password);
            else
                instance = new ClientSocket(username, password);
        }
        return instance;
    }



}
