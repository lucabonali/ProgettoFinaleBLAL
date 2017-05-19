package main.model.board;

import main.model.effects.Effect;
import main.model.effects.EffectsCreator;

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
public class ExcomCardDeck {
    private final String QUERY_PERIOD_1 = "SELECT FROM ... WHERE id=";
    private final String QUERY_PERIOD_2 = "";
    private final String QUERY_PERIOD_3 = "";
    private ConnectionDB connectionDB;
    List<ExcomCard> excomCardList;

    public ExcomCardDeck(){
        connectionDB = new ConnectionDB();
//        prelameCard1FromDB();
//        prelameCard2FromDB();
//        prelameCard3FromDB();
    }

    /**
     * metodo che mi preleva una carta a caso nella tabella del primo periodo,
     * le carte in ciascuna tabella sono univoche per id
     * iniziale che va da 1 a 7.
     */
    private void prelameCard1FromDB() {
        Random rand = new Random();
        ResultSet rs = connectionDB.executeQuery(QUERY_PERIOD_1 + rand.nextInt(7));
        addCardToList(rs);
    }

    /**
     * metodo che mi preleva una carta a caso nella tabella del secondo periodo,
     * le carte in ciascuna tabella sono univoche per id
     * iniziale che va da 1 a 7.
     */
    private void prelameCard2FromDB() {
        Random rand = new Random();
        ResultSet rs = connectionDB.executeQuery(QUERY_PERIOD_2 + rand.nextInt(7));
        addCardToList(rs);
    }

    /**
     * metodo che mi preleva una carta a caso nella tabella del terzo periodo,
     * le carte in ciascuna tabella sono univoche per id
     * iniziale che va da 1 a 7.
     */
    private void prelameCard3FromDB() {
        Random rand = new Random();
        ResultSet rs = connectionDB.executeQuery(QUERY_PERIOD_3 + rand.nextInt(7));
        addCardToList(rs);
    }

    /**
     * metodo che dato un resultSet mi preleva i dati e mi crea la carta
     * e dopo questo la aggiunge alla lista
     * @param rs ResultSet
     */
    private void addCardToList(ResultSet rs) {
        try {
            String codEffect = rs.getString(1);
            Effect effect = EffectsCreator.createEffect(codEffect);
            excomCardList.add(new ExcomCard(effect));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class ExcomCard{
        private Effect effect;

        ExcomCard(Effect effect) {
            this.effect = effect;
        }
    }
}
