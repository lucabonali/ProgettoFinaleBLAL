package game;

import api.PlayerInterface;
import board.Board;
import board.PersonalBoard;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luca
 * @author Andrea
 *
 * Classe che gestisce il comportamento della singola partita
 */
public class Game {
    private int numPlayers,period=1,turn=1,lap=1;
    private Board board;
    private Map<Integer, PlayerInterface> playerMap;
    private Map<Integer, PersonalBoard> personalBoardMap;
    private List<PlayerInterface> turnOrder;
    private PlayerInterface currentPlayer;

    //variabile di prova
    private String name;

    public Game() {
        this.numPlayers = 0;
        board = new Board(numPlayers);
        playerMap = new HashMap<>();
        personalBoardMap = new HashMap<>();
        turnOrder = new ArrayList<>();
    }

    public void addPlayer(PlayerInterface playerInterface){
        numPlayers++;
        playerMap.put(numPlayers , playerInterface);
        personalBoardMap.put(numPlayers,new PersonalBoard(numPlayers));
    }


    public int getId(PlayerInterface player){
        for(int i = 1; i<=numPlayers ; i++){
            if(player == playerMap.get(i))
                return i;
        }
        return -1;
    }

    public int getNumPlayers() {

        return numPlayers;
    }

    public boolean isFull(){
        return numPlayers == 4;
    }


    //metodo di prova
    public void setString(String name) throws RemoteException {
        this.name = name;
        for(int i=1; i<=numPlayers; i++){
            playerMap.get(i).writeToClient(name);
        }
    }
}
