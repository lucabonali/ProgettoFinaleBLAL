package main.CLI;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe che gestisce la disposizione delle carte sulle torri nella CLI
 * @author Andrea
 * @author Luca
 */
class CLICards {
    private Map<String,String> territoryCardList;
    private Map<String,String> buildingsCardList;
    private Map<String,String> characterCardList ;
    private Map<String,String> venturesCardList;
    private Map<Integer,Integer> actionSpaceValue;

    private Map<String, String> excomCards;

    public CLICards() {
        territoryCardList = new HashMap<>();
        buildingsCardList = new HashMap<>();
        characterCardList = new HashMap<>();
        venturesCardList = new HashMap<>();
        initializeTerritories();
        initializeBuildings();
        initializeCharacters();
        initializeVentures();
        initializeActionSpaceValue();
        initializeExcomCards();
    }



    private void initializeExcomCards() {
        excomCards = new HashMap();
        excomCards.put("11","- every time you earn military points, you earn 1 less");
        excomCards.put("12","every time you earn coins, you earn 1 less");
        excomCards.put("13","every time you earn servents, you earn 1 less");
        excomCards.put("14"," -1 to all dices rolls");
        excomCards.put("15"," -3 to the all harvest actions");
        excomCards.put("16"," -3 to all production actions");
        excomCards.put("21"," -4 to all buildings actions");
        excomCards.put("22"," -4 to all territories actions ");
        excomCards.put("23"," -4 to all ventures actions");
        excomCards.put("24"," -4 to all characters actions");
        excomCards.put("31"," you won't earn any victory points from territores cards");
        excomCards.put("32"," you won't earn any victory points from ventures cards");
        excomCards.put("33"," you won't earn any victory points from characters cards");
        excomCards.put("34"," every 5 victory points earned you will lose 1");
        excomCards.put("35"," every military points you have earned will costs you -1 victory points");
        excomCards.put("36"," for each physical resource you have you will lose 1 victory point");
    }

    private void initializeActionSpaceValue() {
        actionSpaceValue = new HashMap<>();
        actionSpaceValue.put(0,7);
        actionSpaceValue.put(1,5);
        actionSpaceValue.put(2,3);
        actionSpaceValue.put(3,1);
        actionSpaceValue.put(4,7);
        actionSpaceValue.put(5,5);
        actionSpaceValue.put(6,3);
        actionSpaceValue.put(7,1);
        actionSpaceValue.put(8,7);
        actionSpaceValue.put(9,5);
        actionSpaceValue.put(10,3);
        actionSpaceValue.put(11,1);
        actionSpaceValue.put(12,7);
        actionSpaceValue.put(13,5);
        actionSpaceValue.put(14,3);
        actionSpaceValue.put(15,1);
    }

    private void initializeVentures() {
        venturesCardList.put("costruire_le_mura","-3 stones -- +2 military +1 privilege -- end 3 victory");
        venturesCardList.put("riparare_la_chiesa","-1 coin -1 stone -1 wood -- +1 faith -- end 5 victory");
        venturesCardList.put("sostegno_al_vescovo","-2 military -- +3 faith -- end 1 victory");
        venturesCardList.put("ospitare_i_mendicanti","-3 woods -- +4 servants -- end 4 victory");
        venturesCardList.put("campagna_militare","-2 military -- +3 coins -- end 5 victory");
        venturesCardList.put("innalzare_una_statua","-2 woods -2 stone -- +2 different privileges -- end 4 victory");
        venturesCardList.put("ingaggiare_reclute","-4 coins -- +5 military -- end 4 victory");
        venturesCardList.put("combattere_le_eresie","-3 military -- +2 faith -- end 5 victory");
    }

    private void initializeCharacters() {
        characterCardList.put("predicatore","-2 coins -- +4 faith --");
        characterCardList.put("artigiano","-3 coins -- -- +2 production ");
        characterCardList.put("cavaliere","-2 coins -- 1 privilege -- +2 venture action");
        characterCardList.put("badessa","-3 coins -- 4 action tower + 1 faith -- ");
        characterCardList.put("condottiero","-2 coins -- +3 military -- +2 territory action");
        characterCardList.put("dama","-4 coins -- -- +2 character action");
        characterCardList.put("contadino","-3 coins -- -- +2 harvest action");
        characterCardList.put("costruttore","-4 coins -- -- +2 buildings");
    }

    private void initializeBuildings() {
        buildingsCardList.put("esattoria","-3 woods -1 stone -- 5 victory -- 5 production (1 coin for each territory)");
        buildingsCardList.put("falegnameria","-1 coins - 2 woods -- +3 victory -- 4 production (1 wood into 3 coins)");
        buildingsCardList.put("teatro","-2 coins -2 woods -- +6 victory -- 6 production (1 victory for each character)");
        buildingsCardList.put("tagliapietre","-1 coin -2 stones -- +2 victory -- 3 production (1 stone into 3 coins)");
        buildingsCardList.put("cappella","-2 woods -- +1 faith -- 2 production (1 coin into 1 faith)");
        buildingsCardList.put("residenza","-2 stones -- +1 victory -- 1 production (1 coin into 1 privilege)");
        buildingsCardList.put("zecca","-1 wood -3 stones -- +5 victory -- 5 production (1 coin for each building)");
        buildingsCardList.put("arco_di_trionfo","-2 coins -2 stones -- +6 victory -- 6 production (1 victory for each venture)");
    }

    private void initializeTerritories() {
        territoryCardList.put("monastero", "Free -- + 2 military, 1 servants -- 6 harvest 1 faith 1 stone");
        territoryCardList.put("citta", "Free -- +3 coins -- 6 harvest 1 privilege ");
        territoryCardList.put("foresta","Free -- +1 wood -- 5 harvest 3 woods ");
        territoryCardList.put("borgo", "Free -- 3 harvest 1 coins 1 servant");
        territoryCardList.put("cava_di_ghiaia", "Free -- +2 stones -- 4 harvest 2 stones");
        territoryCardList.put("bosco", "Free -- +1 wood -- 2 harvest 1 wood");
        territoryCardList.put("rocca", "Free -- 5 harvest 2 military 1 stone");
        territoryCardList.put("avamposto_commerciale", "Free -- 1 harvest 1 coin");
    }

    public Map<String, String> getTerritoryCardList() {
        return territoryCardList;
    }

    public Map<String, String> getBuildingsCardList() {
        return buildingsCardList;
    }

    public Map<String, String> getCharacterCardList() {
        return characterCardList;
    }

    public Map<String, String> getVenturesCardList() {
        return venturesCardList;
    }

    public int getActionSpaceValue(int i) {
        return actionSpaceValue.get(i);
    }

    public String getExcomCards(String code) {
        return excomCards.get(code);
    }

}
