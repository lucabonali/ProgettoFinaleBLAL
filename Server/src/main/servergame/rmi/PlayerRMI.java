package main.servergame.rmi;

import main.model.board.DevelopmentCard;
import main.servergame.AbstractPlayer;

import java.rmi.RemoteException;
import java.util.List;


/**
 * @author Luca
 * @author Andrea
 *
 * Classe che identifica il giocatore connesso tramite RMI
 */
public class PlayerRMI extends AbstractPlayer {

    public PlayerRMI(String username) throws RemoteException {
        super(username);

    }

    // override di metodi ereditati da ABSTRACT PLAYER /////////////////////////////////////////////////

    @Override
    public void gameIsStarted() throws RemoteException {
        //getClientInterface().notifyMessage("La partita è iniziata");
    }

    /**
     * notifco al giocatore che è il suo turno
     * @throws RemoteException
     */
    public void isYourTurn() throws RemoteException {
        //getClientInterface().notifyYourTurn();
    }

    @Override
    public void isYourExcommunicationTurn() throws RemoteException {
        //getClientInterface().notifyYourExcommunicationTurn();
    }

    public void youWin() throws RemoteException {
        //getClientInterface().notifyMessage("Hai vinto, complimenti!!");
    }

    public void youLose() throws RemoteException {
        //getClientInterface().notifyMessage("Hai perso :( ");
    }

    @Override
    public void notifyNewAction(int value, char codeAction) throws RemoteException {
        //getClientInterface().notifyNewAction(value, codeAction);
    }

    @Override
    public void notifyError(String errorMessage) throws RemoteException {
        getClientInterface().notifyMessage(errorMessage);
    }

    @Override
    public void updateResources() throws RemoteException {
        //getClientInterface().updateResources(getPersonalBoard().getQtaResources());
    }

    @Override
    public void initializeBoard(List<DevelopmentCard> towersCardsList) throws RemoteException {
        //implementare
    }


    //metodi erediati da PLAYER INTERFACE /////////////////////////////////////////////////////////


}
