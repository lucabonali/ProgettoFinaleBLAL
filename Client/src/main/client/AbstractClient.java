package main.client;

import main.api.ClientInterface;
import main.api.messages.MessageAction;
import main.api.types.CardType;
import main.api.types.Phases;
import main.api.types.ResourceType;
import main.gui.game_view.GameController;
import main.gui.game_view.MessagesController;
import main.gui.game_view.PersonalBoardController;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
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
    private List<Integer> opponentsIdList;
    private GameController gameController;
    private MessagesController messagesController;
    private PersonalBoardController personalBoardController;
    private Phases phase;

    protected AbstractClient() throws RemoteException {
    }


    public AbstractClient(String username, String password) throws RemoteException {
        this.username = username;
        this.password = password;
    }

    /// METODI EREDITATI DALL'INTERFACCIA CLIENT INTERFACE ////////////////

    /**
     * mi salva il mio id e l'id dei giocatori
     * @param id mio id
     * @param opponentsId l'id dei giocatori
     */
    @Override
    public void isGameStarted(int id, List<Integer> opponentsId) {
        this.id = id;
        opponentsIdList = opponentsId;
        gameController.createDiscs(id);
        personalBoardController.startGame(id);
        messagesController.setMessage("La partita è iniziata");
    }

    /**
     * metodo che mi setta sul tabellone tutte le carte ricevute dal server
     * @param list lista dei nomi delle carte
     * @throws RemoteException
     */
    public void setTowersCards(List<String> list) throws RemoteException {
        gameController.setBoardCards(list);
    }

    /**
     * metodo che aggiorna le mie risorse
     * @param qtaResourcesMap lista di risorse
     * @throws RemoteException
     */
    @Override
    public void updateResources(Map<ResourceType, Integer> qtaResourcesMap) throws RemoteException {
        Map<ResourceType, Integer> resourceMap = new HashMap<>();
        Map<ResourceType, Integer> pointMap = new HashMap<>();
        qtaResourcesMap.forEach(((resourceType, integer) -> {
            if (resourceType == ResourceType.COINS || resourceType == ResourceType.WOOD || resourceType == ResourceType.STONE || resourceType == ResourceType.SERVANTS)
                resourceMap.put(resourceType, integer);
            else
                pointMap.put(resourceType,integer);
        }));
        gameController.modifyPoints(pointMap);
        personalBoardController.modifyResources(resourceMap);
    }

    /**
     * metodo che mi aggiorna le mie carte nella personal board
     * @param personalcardsMap mapp delle carte
     * @throws RemoteException
     */
    @Override
    public void updatePersonalCards(Map<CardType, List<String>> personalcardsMap) throws RemoteException {
        gameController.removeDrawnCards(personalcardsMap);
        personalBoardController.updateCards(personalcardsMap);
    };

    /**
     * metodo che mi notifica al client un messaggio provenitente dal server
     * @param msg messaggio da notificare
     * @throws RemoteException
     */
    @Override
    public void notifyMessage(String msg) throws RemoteException {
        messagesController.setMessage(msg);
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
        messagesController.setMessage("il primo giocatore ha tirato i dadi");
        gameController.setDices(orange, white, black);
    }

    /**
     * mi informa il giocatore che deve tirare i dadi
     * @throws RemoteException
     */
    @Override
    public void notifyHaveToShotDice() throws RemoteException {
        messagesController.setMessage("devi tirare i dadi!!!");
        gameController.showDices();
    }

    /**
     * notifica al client che deve fare un'altra azione
     * @param value valore dell'azione
     * @param codeAction codice dell'azione (spazio azione)
     * @throws RemoteException
     */
    @Override
    public void notifyNewAction(int value, char codeAction) throws RemoteException {
        messagesController.setMessage("devi fare una nuova azione");
        phase = Phases.NEW_ACTION;
    }

    /**
     * notifica al client che è il suo turno nella fase azione
     * @throws RemoteException
     */
    @Override
    public void notifyYourTurn() throws RemoteException {
        messagesController.setMessage("è il tuo turno!!!");
        phase = Phases.ACTION;
    }

    /**
     * notifica al client che è il suo turno nella fase scomunica
     * @throws RemoteException
     */
    @Override
    public void notifyYourExcommunicationTurn() throws RemoteException {
        messagesController.setMessage("devi fare una scelta di scomunica");
        phase = Phases.EXCOMMUNICATION;
    }

    /**
     * mi notifica che ho terminato il turno
     * @throws RemoteException
     */
    public void notifyEndMove() throws RemoteException{
        messagesController.setMessage("hai terminato il tuo turno attendi il tuo avversario");
    }


    /// METODI AGGIUNTI DALLA CLASSE ASTRATTA E GIA' IMPLEMENTATI /////////////////////////////////////////////////////////


    public int getId() {
        return id;
    }

    public void setLogged(){
        logged = true;
    }

    public void setGameController(GameController controller) {
        this.gameController = controller;
    }

    public void setMessagesController(MessagesController controller) {
        this.messagesController = controller;
    }

    public void setPersonalBoardController(PersonalBoardController controller) {
        this.personalBoardController = controller;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }


    /// METODI AGGIUNTI DA QUESTA CLASSE CHE VERRANNO IMPLEMENTATI DALLE SOTTO-CLASSI  ///////////////////////////////////////


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
     * metodo che invia al server un'azione, verrà implementato nella maniera
     * corretta dalle due sottoclassi.
     */
    public abstract void doAction(MessageAction msg) throws RemoteException;

    /**
     * il giocatore lancia i dadi, e invia i risultati al server!!
     * @param orange dado arancione
     * @param white bianco
     * @param black nero
     * @throws RemoteException
     */
    public abstract void shotDice(int orange, int white, int black) throws RemoteException;

    /**
     * metodo che mi identifica la scelta di dare sostegno o meno alla chiesa
     * @param choice true accetto la scomunica, false do sostegno
     * @throws RemoteException
     */
    public abstract void excommunicationChoice(boolean choice) throws RemoteException;


    /**
     * metyodo che viene chiamato dal client che notifica al server che la mossa è finita
     * @throws RemoteException
     */
    public abstract void endMove() throws RemoteException;


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
