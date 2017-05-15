package game;

import api.Message;
import api.PlayerInterface;
import board.Board;
import board.FamilyMember;
import board.PersonalBoard;
import api.LorenzoException;

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
    private int numPlayers;
    private int period=1,turn=1,lap=1;
    private Board board;
    private Map<Integer, PlayerInterface> playerMap;
    private Map<Integer, PersonalBoard> personalBoardMap;
    private List<PlayerInterface> turnOrder;
    private PlayerInterface currentPlayer;

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
        playerInterface.createPersonalBoard(numPlayers);
    }


    public int getId(PlayerInterface player){
        for(int i = 1; i<=numPlayers ; i++){
            if(player == playerMap.get(i))
                return i;
        }
        return -1;
    }

    public boolean isFull(){
        return numPlayers == 4;
    }

    /**
     * metodo che mi controlla se è il turno del mio giocatore e se il familiare
     * è già stato posizionato o meno
     * @param player giocatore che la esegue
     * @param msg messggio da decodificare
     * @param familyMember familiare da spostare, già ricavato dall classe che lo invoca
     * @throws LorenzoException in caso si verifichino errori
     */
    public void doAction(PlayerInterface player, Message msg, FamilyMember familyMember) throws LorenzoException{
        if(!(player == currentPlayer))
            throw new LorenzoException("non è il tuo turno");
        if (familyMember.isPositioned())
            throw new LorenzoException("il familiare è già stato posizionato!!");
        board.doAction(msg, familyMember);
    }
}
