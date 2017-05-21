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
     * @param username username del giocatore
     * @param password password del giocatore
     * @return true se va a buon fine
     * @throws RemoteException
     */
    boolean login(String username, String password) throws RemoteException;

    /**
     * metodo che mi esegue il logout quando ancora non ho iniziato una partita
     * @param username username del giocatore
     * @return true se va a buon fine
     * @throws RemoteException
     */
    boolean logout(String username) throws RemoteException;


    /**
     * metodo che esegue l' inizio della partita,
     * @param username username del giocatore che vuole giocare
     * @return
     * @throws RemoteException
     */
    PlayerInterface startGame(String username) throws RemoteException;

}
