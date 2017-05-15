package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia implementata nel client e invocata dal server (PLayerRMI) per modificare il client (Serve solo per rmi)
 * Created by Luca, Andrea on 15/05/2017.
 */
public interface ClientInterface extends Remote {
    void printString(String name) throws RemoteException;
}
