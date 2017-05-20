package main.model.board;

import main.model.effects.excommunicating_effects.ExcommunicatingEffectCreator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * @author Luca
 * @author Andrea
 *
 * rappresenta il mazzo delle tessere scomunica, esse vengono prese dal db
 * dove sono divise in tabelle, in base al periodo di appartenenza, poichè
 * per la partia ne serviranno solo una per periodo, la classe mi estrae un
 * numero casuale per tabella e me la crea.
 */
public class ExcommunicatingDeck {
    private final String QUERY_PERIOD_1 = "SELECT FROM excommunicatingcards WHERE period=1 AND id=";
    private final String QUERY_PERIOD_2 = "SELECT FROM excommunicatingcards WHERE period=2 AND id=";
    private final String QUERY_PERIOD_3 = "SELECT FROM excommunicatingcards WHERE period=3 AND id=";
    private ConnectionDB connectionDB;
    private List<ExcommunicatingCard> excomCardList;

    public ExcommunicatingDeck(){
        connectionDB = new ConnectionDB();
        prelameCardFirtPeriodFromDB();
        prelameCardSecondPeriodFromDB();
        prelameCardThirdPeriodFromDB();
    }

    /**
     * metodo che mi preleva una carta a caso nella tabella del primo periodo,
     * le carte in ciascuna tabella sono univoche per id
     * iniziale che va da 1 a 7.
     */
    private void prelameCardFirtPeriodFromDB() {
        Random rand = new Random();
        ResultSet rs = connectionDB.executeQuery(QUERY_PERIOD_1 + rand.nextInt(7));
        try {
            String codEffect = rs.getString("effect_code");
            excomCardList.add(new ExcommunicatingCard(ExcommunicatingEffectCreator
                                                        .createInstanceFirstPeriod(codEffect)));
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
        ResultSet rs = connectionDB.executeQuery(QUERY_PERIOD_2 + rand.nextInt(7));
        try {
            String codEffect = rs.getString("effect_code");
            excomCardList.add(new ExcommunicatingCard(ExcommunicatingEffectCreator
                    .createInstanceSecondPeriod(codEffect)));
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
        ResultSet rs = connectionDB.executeQuery(QUERY_PERIOD_3 + rand.nextInt(7));
        try {
            String codEffect = rs.getString("effect_code");
            excomCardList.add(new ExcommunicatingCard(ExcommunicatingEffectCreator
                    .createInstanceThirdPeriod(codEffect)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ExcommunicatingCard> getExcomCardList() {
        return excomCardList;
    }
}
