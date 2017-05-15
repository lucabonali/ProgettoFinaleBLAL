package api;


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
    void doAction(Message msg) throws RemoteException, LorenzoException;

    /**
     * mi crea la plancia personale
     * @param id id del giocatore
     */
    void createPersonalBoard(int id);
}
