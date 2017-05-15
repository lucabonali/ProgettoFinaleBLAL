package api;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interfaccia che saraà implementata dell' oggetto scaricabile dal client RMI
 * Created by Luca, Andrea on 15/05/2017.
 */
public interface ServerPlayerInterface extends Remote {

    PlayerInterface playerRMIRequest(int idGame) throws RemoteException;

    List<Integer> getGamesMap() throws RemoteException;
}
