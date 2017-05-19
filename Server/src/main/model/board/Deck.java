package main.model.board;

import main.model.effects.*;
import main.model.fields.Field;
import main.model.fields.Resource;
import main.api.types.CardType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    /**
     * Metodo che prende in ingresso il risultato dell' interrogazione sul database e il tipo della carta e crea
     * la lista di carte corrispondente al tipo
     * @param type della carta
     * @param rs risultato integgogazione db
     * @return lista carte
     */
    private List<Card> createCardList(CardType type, ResultSet rs) {
        Card card=null;
        List<Card> list = new ArrayList<>();
        try {
            while(rs.next()) {
                int period = rs.getInt("period");
                String name = rs.getString("name");
                String codcost = rs.getString("cost");
                String codQuickEffect = rs.getString("quick_effect");
                String codPermanentEffect = rs.getString("permanent_effect");
                //creo la carta
                switch(type){
                    case TERRITORY:
                        card = new Card(type, name, createCostsList(codcost),
                                createQuickEffectListTB(codQuickEffect), createPermanentEffectListTerritory(codPermanentEffect),
                                period);
                    case BUILDING:
                        card = new Card(type, name, createCostsList(codcost),
                                createQuickEffectListTB(codQuickEffect), createPermanentEffectListBuildings(codPermanentEffect),
                                period);
                        break;
                    case VENTURES:
                        card = new Card(type, name, createCostsList(codcost),
                                createQuickEffectListVC(codQuickEffect), createPermanentEffectListVentures(codPermanentEffect),
                                period);
                        break;
                    case CHARACTER:
                        card = new Card(type, name, createCostsList(codcost),
                                createQuickEffectListVC(codQuickEffect), createPermanentEffectListCharacters(codPermanentEffect),
                                period);
                        break;
                    default:
                        break;
                }
                list.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shuffle(list);
    }


    /**
     * Crea fli effetti delle carte di tipo Personaggio
     * @param cod
     * @return
     */
    private List<Effect> createPermanentEffectListCharacters(String cod) {
        List<Effect> permanentEffectList = new ArrayList<>();
        if(cod != null){
            permanentEffectList.add(ActionValueIncrementEffect.createInstance(cod.substring(0,2)));
            return permanentEffectList;
        }
        return null;
    }

    /**
     * Crea gli effetti delle carte di tipo Imprese
     * @param cod
     * @return
     */
    private List<Effect> createPermanentEffectListVentures(String cod) {
        List<Effect> permanentEffectList = new ArrayList<>();
        if(cod != null){
            permanentEffectList.add(FixedIncrementEffect.createInstance(cod.substring(0,2)));
            return permanentEffectList;
        }
        return null;
    }

    /**
     * crea la lista degli effetti delle carte di tipo edificio
     * @param cod
     * @return
     */
    private List<Effect> createPermanentEffectListBuildings(String cod) {
        List<Effect> permanentEffectList = new ArrayList<>();
        if(cod != null){
            if(cod.charAt(1) == 'g'){
                permanentEffectList.add(AreaActivationEffect.createInstance(cod));
                return permanentEffectList;
            }
            if(cod.contains("_")) {
                String increment = cod.split("_")[0];
                String decrement = cod.split("_")[1];
                permanentEffectList.add(AreaActivationEffect.createInstance(increment,decrement));
                return permanentEffectList;
            }
            permanentEffectList.add(AreaActivationEffect.createInstance(cod.charAt(0),cod.substring(1,3) ));
            if (cod.length() == 5 ) {
                permanentEffectList.add(AreaActivationEffect.createInstance(cod.charAt(0),cod.substring(3,5)));
            }
            if (cod.length() == 7) {
                permanentEffectList.add(AreaActivationEffect.createInstance(cod.charAt(0),cod.substring(5,7)));
            }
            return permanentEffectList;
        }
        return null;
    }

    /**
     * Crea la lista degli effetti delle carte di tipo territorio
     * @param cod
     * @return
     */

    private List<Effect> createPermanentEffectListTerritory(String cod) {
        List<Effect> permanentEffectList = new ArrayList<>();
        if(cod != null){
            permanentEffectList.add(AreaActivationEffect.createInstance(cod.charAt(0),cod.substring(1,3)));
            if (cod.length() == 5) {
                permanentEffectList.add(AreaActivationEffect.createInstance(cod.charAt(0),cod.substring(3,5)));
            }
            if (cod.length() == 7) {
                permanentEffectList.add(AreaActivationEffect.createInstance(cod.charAt(0),cod.substring(5,7)));
            }
            return permanentEffectList;
        }
        return null;
    }

    /**
     * Da qui in poi metodi che creano gli effetti dei tipo rapido
     * @param cod
     * @return
     */

    private List<Effect> createQuickEffectListVC(String cod) {
        List<Effect> effectList = new ArrayList<>();
        if(cod != null){
            if (cod.charAt(0) == 'g') {
                effectList.add(VariableIncrementEffect.createInstance(cod));
                return effectList;
            }
            effectList.add(EffectsCreator.createEffect(cod.substring(0, 2)));
            if (cod.length() == 4) {
                effectList.add(EffectsCreator.createEffect(cod.substring(2, 4)));
            }
            if (cod.length() == 6) {
                effectList.add(EffectsCreator.createEffect(cod.substring(2, 4)));
            }
            return effectList;
        }
        return null;
    }

    private List<Effect> createQuickEffectListTB(String cod) {
        List<Effect> effectList = new ArrayList<>();
        if(cod != null) {
            effectList.add(FixedIncrementEffect.createInstance(cod.substring(0, 2)));
            if (cod.length() == 4) {
                effectList.add(FixedIncrementEffect.createInstance(cod.substring(2, 4)));
            }
            return effectList;
        }
        return null;
    }


    /**
     * crea la lista dei costi di ogni carta, metodo chiamato dal metodo createCardList
     * @param cod
     * @return
     */
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

    /**
     * metodo che mi mischia la lista delle carte in maniera casuale
     * @param list lista da mischiare
     * @return la lista mischiata
     */
    private List<Card> shuffle(List<Card> list) {
        List<Card> shuffledList1 = new ArrayList<>();
        List<Card> shuffledList2 = new ArrayList<>();
        List<Card> shuffledList3 = new ArrayList<>();

        for(Card c : list){
            if(c.getPeriod() == 1){
                shuffledList1.add(c);
            }
            else if(c.getPeriod()==2){
                shuffledList2.add(c);
            }
            else{
                shuffledList3.add(c);
            }
        }
        Collections.shuffle(shuffledList1);
        Collections.shuffle(shuffledList2);
        Collections.shuffle(shuffledList3);

        list = new ArrayList<>();
        list.addAll(shuffledList1);
        list.addAll(shuffledList2);
        list.addAll(shuffledList3);

        return list;
    }
}
