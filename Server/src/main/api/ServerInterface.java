package main.api;


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia che saraà implementata dell' oggetto scaricabile dal clientGame RMI
 * gli username sono univoci
 * Created by Luca, Andrea on 15/05/2017.
 */
public interface ServerInterface extends Remote {

    /**
     * metodo che esegue il login di un giocatore
     * @param username
     * @param password
     * @return
     * @throws RemoteException
     */
    boolean login(String username, String password) throws RemoteException;


    /**
     * metodo richiamato dal clientGame una volta fatta la lookup del regisrty, che serve per aggiungere un ' istanza di
     * giocatore RMI nel server, per poter chiamare metodi sul clientGame da parte del server
     * @param clientInterface
     * @throws RemoteException
     */
    void addPlayerRMI(ClientInterface clientInterface) throws RemoteException;


    /**
     * metodo che esegue l' inizio della partita,
     * @param username
     * @return
     * @throws RemoteException
     */
    PlayerInterface startGame(String username) throws RemoteException;

}
