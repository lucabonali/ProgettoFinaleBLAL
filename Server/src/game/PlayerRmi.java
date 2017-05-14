package game;

import board.PersonalBoard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


/**
 * @author Luca
 * @author Andrea
 *
 * Classe che identifica il giocatore connesso tramite RMI
 */
public class PlayerRmi extends UnicastRemoteObject implements Player {
    private PersonalBoard personalBoard;

    protected PlayerRmi() throws RemoteException {

    }



}
