package main.client;

import main.CLI.InterfaceController;
import main.api.ClientInterface;
import main.api.messages.MessageAction;
import main.api.messages.MessageNewAction;
import main.api.types.*;

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
    private InterfaceController interfaceController; //controller che potrà essere GUIController se della GUI, oppure CLIController se per la cli
    private Phases phase;
    private ActionSpacesType actionSpacesType;
    private CardType cardType;
    private int numFloor;
    private FamilyMemberType familyMemberType;
    private MarketActionType marketActionType;
    private int currentNewActionValue;
    private ActionSpacesType currentNewActionActionSpaceType;
    private CardType currentNewActionCardType;



    private Map<ResourceType, Integer> qtaResourcesMap = new HashMap<>();



    private Map<Integer, Map<ResourceType, Integer>> opponentQtaResourcesMap = new HashMap<>();

    private Map<CardType, List<String>> myCardsList = new HashMap<>();

    private Map<Integer, Map<CardType, List<String>>> opponentsCardsMap = new HashMap<>();


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
    public void isGameStarted(int id, List<Integer> opponentsId, List<String> codeExcomList) throws RemoteException{
        this.id = id;
        opponentsIdList = opponentsId;
        interfaceController.startGame(id);
        interfaceController.showExcomCards(codeExcomList);
        initializeQtaResorcesMap();
        opponentsId.forEach((idValue -> {
            interfaceController.createOpponentDiscs(idValue);
            opponentQtaResourcesMap.put(idValue, new HashMap<>());
            opponentsCardsMap.put(idValue, new HashMap<>());
        }));
    }

    private void initializeQtaResorcesMap() {
        qtaResourcesMap.put(ResourceType.COINS, 4);
        qtaResourcesMap.put(ResourceType.WOOD, 2);
        qtaResourcesMap.put(ResourceType.STONE, 2);
        qtaResourcesMap.put(ResourceType.SERVANTS, 3);
        qtaResourcesMap.put(ResourceType.MILITARY, 0);
        qtaResourcesMap.put(ResourceType.FAITH, 0);
        qtaResourcesMap.put(ResourceType.VICTORY, 0);
    }

    /**
     * metodo che mi setta sul tabellone tutte le carte ricevute dal server
     * @param list lista dei nomi delle carte
     * @throws RemoteException
     */
    public void setTowersCards(List<String> list) throws RemoteException {
        interfaceController.setBoardCards(list);
        interfaceController.relocateFamilyMembers();
    }

    /**
     * metodo che aggiorna le mie risorse
     * @param qtaResourcesMap lista di risorse
     * @throws RemoteException
     */
    @Override
    public void updateResources(Map<ResourceType, Integer> qtaResourcesMap) throws RemoteException {
        this.qtaResourcesMap = qtaResourcesMap;
        interfaceController.modifyResources(qtaResourcesMap);
    }

    /**
     * metodo che mi aggiorna le mie carte nella personal board
     * @param personalcardsMap mapp delle carte
     * @throws RemoteException
     */
    @Override
    public void updatePersonalCards(Map<CardType, List<String>> personalcardsMap) throws RemoteException {
        myCardsList = personalcardsMap;
        interfaceController.removeDrawnCards(personalcardsMap);
        interfaceController.updateMyCards(personalcardsMap);
        interfaceController.moveFamilyMember(actionSpacesType, cardType, numFloor, marketActionType, familyMemberType);
    };

    /**
     * notifica al client che un suo avversatio ha mosso qualcosa, e cosa..
     * @param id id del giocatore che ha mosso
     * @param personalcardsMap mappa delle carte personali del giocatore che ha mosso
     * @param qtaResourcesMap mappa delle qta delle risorse del giocatore che ha mosso
     * @throws RemoteException
     */
    @Override
    public void opponentMove(int id, Map<CardType, List<String>> personalcardsMap, Map<ResourceType, Integer> qtaResourcesMap) throws RemoteException {
        interfaceController.removeDrawnCards(personalcardsMap); //rimuovo le carte che ha pescato
//        opponentQtaResourcesMap.get(id) = qtaResourcesMap;
//        opponentsCardsMap.get(id) = personalcardsMap;
        Map<ResourceType, Integer> pointMap = new HashMap<>();
        qtaResourcesMap.forEach(((resourceType, integer) -> {
            if (resourceType == ResourceType.VICTORY || resourceType == ResourceType.MILITARY || resourceType == ResourceType.FAITH)
                pointMap.put(resourceType,integer);
        }));
        interfaceController.modifyOpponentPoints(pointMap, id);
    }

    /**
     * metodo che mi notifica al client un messaggio provenitente dal server
     * @param msg messaggio da notificare
     * @throws RemoteException
     */
    @Override
    public void notifyMessage(String msg) throws RemoteException {
        interfaceController.notifyMessage(msg);
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
        interfaceController.notifyMessage("il primo giocatore ha tirato i dadi");
        interfaceController.setDices(orange, white, black);
    }

    /**
     * mi informa il giocatore che deve tirare i dadi
     * @throws RemoteException
     */
    @Override
    public void notifyHaveToShotDice() throws RemoteException {
        interfaceController.notifyMessage("devi tirare i dadi!!!");
        interfaceController.showDices();
    }

    /**
     * notifica l'ottenimento di un nuovo privielgio
     * @throws RemoteException
     */
    public void notifyPrivilege() throws RemoteException {
        interfaceController.showPrivilegeAlert();
    }

    /**
     * notifica al client che deve fare un'altra azione
     * @param value valore dell'azione
     * @param codeAction codice dell'azione (spazio azione)
     * @throws RemoteException
     */
    @Override
    public void notifyNewAction(int value, char codeAction) throws RemoteException {
        interfaceController.notifyMessage("devi fare una nuova azione");
        currentNewActionValue = value;
        currentNewActionActionSpaceType = main.GUI.Service.getActionSpaceType(codeAction);
        currentNewActionCardType = main.GUI.Service.getCardType(codeAction);
        phase = Phases.NEW_ACTION;
    }

    /**
     * notifica al client che è il suo turno nella fase azione
     * @throws RemoteException
     */
    @Override
    public void notifyYourTurn() throws RemoteException {
        interfaceController.notifyMessage("è il tuo turno!!!");
        phase = Phases.ACTION;
    }

    /**
     * notifica al client che è il suo turno nella fase scomunica
     * @throws RemoteException
     */
    @Override
    public void notifyYourExcommunicationTurn() throws RemoteException {
        interfaceController.showExcommunicatingAlert();
        phase = Phases.EXCOMMUNICATION;
    }

    /**
     * mi notifica che ho terminato il turno
     * @throws RemoteException
     */
    @Override
    public void notifyEndMove() throws RemoteException{
        interfaceController.notifyMessage("hai terminato il tuo turno attendi il tuo avversario");
    }

    /**
     * notifica che sono stato scomunicato nel periodo passato come parametro
     * @param period periodo
     * @throws RemoteException
     */
    @Override
    public void excommunicate(int id, int period) throws RemoteException{
        interfaceController.excommunicate(id, period);
    };

    /**
     * notifica che la partita è terminata, con l'esito
     * @param msg messaggio (esito)
     */
    @Override
    public void gameEnded(String msg) throws RemoteException {
        notifyMessage(msg);
        interfaceController.backToMenu();

    };

    public Map<ResourceType, Integer> getQtaResourcesMap() {
        return qtaResourcesMap;
    }
    public Map<CardType, List<String>> getMyCardsList() {
        return myCardsList;
    }

    public Map<ResourceType, Integer> getOpponentQtaResourcesMap(int id) {
        return opponentQtaResourcesMap.get(id);
    }

    public Map<Integer, Map<CardType, List<String>>> getOpponentsCardsMap() {
        return opponentsCardsMap;
    }

    public List<Integer> getOpponentsIdList() {
        return opponentsIdList;
    }

    /// METODI AGGIUNTI DALLA CLASSE ASTRATTA E GIA' IMPLEMENTATI /////////////////////////////////////////////////////////

    public void setActionSpacesType(ActionSpacesType actionSpacesType) {
        this.actionSpacesType = actionSpacesType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public void setNumFloor(int numFloor) {
        this.numFloor = numFloor;
    }

    public void setMarketActionType(MarketActionType marketActionType) {
        this.marketActionType = marketActionType;
    }

    public void setFamilyMemberType(FamilyMemberType familyMemberType) {
        this.familyMemberType = familyMemberType;
    }

    public int getId() {
        return id;
    }

    public void setLogged(){
        logged = true;
    }

    public void setInterfaceController(InterfaceController controller) {
        this.interfaceController = controller;
    }

    public int getQtaResource(ResourceType type) {
        return qtaResourcesMap.get(type);
    }

    /**
     * metodo che mi codifica il messaggio azione
     * @return ritorna il MessageAction corretto da inviare al server
     */
    public MessageAction encondingMessageAction() {
        if (actionSpacesType == null)
            interfaceController.notifyMessage("non hai selezionato lo spazio azione");
        else if (familyMemberType == null)
            interfaceController.notifyMessage("non hai selezionato il familiare");
        else
            return new MessageAction(actionSpacesType, cardType, numFloor, marketActionType, familyMemberType);
        return null;
    }

    /**
     * metodo che mi codifica il messaggio azione
     * @return ritorna il MessageAction corretto da inviare al server
     */
    public MessageNewAction encondingMessageNewAction() {
        if (currentNewActionActionSpaceType == ActionSpacesType.SINGLE_HARVEST){
            if (actionSpacesType == ActionSpacesType.SINGLE_HARVEST || actionSpacesType == ActionSpacesType.LARGE_HARVEST)
                return new MessageNewAction(actionSpacesType, cardType, numFloor, marketActionType, currentNewActionValue);
        }
        else if (currentNewActionActionSpaceType == ActionSpacesType.SINGLE_PRODUCTION){
            if (actionSpacesType == ActionSpacesType.SINGLE_PRODUCTION || actionSpacesType == ActionSpacesType.SINGLE_PRODUCTION)
                return new MessageNewAction(actionSpacesType, cardType, numFloor, marketActionType, currentNewActionValue);
        }
        else if (actionSpacesType != currentNewActionActionSpaceType)
            interfaceController.notifyMessage("non hai selezionato lo spazio azione corretto");
        else if (currentNewActionCardType == null) //va bene qualsiasi torre
            return new MessageNewAction(actionSpacesType, cardType, numFloor, marketActionType, currentNewActionValue);
        else if (cardType != currentNewActionCardType)
            interfaceController.notifyMessage("non hai selezionato la torre corretta");
        return new MessageNewAction(actionSpacesType, cardType, numFloor, marketActionType, currentNewActionValue);
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
     * @param msg messaggio già codificato
     * @param servantsToPay  servitori che voglio pagare
     * @throws RemoteException
     */
    public abstract void doAction(MessageAction msg, int servantsToPay) throws RemoteException;

    /**
     * metodo che invia un messa di nuova azione al server
     * @param msg messaggio già codificato
     * @param servantsToPay servitori che voglio pagare
     * @throws RemoteException
     */
    public abstract void doNewAction(MessageNewAction msg, int servantsToPay) throws RemoteException;

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
     * manda un messaggio al server dicendo in cosa voglio convertire il mio privilegio
     * @param qta qta in cui convertire
     * @param type tipo in cui convertire
     * @throws RemoteException
     */
    public abstract void convertPrivilege(int qta, ResourceType type) throws RemoteException;

    /**
     * mi dice al server che voglio abbandonare la partita
     * @throws RemoteException
     */
    public abstract void surrender() throws RemoteException;


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
