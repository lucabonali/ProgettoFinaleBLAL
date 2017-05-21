package main.model;

import main.api.PlayerInterface;
import main.api.exceptions.LorenzoException;
import main.api.exceptions.NewActionException;
import main.api.messages.MessageGame;
import main.api.types.CardType;
import main.api.types.Phases;
import main.api.types.ResourceType;
import main.model.board.Board;
import main.model.board.FamilyMember;
import main.model.board.developmentCard;
import main.model.fields.Resource;
import main.servergame.AbstractPlayer;

import java.rmi.RemoteException;
import java.util.*;

/**
 * @author Luca
 * @author Andrea
 *
 * Classe che gestisce il comportamento della singola partita
 */
public class Game {
    private boolean isStarted;
    private int numPlayers;
    private int period=1,turn=1,lap=1;
    private Board board;
    private Map<Integer, AbstractPlayer> playerMap;
    private List<AbstractPlayer> turnOrder;
    private AbstractPlayer currentPlayer;
    private Phases phase = Phases.ACTION;


    public Game() {
        this.numPlayers = 0;
        playerMap = new HashMap<>();
        turnOrder = new ArrayList<>();
    }

    public AbstractPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * metodo che mi aggiunge un giocatore alla partita.
     * @param abstractPlayer
     * @throws RemoteException
     */
    public void addPlayer(AbstractPlayer abstractPlayer) throws RemoteException {
        numPlayers++;
        playerMap.put(numPlayers , abstractPlayer);
        abstractPlayer.createPersonalBoard(numPlayers);
        turnOrder.add(abstractPlayer);
        if(numPlayers == 2)
            new Timer();
        if(numPlayers == 4)
            startGame();
    }

    /**
     * metodo che mi fa partire la partita
     * @throws RemoteException
     */
    private void startGame() throws RemoteException {
        this.isStarted = true;
        board = new Board(numPlayers);
        phase = Phases.ACTION;
        currentPlayer = turnOrder.get(0);
        currentPlayer.isYourTurn();
    }

    /**
     * simula il lancio dei dadi!!
     * @param player
     * @throws LorenzoException
     */
    public void shotDice(AbstractPlayer player, int orange, int white, int black) throws  RemoteException {
        try{
            checkTurn(player);
            if(!(player == turnOrder.get(0)) && (lap == 1) && (phase == Phases.ACTION))
                player.notifyError("I dadi sono già stati tirati");
            else
                turnOrder.forEach(abstractPlayer -> abstractPlayer.setDiceValues(orange, white, black));
        }
        catch (LorenzoException e) {
            player.notifyError(e.getMessage());
        }
    }


    public int getId(PlayerInterface player){
        for(int i = 1; i<=numPlayers ; i++){
            if(player == playerMap.get(i))
                return i;
        }
        return -1;
    }

    /**
     * mi dice se la partita è al completo o comunque se è già cominciata!
     * @return
     */
    public boolean isFull(){
        return isStarted;
    }

    /**
     * metodo che controlla se è il turno del giocatore passato come parametro
     * @param player
     * @throws LorenzoException
     */
    private void checkTurn(AbstractPlayer player) throws LorenzoException {
        if (player != currentPlayer)
            throw new LorenzoException("non è il tuo turno");
    }

    /**
     * controlla se il familiare è già posizionato
     * @param familyMember
     * @throws LorenzoException
     */
    private void isAlreadyPositioned(FamilyMember familyMember) throws LorenzoException {
        if (familyMember.isPositioned())
            throw new LorenzoException("il familiare è già stato posizionato!!");
    }

    /**
     * controlla se la partita è cominciata
     * @throws LorenzoException
     */
    private void isStarted() throws LorenzoException {
        if (!isStarted)
            throw new LorenzoException("la partita non è ancora cominciata!!");
    }

    /**
     * mi scomunica il giocatore preciso
     * @param player giocatore
     */
    public void excommunicatePlayer(AbstractPlayer player) throws RemoteException {
        if (phase == Phases.EXCOMMUNICATION){
            try {
                checkTurn(player);
                board.excommunicatePlayer(period, player);
            }
            catch (LorenzoException e) {
               player.notifyError(e.getMessage());
            }
        }
        else {
            player.notifyError("non sei nella fase di scomunica!!!");
        }
    }

