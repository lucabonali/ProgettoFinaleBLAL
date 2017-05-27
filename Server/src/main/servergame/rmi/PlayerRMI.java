package main.servergame.rmi;

import main.model.board.DevelopmentCard;
import main.servergame.AbstractPlayer;

import java.rmi.RemoteException;
import java.util.ArrayList;
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

    // OVERRADI DEI METODI EREDITATI DA ABSTRACT PLAYER /////////////////////////////////////////////////

    @Override
    public void gameIsStarted(List<Integer> idList) throws RemoteException {
        getClientInterface().isGameStarted(getIdPlayer(), idList);
    }

    /**
     * notifco al giocatore che è il suo turno
     * @throws RemoteException
     */
    public void isYourTurn() throws RemoteException {
        getClientInterface().notifyYourTurn();
    }

    @Override
    public void isYourExcommunicationTurn() throws RemoteException {
        getClientInterface().notifyYourExcommunicationTurn();
    }

    public void youWin() throws RemoteException {
        //getClientInterface().notifyMessage("Hai vinto, complimenti!!");
    }

    public void youLose() throws RemoteException {
        //getClientInterface().notifyMessage("Hai perso :( ");
    }

    @Override
    public void notifyNewAction(int value, char codeAction) throws RemoteException {
        getClientInterface().notifyNewAction(value, codeAction);
    }

    @Override
    public void notifyError(String errorMessage) throws RemoteException {
        getClientInterface().notifyMessage(errorMessage);
    }

    @Override
    public void updateMove() throws RemoteException {
        getClientInterface().updateResources(getPersonalBoard().getQtaResources());
        getClientInterface().updatePersonalCards(getPersonalBoard().getPersonalCardsMap());
    }

    /**
     * mi informa il giocatore che deve tirare i dadi
     * @throws RemoteException
     */
    @Override
    public void notifyRollDice() throws RemoteException {
        getClientInterface().notifyHaveToShotDice();
    }

    /**
     * mi invia i valori dei dadi ai giocatori
     * @param orange arancio
     * @param white bianco
     * @param black nero
     * @throws RemoteException
     */
    @Override
    public void sendDicesValues(int orange, int white, int black) throws RemoteException {
        getClientInterface().setDiceValues(orange, white, black);
    }

    /**
     * metodo che invia al giocatore la lista delle carte sulle torri
     * @param towersCardsList lista di stringhe che mi indica i nomi delle carte pescate
     * @throws RemoteException
     */
    @Override
    public void initializeBoard(List<DevelopmentCard> towersCardsList) throws RemoteException {
        List<String> list = new ArrayList<>();
        towersCardsList.forEach((developmentCard -> list.add(developmentCard.getName())));
        getClientInterface().setTowersCards(list);
    }

    @Override
    public void notifyEndMove() throws RemoteException {
        getClientInterface().notifyEndMove();
    }


    //metodi erediati da PLAYER INTERFACE /////////////////////////////////////////////////////////


}
