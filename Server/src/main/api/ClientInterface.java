package main.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interfaccia implementata nel main.client e invocata dal server (PLayerRMI) per modificare il main.client (Serve solo per rmi)
 * Created by Luca, Andrea on 15/05/2017.
 */
public interface ClientInterface extends Remote{

    /** metodo che mi andrà ad aggiornare tutte le mie risorse
     * // WOOD , STONE , SERVANTS , COINS , VICTORY , FAITH , MILITARY
     * @param qtaResourcesList lista di risorse
     * @throws RemoteException in caso qualcosa vada storto
     */
    void updateResources(List<Integer> qtaResourcesList) throws RemoteException;

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
    void setDiceValues(int orange, int white , int black) throws RemoteException;

    /**
     * metodo che mi notifica al main.client che deve fare una nuova azione del tipo
     * identificato dal codeAction e del valore di value
     * @param value valore dell'azione
     * @param codeAction codice dell'azione (spazio azione)
     * @throws RemoteException
     */
    void notifyNewAction(int value, char codeAction) throws RemoteException;
}
