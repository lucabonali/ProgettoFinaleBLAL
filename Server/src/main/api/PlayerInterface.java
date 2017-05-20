package main.api;


import main.api.exceptions.LorenzoException;
import main.api.exceptions.NewActionException;
import main.api.messages.MessageGame;

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
    void doAction(MessageGame msg) throws RemoteException, LorenzoException;


    /**
     * mi aggiunge all'oggetto player la sua main.clientGame interface
     * @param clientInterface
     * @throws RemoteException
     */
    void addClientInterface(ClientInterface clientInterface) throws RemoteException;

    /**
     * metodo chiamato per il lancio del dado, solo il primo giocatore può farlo
     * in caso viene lanciata una LorenzoException
     * @throws RemoteException
     */
    void shotDice(int orange, int white, int black) throws RemoteException, LorenzoException;

    /**
     * metodo chiamato per indicare la fine del turno del giocatore
     * @throws RemoteException
     */
    void endMove() throws RemoteException, NewActionException;

    /**
     * metodo che mi identifica la scelta di dare sostegno o meno alla chiesa
     * @param choice true accetto la scomunica, false do sostegno
     * @throws RemoteException
     */
    void excommunicationChoice(boolean choice) throws RemoteException;
}
