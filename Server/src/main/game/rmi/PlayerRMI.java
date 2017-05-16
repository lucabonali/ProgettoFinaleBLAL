package main.game.rmi;

import main.api.LorenzoException;
import main.api.MessageGame;
import main.controller.board.FamilyMember;
import main.game.AbstractPlayer;

import java.rmi.RemoteException;


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
        getClientInterface().notifyMessage("La partita è iniziata");
    }

    public void isYourTurn() throws RemoteException {
        getClientInterface().notifyMessage("Tocca a te!!");
    }

    public void youWin() throws RemoteException {
        getClientInterface().notifyMessage("Hai vinto, complimenti!!");
    }

    public void youLose() throws RemoteException {
        getClientInterface().notifyMessage("Hai perso :( ");
    }


    //metodi erediati da PLAYER INTERFACE /////////////////////////////////////////////////////////

    @Override
    public void doAction(MessageGame msg) throws RemoteException {
        FamilyMember familyMember = getPersonalBoard().getFamilyMember(msg.getFamilyMemberType());
        try {
            getGame().doAction(this, msg, familyMember);
            getClientInterface().updateResources(getPersonalBoard().getQtaResources());
        } catch (LorenzoException e) {
            getClientInterface().notifyMessage(e.getMessage());
        }
    }

}
