package main.api;

import main.api.types.ResourceType;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * Interfaccia implementata nel main.clientGame e invocata dal server (PLayerRMI) per modificare il main.clientGame (Serve solo per rmi)
 * Created by Luca, Andrea on 15/05/2017.
 */
public interface ClientInterface extends Remote{

    /** metodo che mi andrà ad aggiornare tutte le mie risorse
     * // WOOD , STONE , SERVANTS , COINS , VICTORY , FAITH , MILITARY
     * @param qtaResourcesMap lista di risorse
     * @throws RemoteException in caso qualcosa vada storto
     */
    void updateResources(Map<ResourceType,Integer> qtaResourcesMap) throws RemoteException;

    /**
     * mi notifica un messaggio di informazione
     * @param msg messaggio da notificare
     * @throws RemoteException
     */
    void notifyMessage(String msg) throws RemoteException;

    /**
     * serve per notificare al main.clientGame il valore dei dadi tirati a inizio di turno
     * @param orange
     * @param white
     * @param black
     * @throws RemoteException
     */
    void setDiceValues(int orange, int white , int black) throws RemoteException;

    /**
     * metodo che mi notifica al main.clientGame che deve fare una nuova azione del tipo
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
}
