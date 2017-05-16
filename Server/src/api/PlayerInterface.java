package api;


import board.FamilyMember;

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
    void doAction(Message msg) throws RemoteException, LorenzoException;

    /**
     * mi crea la plancia personale
     * @param id id del giocatore
     */
    void createPersonalBoard(int id);

    /**
     * Iscrive un giocatore alla partita di id = idGame
     * @param idGame id della partita
     * @return true se partita non piene, false else
     * @throws RemoteException
     */
    boolean setGame(int idGame) throws RemoteException;


    /**
     * Serve al client per conoscere la lista delle partite attive
     * @return lista partite
     * @throws RemoteException
     */
    List<Integer> getGamesMap() throws RemoteException;

    /**
     * Notifica in due modi diversi ai client che la partita è iniziata
     * @throws RemoteException
     */
    void gameIsStarted() throws RemoteException;

    /**
     * notifica al giocatore che è il suo turno
     * @throws RemoteException
     */
    void isYourTurn() throws RemoteException;


    void setDiceValues(int orange, int white, int black);

    FamilyMember getFamilyMember(FamilyMemberType type);
}
