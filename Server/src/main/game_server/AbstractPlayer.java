package main.game_server;

import main.api.ClientInterface;
import main.api.PlayerInterface;
import main.api.messages.MessageAction;
import main.api.messages.MessageNewAction;
import main.api.types.CardType;
import main.api.types.FamilyMemberType;
import main.api.types.ResourceType;
import main.game_server.exceptions.NewActionException;
import main.model.Game;
import main.model.action_spaces.Action;
import main.model.board.DevelopmentCard;
import main.model.board.FamilyMember;
import main.model.board.PersonalBoard;
import main.model.fields.Resource;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

/**
 * @author Luca
 * @author Andrea
 */
public abstract class AbstractPlayer extends UnicastRemoteObject implements PlayerInterface {
    private ClientInterface clientInterface;
    private String userName;
    private PersonalBoard personalBoard;
    private int idPlayer;
    private Game game;

    public AbstractPlayer(String userName) throws RemoteException {
        this.userName = userName;
    }

    public void setGame(Game game) {
        this.game = game;
        idPlayer = game.getId(this);
    }

    public int calculateVictoryPoints() throws RemoteException, NewActionException {
        return personalBoard.calculateVictoryPoints();
    }

    /**
     * metodo che mi setta il valore a tutti i miei familiari
     * @param orange valore dell'arancione
     * @param white valore del bianco
     * @param black valore del nero
     */
    public void setDiceValues(int orange, int white, int black) {
        personalBoard.setDiceValues(orange,white,black);
        try {
            sendDicesValues(orange, white, black);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public FamilyMember getFamilyMember(FamilyMemberType type) {
        return personalBoard.getFamilyMember(type);
    }

    public Game getGame() {
        return game;
    }

    public void createPersonalBoard(int id) {
        this.idPlayer = id;
        personalBoard = new PersonalBoard(id);
    }

    public PersonalBoard getPersonalBoard() {
        return this.personalBoard;
    }

    public ClientInterface getClientInterface() {
        return this.clientInterface;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    /**
     * metodo che mi rimuove tutti i familiari dalle loro postazione
     * cioè mi setterà a false il boolean isPositioned di ciascuno.
     */
    public void removeAllFamilyMembers(){
        personalBoard.removeAllFamilyMembers();
    };

    public void activeExcommunicationEffects(Action action, int type) throws RemoteException{
        personalBoard.setCurrentAction(action);
        try {
            game.activeFirstPeriodExcommunication(action, type);
        }
        catch (NewActionException e) {
            //non dovrebbe mai verificrsi
            e.printStackTrace();
        }
    }

    public void activeExcommunicationEffects(Action action) throws RemoteException{
        personalBoard.setCurrentAction(action);
        try {
            game.activeSecondPeriodExcommunication(action);
        }
        catch (NewActionException e) {
            //non dovrebbe mai verificrsi
            e.printStackTrace();
        }
    }

    public void activeExcommunicationEffects() throws RemoteException{
        game.activeThirdPeriodExcommunication();
    }



    /// METODI ASTRATTI AGGIUNTI DA QUESTA CLASSE E CHE VERRANNO IMPLEMENTATI DALLE SUE DUE DIRETTE SOTTOCLASSI ///////////////


    /**
     * mi notifica che la partita è cominciata
     * @throws RemoteException
     */
    public abstract void gameIsStarted(List<Integer> idList, List<String> codeList) throws RemoteException;

    /**
     * mi notifica che è il mio turno
     * @throws RemoteException
     */
    public abstract void isYourTurn() throws RemoteException;

    /**
     * metodo che notifica al client che è il suo turno di scelta se scomunicarsi o no.
     * @throws RemoteException
     */
    public abstract void isYourExcommunicationTurn() throws RemoteException;

    /**
     * mi notifica che ho vinto
     * @throws RemoteException
     */
    public abstract void youWin() throws RemoteException;

    /**
     * mi notifica che ho perso
     * @throws RemoteException
     */
    public abstract void youLose() throws RemoteException;

    /**
     * mi notifica che devo fare un'altra azione
     * @param value valore dell'azione
     * @param codeAction codice che mi identifica che tipo di azione posso fare
     * @throws RemoteException
     */
    public abstract void notifyNewAction(int value, char codeAction) throws RemoteException;

    /**
     * mi notifica un messaggio di errore
     * @param message messaggio
     * @throws RemoteException
     */
    public abstract void notifyError(String message) throws RemoteException;

    /**
     * mi notifica i cambiamenti nella mia plancia in seguito alla mia mossa
     * @param msgAction messaggio codificato dell'azione appena andata a buon fine
     * @throws RemoteException
     */
    public abstract void updateMove(MessageAction msgAction) throws RemoteException;

    /**
     * notifica a tutti i giocatori che cosa ha mosso il giocatore che ha appena effettuato la mossa
     * @throws RemoteException
     */
    public abstract void updateOpponentMove(int id, Map<CardType, List<String>> personalcardsMap, Map<ResourceType, Integer> qtaResourcesMap, MessageAction msg) throws RemoteException;

    /**
     * notifica al giocatore che deve tirare i dadi
     * @throws RemoteException
     */
    public abstract void notifyRollDice() throws RemoteException;

    /**
     * notifica al giocatore che è stato scomunicato
     * @param id id del giocatore
     * @param period periodo
     * @throws RemoteException
     */
    public abstract void excommunicate(int id, int period) throws RemoteException;

    /**
     * notifica che un altro giocatore è stato scomunicato
     * @param idPlayer id del giocatore
     * @param period periodo
     */
    public abstract void opponentExcommunicate(int idPlayer, int period) throws RemoteException;

    /**
     * metodo che invia al client i risultati del tiro del dado
     * @param orange valore dado arancione
     * @param white dado bianco
     * @param black dado nero
     * @throws RemoteException
     */
    public abstract void sendDicesValues(int orange, int white, int black) throws RemoteException;

    /**
     * metodo che viene chiamato per inizializzare il turno, cioè mi invia al
     * client tutte le carte che sono state pescate in questo turno
     * @param towersCardsList lista di stringhe che mi indica i nomi delle carte pescate
     * @throws RemoteException
     */
    public abstract void initializeBoard(List<DevelopmentCard> towersCardsList) throws RemoteException;

    /**
     * notifica al client che ha terminato il suo turno
     * @throws RemoteException
     */
    public abstract void notifyEndMove() throws RemoteException;

    /**
     * metodo che notifica il guadagno di un privilegio
     * @throws RemoteException
     */
    public abstract void notifyPrivilege() throws RemoteException;

    /**
     * invia al client l'ordine dei giocatori
     * @param playersOrderList lista dei giocatori
     * @throws RemoteException
     */
    public abstract void sendOrder(List<AbstractPlayer> playersOrderList) throws RemoteException;



    /// METODI IMPLEMENTATI DALL'INTERFACCIA PLAYER INTERFACE ///////////////////////////////////////////////



    @Override
    public void shotDice(int orange, int white, int black) throws RemoteException{
        game.shotDice(this, orange, white, black);
    }


    @Override
    public void addClientInterface(ClientInterface clientInterface) throws RemoteException {
        this.clientInterface = clientInterface;
    }

    @Override
    public synchronized void doAction(MessageAction msg) throws RemoteException {
        FamilyMember familyMember = personalBoard.getFamilyMember(msg.getFamilyMemberType());
        game.doAction(this, msg, familyMember);
    }

    @Override
    public void endMove() throws RemoteException {
        game.endMove(this);
    }

    @Override
    public synchronized void surrender() throws RemoteException {
        game.removePlayer(this);
    }

    @Override
    public void doNewAction(MessageNewAction msg) throws RemoteException {
        game.doNewAction(this, msg);
    }

    /**
     * identifica la scelta nella fase di scomunica.
     * @param choice true accetto la scomunica, false do sostegno
     * @throws RemoteException
     */
    @Override
    public synchronized void excommunicationChoice(boolean choice) throws RemoteException {
        if (choice){
            game.excommunicatePlayer(this);
        }
        else {
            game.giveChurchSupport(this);
        }
    }

    @Override
    public void convertPrivilege(int qta, ResourceType type) throws RemoteException {
        personalBoard.modifyResources(new Resource(qta, type));
        updateMove(null);
        game.notifyAllPlayers(this, idPlayer, personalBoard.getPersonalCardsMap(), personalBoard.getQtaResources(), null);
    }
}
