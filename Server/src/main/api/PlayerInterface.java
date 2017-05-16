package main.api;


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Luca
 * @author Andrea
 *
 * interfaccia implementata nel server che avrà tutti i metodi comuni tra
 * i due "tipi di giocatori" e vengono invocati su richiesta del client
 */
public interface PlayerInterface extends Remote {

    /**
     * metodo che mi rappresenta l'esecuzione di un'azione da parte del
     * giocatore (che l'ha eseguita)
     * @param msg messaggio che mi codifica l'azione
     * @throws RemoteException problemi con rmi
     */
    void doAction(MessageGame msg) throws RemoteException, LorenzoException;


    /**
     * mi aggiunge all'oggetto player la sua client interface
     * @param clientInterface
     * @throws RemoteException
     */
    void addClientInterface(ClientInterface clientInterface) throws RemoteException;

    /**
     * metodo chiamato per il lancio del dado, solo il primo giocatore può farlo
     * in caso viene lanciata una LorenzoException
     * @throws RemoteException
     */
    void shotDice() throws RemoteException, LorenzoException;

}