    /**
     * metodo che rappresenta l'azione di dare sostegno alla chiesa, se può, se no
     * mi scomunica automaticamente il giocatore
     * @param player giocatore
     */
    public void giveChurchSupport(AbstractPlayer player) throws RemoteException {
        if (phase == Phases.EXCOMMUNICATION){
            try {
                checkTurn(player);
                if (board.canGiveSupport(period, player)) {
                    int faithPoints = player.getPersonalBoard().getQtaResources().get(ResourceType.FAITH);
                    Resource res = new Resource(faithPoints, ResourceType.VICTORY);
                    player.getPersonalBoard().modifyResources(res);
                    player.getPersonalBoard().modifyResources(new Resource(-faithPoints, ResourceType.FAITH));
                }
                else {
                    excommunicatePlayer(player);
                }
            } catch (LorenzoException e) {
                player.notifyError(e.getMessage());
            }
        }
        else {
            player.notifyError("non sei nella fase di scomunica!!!");
        }
    }

    /**
     * metodo che mi controlla se è il turno del mio giocatore e se il familiare
     * è già stato posizionato o meno
     * @param player giocatore che la esegue
     * @param msg messggio da decodificare
     * @param familyMember familiare da spostare, già ricavato dall classe che lo invoca
     * @throws RemoteException in caso si verifichino errori
     */
    public void doAction(AbstractPlayer player, MessageGame msg, FamilyMember familyMember) throws RemoteException {
        if (phase == Phases.ACTION){
            try {
                isStarted();
                checkTurn(player);
                isAlreadyPositioned(familyMember);
                board.doAction(msg, player, familyMember);
                familyMember.setPositioned(true);
                player.updateResources();
                endMove(); //mi esegue la fine de turno
            } catch (NewActionException e) {
                //ho attivato un effetto che mi fa fare una nuova azione, perciò non è finito il mio turno
            } catch (LorenzoException e) {
                player.notifyError(e.getMessage());
            }
        }
        else {
            player.notifyError("non sei nella fase azione della partita!!!");
        }
    }

    /**
     * viene chiamato dopo che il giocatore ha eseguito la mossa
     * controlla se è finito il giro o no
     * @throws RemoteException in caso di problemi di connesisone
     * @throws NewActionException non si dovrebbe mai verificare, perché gli edifici non lanciano questi
     *                              effetti
     */
    public void endMove() throws RemoteException, NewActionException {
        for(int i = 0 ; i < numPlayers ; i++){
            if(currentPlayer == turnOrder.get(i)) {
                if (i == numPlayers - 1) {
                    endLap();
                    return;
                }
                else{
                    currentPlayer = turnOrder.get(i+1);
                    if (phase == Phases.ACTION)
                        currentPlayer.isYourTurn();
                    else
                        currentPlayer.isYourExcommunicationTurn();
                    return;
                }
            }
        }
    }

    /**
     * controlla se è finito il turno.
     * @throws RemoteException
     */
    private void endLap() throws RemoteException, NewActionException {
        if (lap == 1 && phase == Phases.EXCOMMUNICATION){
            phase = Phases.ACTION;
            lap = 1;
            endTurn();
        }
        else if (lap == 4 && phase == Phases.ACTION){
            lap = 1;
            endTurn();
        }
        else{
            //sempre nella fase azione
            lap++;
            currentPlayer = turnOrder.get(0);
            currentPlayer.isYourTurn();
        }
    }

    /**
     * controlla se è finito il periodo, cioè questo è l'ultimo turno di costui
     * @throws RemoteException
     */
    private void endTurn() throws RemoteException, NewActionException {
        if(turn == 2 && phase == Phases.ACTION){
            turn++;
            phase = Phases.EXCOMMUNICATION;
            currentPlayer = turnOrder.get(0);
            currentPlayer.isYourExcommunicationTurn();
        }
        else if(turn == 3 && phase == Phases.EXCOMMUNICATION) {
            turn = 1;
            phase = Phases.ACTION;
            endPeriod();
        }
        else{
            //siamo nella fase azione, nel primo turno
            turn++;
            sortPlayerOrder();
        }
    }


    /**
     * controlla se è l'ultimo periodo, cioè è finita la partita.
     * @throws RemoteException
     */
    private void endPeriod() throws RemoteException, NewActionException {
        if(period == 3){
            endGame();
        }
        else{
            period ++;
            sortPlayerOrder();
        }
    }


