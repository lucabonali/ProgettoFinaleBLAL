package main.model;

import main.MainServer;
import main.api.PlayerInterface;
import main.api.messages.MessageAction;
import main.api.messages.MessageNewAction;
import main.api.types.CardType;
import main.api.types.Phases;
import main.api.types.ResourceType;
import main.game_server.AbstractPlayer;
import main.game_server.exceptions.LorenzoException;
import main.game_server.exceptions.NewActionException;
import main.model.board.Board;
import main.model.board.FamilyMember;
import main.model.fields.Resource;

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
    //1->random; 2->2giocatori; 3->3giocatori; 4->4giocatori
    private int gameMode; //automaticamente mi indica il numero di giocatori che devo attendere


    public Game(int gameMode) {
        this.gameMode = gameMode;
        this.numPlayers = 0;
        playerMap = new HashMap<>();
        turnOrder = new ArrayList<>();
    }

    public AbstractPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * metodo che mi aggiunge un giocatore alla partita.
     * @param abstractPlayer giocatore da aggiungere alla partita
     * @throws RemoteException
     */
    public void addPlayer(AbstractPlayer abstractPlayer) throws RemoteException {
        numPlayers++;
        playerMap.put(numPlayers , abstractPlayer);
        abstractPlayer.createPersonalBoard(numPlayers);
        turnOrder.add(abstractPlayer);
        if(numPlayers == 2 && gameMode == MainServer.RANDOM)
            new Timer(10); //il parametro indica i secondi di attesa
        if(checkMaxNumberReached())
            new Timer(3);
    }

    private boolean checkMaxNumberReached(){
        if (gameMode != MainServer.RANDOM){
            if (numPlayers == gameMode)
                return true;
        }
        else {
            if (numPlayers == 4)
                return true;
        }
        return false;
    }


    /**
     * metodo che mi fa partire la partita
     * @throws RemoteException
     */
    private void startGame() throws RemoteException {
        this.isStarted = true;
        board = new Board(numPlayers);
        phase = Phases.ACTION;
        playerMap.forEach(((integer, player) -> {
            try {
                player.initializeBoard(board.getCompleteListTowersCards());
                ArrayList<Integer> arrayListId = new ArrayList<>();
                playerMap.forEach((opponentId, opponent) -> {
                    if (opponent != player)
                        arrayListId.add(opponentId);
                });
                player.gameIsStarted(arrayListId);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }));
        currentPlayer = turnOrder.get(0);
        currentPlayer.notifyRollDice();
        currentPlayer.isYourTurn();
    }

    /**
     * metodo per il lancio dei dadi, controlla se è il tuo turno..
     * @param player giocatore che ha appena tirato i dadi
     * @throws RemoteException
     */
    public void shotDice(AbstractPlayer player, int orange, int white, int black) throws  RemoteException {
        try{
            checkTurn(player);
            if((player != turnOrder.get(0)) || (lap != 1) || (phase != Phases.ACTION))
                player.notifyError("I dadi sono già stati tirati");
            else {
                turnOrder.forEach(abstractPlayer -> abstractPlayer.setDiceValues(orange, white, black));
                currentPlayer.isYourTurn();
            }
        }
        catch (LorenzoException e) {
            player.notifyError(e.getMessage());
        }
    }


    /**
     * mi ottiene l'id del giocatore
     * @param player
     * @return
     */
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
     * @param player giocatore da controllare
     * @throws LorenzoException
     */
    public void checkTurn(AbstractPlayer player) throws LorenzoException {
        if (player != currentPlayer)
            throw new LorenzoException("non è il tuo turno");
    }

    /**
     * controlla se il familiare è già posizionato
     * @param familyMember familiare da controllare
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
    public void doAction(AbstractPlayer player, MessageAction msg, FamilyMember familyMember) throws RemoteException {
        if (phase == Phases.ACTION){
            try {
                isStarted();
                checkTurn(player);
                isAlreadyPositioned(familyMember);
                board.doAction(player, msg, familyMember);
                familyMember.setPositioned(true);
                player.updateMove();

                endMove(player); //mi esegue la fine de turno
            }
            catch (NewActionException e) {
                familyMember.setPositioned(true);
                player.updateMove();
                //ho attivato un effetto che mi fa fare una nuova azione, perciò non è finito il mio turno
                phase = Phases.NEW_ACTION;
            }
            catch (LorenzoException e) {
                player.notifyError(e.getMessage());
            }
        }
        else {
            player.notifyError("non sei nella fase azione della partita!!!");
        }
    }

    /**
     * notifica a tutti i giocatori ,tranne a quello che ha eseguito la mossa, che cosa ha modificato la mossa appena
     * eseguita sul tabellone
     * @param player giocatore che ha eseguito la mossa
     * @param id id dello stesso giocatore
     * @param personalcardsMap mappa delle carte personali
     * @param qtaResourcesMap mappa delle risorse
     */
    public void notifyAllPlayers(AbstractPlayer player, int id, Map<CardType, List<String>> personalcardsMap, Map<ResourceType, Integer> qtaResourcesMap) {
        playerMap.forEach(((integer, opponent) -> {
            if (opponent != player)
                try {
                    opponent.updateOpponentMove(id, personalcardsMap, qtaResourcesMap);
                }
                catch (RemoteException e) {
                    e.printStackTrace();
                }
        }));
    }

    /**
     * rappresenta l'esecuzione di una nuova azione (senza familiare)
     * mi controlla se sono nella fase corretta e se è il mio turno, dopodiché passa
     * l'esecuzione della nuova azione al tabellone
     * @param player giocatore che sta eseguendo la nuova azione
     * @param msg messaggio codificato dell'azione
     * @throws RemoteException
     */
    public void doNewAction(AbstractPlayer player, MessageNewAction msg) throws RemoteException {
        if (phase == Phases.NEW_ACTION) {
            try {
                checkTurn(player);
                board.doNewAction(player, msg);
                player.updateMove();
                phase = Phases.ACTION;
                endMove(player);
            }
            catch (LorenzoException e) {
                player.notifyError(e.getMessage());
            }
            catch (NewActionException e) {
                //ho attivato un effetto che mi fa fare una nuova azione, perciò non è finito il mio turno
                phase = Phases.NEW_ACTION;
            }
        }
        else {
            player.notifyError("non sei nella fase azione della partita!!!");
        }
    }

    /**
     * viene chiamato dopo che il giocatore ha eseguito la mossa
     * controlla se è finito il giro o no
     * @param player il giocatore che ha terminato la mossa
     * @throws RemoteException in caso di problemi di connesisone
     */
    public void endMove(AbstractPlayer player) throws RemoteException {
        try {
            isStarted();
            checkTurn(player);
            player.notifyEndMove();
            for(int i = 0 ; i < numPlayers ; i++){
                if(currentPlayer == turnOrder.get(i)) {
                    if (i == numPlayers - 1) {
                        System.out.println("fine giro " + lap);
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
        catch (NewActionException e) {
            e.printStackTrace();
            //non dovrebbe mai verificarsi
        }
        catch (LorenzoException e) {
            player.notifyError(e.getMessage());
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
            System.out.println("fine turno scomunica");
            endTurn();
        }
        else if (lap == 4 && phase == Phases.ACTION){
            lap = 1;
            System.out.println("fine turno " + turn);
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
            System.out.println("fine periodo " + period);
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
//        List<DevelopmentCard> cardsList = new ArrayList<>();
//        cardsList.addAll(board.getCardsFromTower(CardType.TERRITORY));
//        cardsList.addAll(board.getCardsFromTower(CardType.CHARACTER));
//        cardsList.addAll(board.getCardsFromTower(CardType.BUILDING));
//        cardsList.addAll(board.getCardsFromTower(CardType.VENTURES));
        playerMap.forEach(((id, player) -> {
            try {
                player.initializeBoard(board.getCompleteListTowersCards());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }));
        currentPlayer = turnOrder.get(0);
        currentPlayer.notifyRollDice();
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
                if (player.getPersonalBoard().getQtaResources().get(ResourceType.MILITARY) == militaryValues.get(i)) {
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
        private final long MIN_INTERVAL_TO_START;
        private int seconds = 0;

        /**
         * costruttore che prende un parametro il quale indica i secondi che devo aspettare prima
         * di far cominciare la partita
         * @param seconds secondi
         */
        public Timer (long seconds) {
            this.MIN_INTERVAL_TO_START = seconds;
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
