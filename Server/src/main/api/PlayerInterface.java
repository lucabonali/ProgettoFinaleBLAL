package main.api;


import main.api.messages.MessageAction;
import main.api.messages.MessageNewAction;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Luca
 * @author Andrea
 *
 * interfaccia implementata nel server che avrà tutti i metodi comuni tra
 * i due "tipi di giocatori" e vengono invocati su richiesta del main.clientGame
 */
public interface PlayerInterface extends Remote {

    /**
     * metodo che mi rappresenta l'esecuzione di un'azione da parte del
     * giocatore (che l'ha eseguita)
     * @param msg messaggio che mi codifica l'azione
     * @throws RemoteException problemi con rmi
     */
    void doAction(MessageAction msg) throws RemoteException;

    /**
     * metodo che mi rappresenta l'esecuzione di una mossa supplementare
     * che posso fare solo se il server me lo concede
     * @param msg messaggio che codifica la nuova azione
     * @throws RemoteException problemi con rmi
     */
    void doNewAction(MessageNewAction msg) throws RemoteException;


    /**
     * mi aggiunge all'oggetto player la sua main.clientGame interface
     * @param clientInterface l'interfacccia client del giocatore
     * @throws RemoteException problemi con rmi
     */
    void addClientInterface(ClientInterface clientInterface) throws RemoteException;

    /**
     * metodo chiamato per il lancio del dado, solo il primo giocatore può farlo
     * in caso viene lanciata una LorenzoException
     * @throws RemoteException
     */
    void shotDice(int orange, int white, int black) throws RemoteException;

    /**
     * metodo chiamato per indicare la fine del turno del giocatore
     * @throws RemoteException
     */
    void endMove() throws RemoteException;

    /**
     * metodo che mi identifica la scelta di dare sostegno o meno alla chiesa
     * @param choice true accetto la scomunica, false do sostegno
     * @throws RemoteException
     */
    void excommunicationChoice(boolean choice) throws RemoteException;

    /**
     * mi abbandona la partita in corso.
     * @throws RemoteException
     */
    void abandon() throws RemoteException;
}
