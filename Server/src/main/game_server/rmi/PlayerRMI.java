package main.game_server.rmi;

import main.api.messages.MessageAction;
import main.api.types.CardType;
import main.api.types.ResourceType;
import main.game_server.AbstractPlayer;
import main.model.board.DevelopmentCard;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

    // OVERRIDE DEI METODI EREDITATI DA ABSTRACT PLAYER /////////////////////////////////////////////////


    /**
     * notifica l'inizio della partita passando i giocatori avversarie le tessere scomunica
     * @param opponents giocatori avversari
     * @param codeList codici delle tessete scomuniche
     * @throws RemoteException
     */
    @Override
    public void gameIsStarted(Map<Integer, String> opponents, List<String> codeList) throws RemoteException {
        getClientInterface().isGameStarted(getIdPlayer(), opponents, codeList);
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
        getClientInterface().gameEnded("YOU WON, CONGRATS BUDDY!!");
    }

    public void youLose() throws RemoteException {
        getClientInterface().gameEnded(" YOU LOSE, SORRY ");
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
    public void updateMove(MessageAction msg) throws RemoteException {
        getClientInterface().updateResources(getPersonalBoard().getQtaResources());
        getClientInterface().updatePersonalCards(getPersonalBoard().getPersonalCardsMap());
        getGame().notifyAllPlayers(this, getIdPlayer(), getPersonalBoard().getPersonalCardsMap(), getPersonalBoard().getQtaResources(), msg);
    }

    @Override
    public void updateOpponentMove(int id, Map<CardType, List<String>> personalcardsMap, Map<ResourceType, Integer> qtaResourcesMap, MessageAction msgAction) throws RemoteException {
        getClientInterface().opponentMove(id, personalcardsMap, qtaResourcesMap);
        if (msgAction != null)
            getClientInterface().opponentFamilyMemberMove(id, msgAction);
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

    /**
     * notifica che ha appena ottenuto un privilegio
     * @throws RemoteException
     */
    @Override
    public void notifyPrivilege() throws RemoteException {
        getClientInterface().notifyPrivilege();
    }

    /**
     * invio la lista degli id dei giocatori nell'ordine corretto
     * @param playersOrderList lista dei giocatori
     * @throws RemoteException
     */
    @Override
    public void sendOrder(List<AbstractPlayer> playersOrderList) throws RemoteException {
        List<Integer> orderList = new ArrayList<>();
        for (AbstractPlayer player: playersOrderList) {
            orderList.add(player.getIdPlayer());
        }
        getClientInterface().notifyTurnOrder(orderList);
    }

    @Override
    public void opponentSurrender(int id) throws RemoteException {
        getClientInterface().notifyOpponentSurrender(id);
    }

    @Override
    public void excommunicate(int id, int period) throws RemoteException {
        getGame().notifyAllPlayers(this, period);
        getClientInterface().excommunicate(id, period);
    }

    @Override
    public void opponentExcommunicate(int idPlayer, int period) throws RemoteException {
        getClientInterface().excommunicate(idPlayer, period);
    }


    //metodi erediati da PLAYER INTERFACE /////////////////////////////////////////////////////////


}