    /**
     * questo metodo controlla lo spazio azione del consiglio e riposiziona nella
     * lista turnOrder il nuovo ordine correto per il nuovo turno.
     * @throws RemoteException
     */
    private void sortPlayerOrder() throws RemoteException {
        List<FamilyMember> familyMembersList = board.getOrder();
        if(!familyMembersList.isEmpty()){
            List<AbstractPlayer> newTurnOrder = new ArrayList<>();
            for(FamilyMember f : familyMembersList){
                for(AbstractPlayer p : turnOrder){
                    if(f == p.getFamilyMember(f.getType())) {
                        boolean isPresent = false;
                        for(PlayerInterface p1 : newTurnOrder){
                            if (p1 == p){
                                isPresent = true;
                            }
                        }
                        if(!isPresent){
                            newTurnOrder.add(p);
                        }
                    }
                }
            }
            if(!(newTurnOrder.size() == turnOrder.size())) {
                for (AbstractPlayer p : turnOrder) {
                    boolean isPresent = false;
                    for (AbstractPlayer newP : newTurnOrder) {
                        if (newP == p)
                            isPresent = true;
                    }
                    if (!isPresent)
                        newTurnOrder.add(p);

                }
            }
            turnOrder = newTurnOrder;
        }
        //inizializza il turno sul tabellone
        board.initializeTurn(period, turn);
        playerMap.forEach(((integer, abstractPlayer) -> abstractPlayer.removeAllFamilyMembers()));
        List<developmentCard> cardsList = new ArrayList<>();
        cardsList.addAll(board.getCardsFromTower(CardType.TERRITORY));
        cardsList.addAll(board.getCardsFromTower(CardType.CHARACTER));
        cardsList.addAll(board.getCardsFromTower(CardType.BUILDING));
        cardsList.addAll(board.getCardsFromTower(CardType.VENTURES));
        playerMap.forEach(((integer, abstractPlayer) -> {
            try {
                abstractPlayer.initializeBoard(cardsList);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }));
        currentPlayer = turnOrder.get(0);
        currentPlayer.isYourTurn();
    }

    /**
     * metodo chiamato al termine della gara, non fa altro che calcolare
     * tutti i punti vittoria di ciascun giocatore e decretare il vincitore.
     */
    private void endGame() throws RemoteException, NewActionException {
        //military points
        Map<AbstractPlayer, Integer> militaryMap = new HashMap<>();
        for (AbstractPlayer player: turnOrder){
            militaryMap.put(player, player.getPersonalBoard().getQtaResources().get(7));
        }

        List<AbstractPlayer> militaryWinners = new LinkedList<>();
        List<Integer> militaryValues = new LinkedList<>(militaryMap.values());
        Collections.sort(militaryValues);
        for (AbstractPlayer player : turnOrder){
            for(int i=0; i<numPlayers; i++) {
                if (player.getPersonalBoard().getQtaResources().get(7) == militaryValues.get(i)) {
                    militaryWinners.add(player);
                    break;
                }
            }
        }

        militaryWinners.get(0).getPersonalBoard().modifyResources(new Resource(5, ResourceType.VICTORY));
        militaryWinners.get(1).getPersonalBoard().modifyResources(new Resource(2, ResourceType.VICTORY));

        //victory points
        Map<AbstractPlayer, Integer> victoryMap = new HashMap<>();
        for (AbstractPlayer player: turnOrder){
            victoryMap.put(player, player.calculateVictoryPoints());
        }
        AbstractPlayer winner = turnOrder.get(0);
        for (AbstractPlayer player: turnOrder){
            if(victoryMap.get(winner) < victoryMap.get(player))
                winner = player;
        }
        winner.youWin();
        for (AbstractPlayer player: turnOrder)
            if(player != winner)
                player.youLose();
    }

    /**
     * mi rimuove il giocatore passato come paramentro dalla partita
     * e verifica se la parittiaha ancora un numero sufficiente di giocatori
     * @param player
     */
    public void removePlayer(AbstractPlayer player) {
        playerMap.remove(player.getIdPlayer());
        for (int i=0; i<numPlayers; i++){
            if (turnOrder.get(i) == player)
                turnOrder.remove(i);
        }
        numPlayers--;
        if (numPlayers<2){
            //la partita è finita e l'ultimo giocatorer rimasto è il vincitore.
        }
    }


    private class Timer extends Thread{
        private final int MIN_INTERVAL_TO_START = 5;
        private int seconds = 0;
        public Timer () {
            this.start();
        }

        public void run(){
            while (seconds < MIN_INTERVAL_TO_START){
                try {
                    Thread.sleep(1000);
                    seconds++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                startGame();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }
}
