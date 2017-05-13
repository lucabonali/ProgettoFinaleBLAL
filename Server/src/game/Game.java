package game;

import board.Board;

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
    private Map<Integer,Player> playerMap;
    private List<Player> turnOrder;

    public Game(int numPlayers){
        this.numPlayers = numPlayers;
        board = new Board(numPlayers);
        playerMap = new HashMap<>();
        turnOrder = new ArrayList<>();
    }

    public void addPlayer(int id,Player player){
        playerMap.put(id, player);
    }



}
