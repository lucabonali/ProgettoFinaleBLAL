package main.model.board;

import main.model.effects.Effect;
import main.model.effects.EffectsCreator;
import main.model.effects.VariableIncrementEffect;
import main.model.fields.Field;
import main.model.fields.Resource;
import main.api.types.CardType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static main.api.types.CardType.TERRITORY;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica il mazzo di carte sviluppo, essa si occupa della
 * creazione delle carte attraverso l'interrogazione al db, grazie alla classe
 * ConnectionDB, e mi genera la lista ordinata in maniera corretta di tutte
 * le carte.
 */

public class Deck {
    //query che uso sempre per interrogare il db
    private static final String QUERY_TERRITORY = "SELECT * FROM cards WHERE type = \"territories\"";
    private static final String QUERY_CHARACTERS = "SELECT * FROM cards WHERE type = \"characters\"";
    private static final String QUERY_BUILDINGS = "SELECT * FROM cards WHERE type = \"buildings\"";
    private static final String QUERY_ENTERPRISES = "SELECT * FROM cards WHERE type = \"ventures\"";

    private ConnectionDB connectionDB;

    //saranno tutte liste di 24 carte ciascuna
    private List<Card> territoriesList;
    private List<Card> charactersList;
    private List<Card> buildingsList;
    private List<Card> venturesList;


    public Deck() {
        connectionDB = new ConnectionDB();
//        territoriesList = createTerritoriesList();
//        charactersList = createCharactersList();
//        buildingsList = createBuildingList();
//        venturesList = createEnterprisesList();
    }

    /**
     * metodo che mi ritorna una lista di carte in funzione del periodo
     * e del turno nel periodo stesso
     * @param period periodo cmopreso fra [1,3]
     * @param turn turno compreso fra [1,2]
     * @return lista di carte
     */
    public List<Card> drawCards(int period, int turn, CardType type) {
        int initPos=0, finalpos=0;
        switch (period) {
            case 1:
                initPos=0;
                break;
            case 2:
                initPos=8;
                break;
            case 3:
                initPos=16;
                break;
        }
        finalpos = initPos+4;
        if(turn == 2) {
            initPos += 4;
            finalpos += 4;
        }
        List<Card> list = new ArrayList<>();
        switch (type.getCode()) {
            case "TERRITORY":
                for (int cont = initPos; cont < finalpos; cont++) {
                list.add(territoriesList.get(cont));
                }
                break;
            case "CHARACTER":
                for (int cont = initPos; cont < finalpos; cont++) {
                list.add(charactersList.get(cont));
                }
                break;
            case "BUILDING":
                for (int cont = initPos; cont < finalpos; cont++) {
                    list.add(buildingsList.get(cont));
                }
                break;
            case "VENTURES":
                for (int cont = initPos; cont < finalpos; cont++) {
                    list.add(venturesList.get(cont));
                }
                break;
        }
        return list;
    }

    /**
     * mi crea la lista ordinata per periodi dei territori
     * @return la lista
     */
    private List<Card> createTerritoriesList() {
        ResultSet rs = connectionDB.executeQuery(QUERY_TERRITORY);
        return createCardList(TERRITORY, rs);
    }

    /**
     * crea la lista dei personaggi ordinata per periodi
     * @return la lista
     */
    private List<Card> createCharactersList() {
        ResultSet rs = connectionDB.executeQuery(QUERY_CHARACTERS);
        return createCardList(CardType.CHARACTER, rs);
    }

    /**
     * crea la lista degli edifici ordinati per periodo
     * @return la lista
     */
    private List<Card> createBuildingList() {
        ResultSet rs = connectionDB.executeQuery(QUERY_BUILDINGS);
        return createCardList(CardType.BUILDING, rs);
    }

    /**
     * crea la lista delle imprese ordinata per periodi
     * @return la lista
     */
    private List<Card> createEnterprisesList() {
        ResultSet rs = connectionDB.executeQuery(QUERY_ENTERPRISES);
        return createCardList(CardType.VENTURES, rs);
    }

    private List<Card> createCardList(CardType type, ResultSet rs) {
        List<Card> list = new ArrayList<>();
        try {
            while(rs.next()) {
                int period = rs.getInt("period");
                String name = rs.getString("name");
                String codcost = rs.getString("cost");
                String codQuickEffect = rs.getString("quick_effect");
                String codPermanentEffect = rs.getString("permanent_effect");
                //creo la carta
                Card card = new Card(type, name, createCostsList(codcost),
                        createQuickEffectList(codQuickEffect), createPermanentEffectList(codPermanentEffect),
                        period);
                list.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shuffle(list);
    }

    private List<Field> createCostsList(String cod) {
        //creo la lista degli effetti immediati
        List<Field> costsList = new ArrayList<>();
        if(cod == null){
            return null;
        }
        costsList.add(Resource.createResource(cod.substring(0,2), true));
        if (cod.length() == 4) {
            costsList.add(Resource.createResource(cod.substring(2,4), true));
        }
        if (cod.length() == 6) {
            costsList.add(Resource.createResource(cod.substring(4,6), true));
        }
        return costsList;
    }

    private List<Effect> createQuickEffectList(String cod) {
        //creo la lista degli effetti immediati
        List<Effect> effectList = new ArrayList<>();
        if (cod.charAt(1) == 'g') {
            effectList.add(VariableIncrementEffect.createInstance(cod.substring(1)));
            return effectList;
        }
        effectList.add(EffectsCreator.createEffect(cod.substring(1,3)));
        if (cod.length() == 5) {
            effectList.add(EffectsCreator.createEffect(cod.substring(3,5)));
        }
        return effectList;
    }

    private List<Effect> createPermanentEffectList(String cod) {
        //creo la lista degli effetti permanenti
        List<Effect> permanentEffectList = new ArrayList<>();
        permanentEffectList.add(EffectsCreator.createEffect(cod.substring(0,2)));
        if (cod.length() == 4) {
            permanentEffectList.add(EffectsCreator.createEffect(cod.substring(2,4)));
        }
        return permanentEffectList;
    }

    /**
     * metodo che mi mischia la lista delle carte in maniera casuale
     * @param list lista da mischiare
     * @return la lista mischiata
     */
    private List<Card> shuffle(List<Card> list) {
        List<Card> shuffledList = new ArrayList<>();
        //da implementare
        return shuffledList;
    }
}
