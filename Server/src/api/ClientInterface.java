package api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interfaccia implementata nel client e invocata dal server (PLayerRMI) per modificare il client (Serve solo per rmi)
 * Created by Luca, Andrea on 15/05/2017.
 */
public interface ClientInterface extends Remote {

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
}
