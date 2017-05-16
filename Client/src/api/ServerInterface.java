package api;


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia che saraà implementata dell' oggetto scaricabile dal client RMI
 * gli username sono univoci
 * Created by Luca, Andrea on 15/05/2017.
 */
public interface ServerInterface extends Remote {

    boolean login(String username, String password) throws RemoteException;

    PlayerInterface startGame(String username) throws RemoteException;

}
