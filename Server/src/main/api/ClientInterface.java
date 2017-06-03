package main.api;

import main.api.messages.MessageAction;
import main.api.types.CardType;
import main.api.types.ResourceType;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * @author Andrea
 * @author Luca
 *
 * Interfaccia implementata nel client e invocata dal server (PLayerRMI) per notifiche e modifiche
 * dell'interfaccia grafica, i suoi metodi vengono invocati direttamente in caso di connessione RMI
 * mentre indirettamente nel caso di connessione socket.
 *
 */
public interface ClientInterface extends Remote{

    /**
     * mi notifica al client che la partita è incominciata
     * @param id mio id
     * @param opponents id e nomi dei giocatori avversari
     * @throws RemoteException
     */
    void isGameStarted(int id, Map<Integer, String> opponents, List<String> codeExcomList) throws RemoteException;

    /** metodo che mi andrà ad aggiornare tutte le mie risorse
     * // WOOD , STONE , SERVANTS , COINS , VICTORY , FAITH , MILITARY
     * @param qtaResourcesMap lista di risorse
     * @throws RemoteException in caso qualcosa vada storto
     */
    void updateResources(Map<ResourceType, Integer> qtaResourcesMap) throws RemoteException;

    /**
     * metodo che mi aggiorna le mie carte nella personal board
     * @param personalcardsMap mapp delle carte
     * @throws RemoteException
     */
    void updatePersonalCards(Map<CardType, List<String>> personalcardsMap) throws RemoteException;

    /**
     * mi notifica un messaggio di informazione
     * @param msg messaggio da notificare
     * @throws RemoteException
     */
    void notifyMessage(String msg) throws RemoteException;

    /**
     * serve per notificare al main.client il valore dei dadi tirati a inizio di turno
     * @param orange
     * @param white
     * @param black
     * @throws RemoteException
     */
    void setDiceValues(int orange, int white, int black) throws RemoteException;

    /**
     * informa il giocatore che deve tirare i dadi!
     * @throws RemoteException
     */
    void notifyHaveToShotDice() throws RemoteException;

    /**
     * metodo che mi notifica al main.client che deve fare una nuova azione del tipo
     * identificato dal codeAction e del valore di value
     * @param value valore dell'azione
     * @param codeAction codice dell'azione (spazio azione)
     * @throws RemoteException
     */
    void notifyNewAction(int value, char codeAction) throws RemoteException;

    /**
     * metodo che mi notifica l'inizio del mio turno di gioco
     * @throws RemoteException
     */
    void notifyYourTurn() throws RemoteException;

    /**
     * notifica al client che è il suo turno della fase scomunica
     * @throws RemoteException
     */
    void notifyYourExcommunicationTurn() throws RemoteException;

    /**
     * mi passa al client la lista delle 16 carte presenti sulle torri
     * @param list lista dei nomi delle carte
     * @throws RemoteException
     */
    void setTowersCards(List<String> list) throws RemoteException;

    /**
     * mi notifica che ho terminato la mia mossa
     * @throws RemoteException
     */
    void notifyEndMove() throws RemoteException;

    /**
     * mi notifica al client che un suo avversario ha mosso, e cosa ha modificato
     * @param id id del giocatore che ha mosso
     * @param personalcardsMap mappa delle carte personali del giocatore che ha mosso
     * @param qtaResourcesMap mappa delle qta delle risorse del giocatore che ha mosso.
     * @throws RemoteException
     */
    void opponentMove(int id, Map<CardType, List<String>> personalcardsMap, Map<ResourceType, Integer> qtaResourcesMap) throws RemoteException;

    /**
     * mi sposta il familiare nello spazio azione corretto
     * @param id id del giocatore che muove
     * @param msgAction messaggio codificato della mossa
     * @throws RemoteException
     */
    void opponentFamilyMemberMove(int id, MessageAction msgAction) throws RemoteException;

    /**
     * notifica l'ottenimento di un nuovo privilegio
     * @throws RemoteException
     */
    void notifyPrivilege() throws RemoteException;

    /**
     * notifica che sono stato scomunicato nel periodo passato come parametro
     * @param id id del giocatore scomunicato
     * @param period periodo
     * @throws RemoteException
     */
    void excommunicate(int id, int period) throws RemoteException;

    /**
     * notifica che la partita è terminata, con l'esito
     * @param msg messaggio (esito)
     */
    void gameEnded(String msg) throws RemoteException;

    /**
     * mi notifica l'ordine dei giocatori per il turno corrente
     * @param orderList lista degli id
     * @throws RemoteException
     */
    void notifyTurnOrder(List<Integer> orderList) throws RemoteException;

    /**
     * notifica e aggiorna l'interfaccia eleiminando ogni traccia del giocatore che ha abbandonato
     * @param surrenderId id del giocatore che ha abbandonato
     * @throws RemoteException
     */
    void notifyOpponentSurrender(int surrenderId) throws RemoteException;
}
