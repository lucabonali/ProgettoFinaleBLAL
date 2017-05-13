package board;

import effects.Effect;
import fields.Field;
import fields.Resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private static final String QUERY_TERRITORY = "";
    private static final String QUERY_CHARACTERS = "";
    private static final String QUERY_BUILDINGS = "";
    private static final String QUERY_ENTERPRISES = "";

    private ConnectionDB connectionDB;

    //saranno tutte liste di 24 carte ciascuna
    private List<Card> territoriesList;
    private List<Card> charactersList;
    private List<Card> buildingsList;
    private List<Card> enterprisesList;


    public Deck() {
        connectionDB = new ConnectionDB();
        territoriesList = createTerritoriesList();
        charactersList = createCharactersList();
        buildingsList = createBuildingList();
        enterprisesList = createEnterprisesList();
    }

    /**
     * metodo che mi ritorna una lista di carte in funzione del periodo
     * e del turno nel periodo stesso
     * @param period periodo cmopreso fra [1,3]
     * @param turn turno compreso fra [1,2]
     * @return lista di carte
     */
    public List<Card> drawCards(int period, int turn) {
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
        for(int cont=initPos; cont<finalpos; cont++) {
            list.add(territoriesList.get(cont));
        }
        for(int cont=initPos; cont<finalpos; cont++) {
            list.add(charactersList.get(cont));
        }
        for(int cont=initPos; cont<finalpos; cont++) {
            list.add(buildingsList.get(cont));
        }
        for(int cont=initPos; cont<finalpos; cont++) {
            list.add(enterprisesList.get(cont));
        }

        return list;
    }

    /**
     * mi crea la lista ordinata per periodi dei territori
     * @return la lista
     */
    private List<Card> createTerritoriesList() {
        ResultSet rs = connectionDB.executeQuery(QUERY_TERRITORY);
        return createCardList(CardType.TERRITORY, rs);
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
                String name = rs.getString(0);
                int period = rs.getInt(1);
                String codcost = rs.getString(2);
                String codQuickEffect = rs.getString(3);
                String codPermanentEffect = rs.getString(4);
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
        List<Field> playerFieldsList = new ArrayList<>();
        playerFieldsList.add(Resource.createResource(cod.substring(0,2)));
        if (cod.length() == 4) {
            playerFieldsList.add(Resource.createResource(cod.substring(2,4)));
        }
        return playerFieldsList;
    }

    private List<Effect> createQuickEffectList(String cod) {
        //creo la lista degli effetti immediati
        List<Effect> effectList = new ArrayList<>();
        effectList.add(Effect.createEffect(cod.substring(0,2)));
        if (cod.length() == 4) {
            effectList.add(Effect.createEffect(cod.substring(2,4)));
        }
        return effectList;
    }

    private List<Effect> createPermanentEffectList(String cod) {
        //creo la lista degli effetti permanenti
        List<Effect> permanentEffectList = new ArrayList<>();
        permanentEffectList.add(Effect.createEffect(cod.substring(0,2)));
        if (cod.length() == 4) {
            permanentEffectList.add(Effect.createEffect(cod.substring(2,4)));
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
