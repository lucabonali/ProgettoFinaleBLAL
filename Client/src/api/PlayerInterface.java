package api;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

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
     * Serve al client per conoscere la lista delle partite attive
     * @return lista partite
     * @throws RemoteException
     */
    List<Integer> getGamesMap() throws RemoteException;

    /**
     * mi aggiunge all'oggetto player la sua client interface
     * @param clientInterface
     * @throws RemoteException
     */
    void addClientInterface(ClientInterface clientInterface) throws RemoteException;

}
