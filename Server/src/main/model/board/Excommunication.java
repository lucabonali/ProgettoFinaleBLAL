package main.model.board;

import main.game_server.exceptions.NewActionException;
import main.model.effects.development_effects.Effect;
import main.model.effects.excommunicating_effects.ExcommunicatingEffectCreator;
import main.game_server.AbstractPlayer;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Luca
 * @author Andrea
 *
 * rappresenta la parte del tabellone della scomunica,gli effetti vengono presi dal db,
 * in base al periodo di appartenenza, poichè
 * per la partia ne serviranno solo una per periodo, la classe mi estrae un
 * numero casuale per tabella e me la crea.
 */
public class Excommunication {
    private final String QUERY_PERIOD_1 = "SELECT * FROM excommunicatingcards WHERE period=1 AND id=";
    private final String QUERY_PERIOD_2 = "SELECT * FROM excommunicatingcards WHERE period=2 AND id=";
    private final String QUERY_PERIOD_3 = "SELECT * FROM excommunicatingcards WHERE period=3 AND id=";
    private ConnectionDB connectionDB;
    private List<Effect> excomEffectList;
    private Map<Integer,List<AbstractPlayer>> excomPlayerMap;

    public Excommunication(){
        connectionDB = new ConnectionDB();
        excomEffectList = new ArrayList<>();
        excomPlayerMap = new HashMap<>();
        excomPlayerMap.forEach(((integer, abstractPlayers) -> abstractPlayers = new ArrayList<>()));
        //prelameCardFirstPeriodFromDB();
        //prelameCardSecondPeriodFromDB();
        //prelameCardThirdPeriodFromDB();
    }

    /**
     * metodo che mi preleva una carta a caso nella tabella del primo periodo,
     * le carte in ciascuna tabella sono univoche per id
     * iniziale che va da 1 a 7.
     */
    private void prelameCardFirstPeriodFromDB() {
        Random rand = new Random();
        ResultSet rs = connectionDB.executeQuery(QUERY_PERIOD_1 + (rand.nextInt(5)+1));
        try {
            rs.next();
            String codEffect = rs.getString("effect_code");
            excomEffectList.add(ExcommunicatingEffectCreator.createInstanceFirstPeriod(codEffect));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * metodo che mi preleva una carta a caso nella tabella del secondo periodo,
     * le carte in ciascuna tabella sono univoche per id
     * iniziale che va da 1 a 7.
     */
    private void prelameCardSecondPeriodFromDB() {
        Random rand = new Random();
        ResultSet rs = connectionDB.executeQuery(QUERY_PERIOD_2 + (rand.nextInt(3)+1));
        try {
            rs.next();
            String codEffect = rs.getString("effect_code");
            excomEffectList.add(ExcommunicatingEffectCreator.createInstanceSecondPeriod(codEffect));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * metodo che mi preleva una carta a caso nella tabella del terzo periodo,
     * le carte in ciascuna tabella sono univoche per id
     * iniziale che va da 1 a 7.
     */
    private void prelameCardThirdPeriodFromDB() {
        Random rand = new Random();
        ResultSet rs = connectionDB.executeQuery(QUERY_PERIOD_3 + (rand.nextInt(5)+1));
        try {
            rs.next();
            String codEffect = rs.getString("effect_code");
            excomEffectList.add(ExcommunicatingEffectCreator.createInstanceThirdPeriod(codEffect));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * mi attiva la scomunica al giocatore passato come parametro solo se risulta essere
     * nella lista degli scomunicati per il periodo 1
     * @param player giocatore
     * @throws RemoteException
     * @throws NewActionException
     */
    public void activeFirtsPeriod(AbstractPlayer player) throws RemoteException, NewActionException {
        if (excomPlayerMap.get(0).contains(player)){
            excomEffectList.get(0).active(player);
        }
    }

    /**
     * mi attiva la scomunica al giocatore passato come parametro solo se risulta essere
     * nella lista degli scomunicati per il periodo 2
     * @param player giocatore
     * @throws RemoteException
     * @throws NewActionException
     */
    public void activeSecondPeriod(AbstractPlayer player) throws RemoteException, NewActionException {
        if (excomPlayerMap.get(1).contains(player)){
            excomEffectList.get(1).active(player);
        }
    }

    /**
     * mi attiva la scomunica al giocatore passato come parametro solo se risulta essere
     * nella lista degli scomunicati per il periodo 3
     * @param player giocatore
     * @throws RemoteException
     * @throws NewActionException
     */
    public void activeThirdPeriod(AbstractPlayer player) throws RemoteException, NewActionException {
        if (excomPlayerMap.get(2).contains(player)){
            excomEffectList.get(2).active(player);
        }
    }

    /**
     * mi scomunica il giocatore passato come parametro
     * @param period periodo della scomunica
     * @param player giocatore da scomunicare
     */
    public void addPlayer(int period, AbstractPlayer player){
        excomPlayerMap.get(period-1).add(player);
    }

    public List<Effect> getExcomCardList() {
        return excomEffectList;
    }

    /**
     * dato il periodo mi ritorna l'effetto scomunica relativo
     * @param period
     * @return
     */
    public Effect getExcommunicatingEffect(int period){
        return excomEffectList.get(period-1);
    }

}
