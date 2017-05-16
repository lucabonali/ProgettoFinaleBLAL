package game;

import api.Message;
import api.PlayerInterface;
import board.Board;
import board.FamilyMember;
import api.LorenzoException;

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
    private Map<Integer, PlayerInterface> playerMap;
    private List<PlayerInterface> turnOrder;
    private PlayerInterface currentPlayer;


    public Game() {
        this.numPlayers = 0;
        playerMap = new HashMap<>();
        turnOrder = new ArrayList<>();
    }

    public void addPlayer(PlayerInterface playerInterface) throws RemoteException {
        numPlayers++;
        playerMap.put(numPlayers , playerInterface);
        playerInterface.createPersonalBoard(numPlayers);
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

    public void shotDice(PlayerInterface player) throws LorenzoException {
        checkTurn(player);
        if(!(player == turnOrder.get(0)))
            throw new LorenzoException("I dadi sono già stati tirati");
        int orange,white,black;
        orange = new Random().nextInt(5)+1;
        white = new Random().nextInt(5)+1;
        black = new Random().nextInt(5)+1;
        for(PlayerInterface p : turnOrder){
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

    public void checkTurn(PlayerInterface player) throws LorenzoException {
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
    public void doAction(PlayerInterface player, Message msg, FamilyMember familyMember) throws LorenzoException, RemoteException {
        checkTurn(player);
        if (familyMember.isPositioned())
            throw new LorenzoException("il familiare è già stato posizionato!!");
        board.doAction(msg, familyMember);
        endMove();
    }

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

    private void endTurn() throws RemoteException {
        if(turn == 2){
            turn = 1;
            endPeriod();
        }
        else{
            turn++;
            initializeTurn();
        }
    }



    private void endPeriod() throws RemoteException {
        if(period == 3){
            endGame();
        }
        else{
            period ++;
            initializeTurn();
        }
    }


    private void initializeTurn() throws RemoteException {
        List<FamilyMember> familyMembersList = board.getOrder();
        if(!familyMembersList.isEmpty()){
            List<PlayerInterface> newTurnOrder = new ArrayList<>();
            for(FamilyMember f : familyMembersList){
                for(PlayerInterface p : turnOrder){
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
            if(newTurnOrder.size() == turnOrder.size())
                turnOrder = newTurnOrder;
            else {
                for (PlayerInterface p : turnOrder){
                    boolean isPresent = false;
                    for (PlayerInterface newP : newTurnOrder){
                        if(newP == p)
                            isPresent = true;
                    }
                    newTurnOrder.add(p);
                }
            }
        }
        board.initializeTurn(period, turn);
        currentPlayer = turnOrder.get(0);
        currentPlayer.isYourTurn();
    }

    private void endGame() {
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
