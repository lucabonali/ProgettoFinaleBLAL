package game;

import board.Board;
import exceptions.LorenzoException;

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
    private List<PlayerInterface> turnOrder;

    public Game() throws LorenzoException {
        this.numPlayers = 0;
        board = new Board(numPlayers);
        playerMap = new HashMap<>();
        turnOrder = new ArrayList<>();
    }

    public void addPlayer(PlayerInterface playerInterface){
        numPlayers++;
        playerMap.put(numPlayers , playerInterface);
    }


    public Board getBoard() {
        return board;
    }

    public int getNumPlayers() {

        return numPlayers;
    }

    public boolean isFull(){
        return numPlayers == 4;
    }


}
