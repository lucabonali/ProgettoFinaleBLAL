package game;

import api.MessageGame;
import api.PlayerInterface;
import controller.board.Board;
import controller.board.FamilyMember;
import api.LorenzoException;
import controller.fields.Resource;
import controller.types.ResourceType;

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


    public Game() {
        this.numPlayers = 0;
        playerMap = new HashMap<>();
        turnOrder = new ArrayList<>();
    }

    public void addPlayer(AbstractPlayer abstractPlayer) throws RemoteException {
        numPlayers++;
        playerMap.put(numPlayers , abstractPlayer);
        abstractPlayer.createPersonalBoard(numPlayers);
        if(numPlayers == 2)
            new Timer();
        if(numPlayers == 4)
            startGame();
    }

    private void startGame() throws RemoteException {
        this.isStarted = true;
        board = new Board(numPlayers);
        for(int i = 1 ; i <= numPlayers ; i++ ){
            turnOrder.add(playerMap.get(i));
        }
        currentPlayer = turnOrder.get(0);
        currentPlayer.isYourTurn();
    }

    public void shotDice(AbstractPlayer player) throws LorenzoException {
        checkTurn(player);
        if(!(player == turnOrder.get(0)))
            throw new LorenzoException("I dadi sono già stati tirati");
        int orange,white,black;
        orange = new Random().nextInt(5)+1;
        white = new Random().nextInt(5)+1;
        black = new Random().nextInt(5)+1;
        for(AbstractPlayer p : turnOrder){
            p.setDiceValues(orange,white,black);
        }
    }


    public int getId(PlayerInterface player){
        for(int i = 1; i<=numPlayers ; i++){
            if(player == playerMap.get(i))
                return i;
        }
        return -1;
    }

    public boolean isFull(){
        if(isStarted)
            return true;
        return false;
    }

    public void checkTurn(AbstractPlayer player) throws LorenzoException {
        if(!(player == currentPlayer))
            throw new LorenzoException("non è il tuo turno");
    }

    /**
     * metodo che mi controlla se è il turno del mio giocatore e se il familiare
     * è già stato posizionato o meno
     * @param player giocatore che la esegue
     * @param msg messggio da decodificare
     * @param familyMember familiare da spostare, già ricavato dall classe che lo invoca
     * @throws LorenzoException in caso si verifichino errori
     */
    public void doAction(AbstractPlayer player, MessageGame msg, FamilyMember familyMember) throws LorenzoException, RemoteException {
        checkTurn(player);
        if (familyMember.isPositioned())
            throw new LorenzoException("il familiare è già stato posizionato!!");
        board.doAction(msg, familyMember);
        endMove();
    }

    /**
     * viene chiamato dopo che il giocatore ha eseguito la mossa
     * controlla se è finito il giro o no
     * @throws RemoteException
     */
    private void endMove() throws RemoteException {
        for(int i = 0 ; i < numPlayers ; i++){
            if(currentPlayer == turnOrder.get(i)) {
                if (i == numPlayers - 1)
                    endLap();
                else{
                    currentPlayer = turnOrder.get(i+1);
                    currentPlayer.isYourTurn();
                }
            }

        }
    }

    /**
     * controlla se è finito il turno.
     * @throws RemoteException
     */
    private void endLap() throws RemoteException {
        if (lap == 4){
            lap = 1;
            endTurn();
        }
        else{
            lap++;
            currentPlayer = turnOrder.get(0);
            currentPlayer.isYourTurn();
        }
    }

    /**
     * controlla se è finito il periodo, cioè questo è l'ultimo turno di costui
     * @throws RemoteException
     */
    private void endTurn() throws RemoteException {
        if(turn == 2){
            turn = 1;
            endPeriod();
        }
        else{
            turn++;
            sortPlayerOrder();
        }
    }

    /**
     * controlla se è l'ultimo periodo, cioè è finita la partita.
     * @throws RemoteException
     */
    private void endPeriod() throws RemoteException {
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
        board.initializeTurn(period, turn);
        currentPlayer = turnOrder.get(0);
        currentPlayer.isYourTurn();
    }

    /**
     * metodo chiamato al termine della gara, non fa altro che calcolare
     * tutti i punti vittoria di ciascun giocatore e decretare il vincitore.
     */
    private void endGame() throws RemoteException {
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


    private class Timer extends Thread{
        private int seconds = 0;
        public Timer () {
            this.start();
        }

        public void run(){
            while (seconds < 30){
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
