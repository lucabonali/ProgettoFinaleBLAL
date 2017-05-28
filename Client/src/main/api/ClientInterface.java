package main.api;

import main.api.types.CardType;
import main.api.types.ResourceType;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * Interfaccia implementata nel main.client e invocata dal server (PLayerRMI) per modificare il main.client (Serve solo per rmi)
 * Created by Luca, Andrea on 15/05/2017.
 */
public interface ClientInterface extends Remote{

    /**
     * mi notifica al client che la partita è incominciata
     * @param id mio id
     * @param opponentsId l'id dei giocatori
     * @throws RemoteException
     */
    void isGameStarted(int id, List<Integer> opponentsId) throws IOException, UnsupportedAudioFileException, LineUnavailableException;

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
}
