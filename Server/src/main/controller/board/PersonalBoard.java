package main.controller.board;

import main.api.exceptions.NewActionException;
import main.api.types.CardType;
import main.api.types.FamilyMemberType;
import main.api.types.ResourceType;
import main.controller.actionSpaces.Action;
import main.controller.effects.ExcomEffect;
import main.controller.fields.Field;
import main.controller.fields.Resource;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.api.types.FamilyMemberType.*;
import static main.api.types.ResourceType.*;

/**
 * @author Luca
 * @author Andrea
 *
 * rappresenta la plancia personale del singolo giocatore
 */
public class PersonalBoard {
    //lista dei familiari in possesso, uno neutro e tre personali
    private Map<FamilyMemberType,FamilyMember> familyMemberList;

    // WOOD , STONE , SERVANTS , COINS , VICTORY , FAITH , MILITARY
    //private List<Resource> resourceList;
    private Map<ResourceType, Resource> resourceList;

    //liste delle carte in possesso, al massimo 6 per tipo
    private Map<CardType, List<Card>> cardsMap;
    //private List<Card> territoriesList; //gli effetti permanenti verranno attivati solo dopo azione raccolta
    //private List<Card> buildingsList; //gli effetti permanenti verranno attivati solo dopo azione produzione
    //private List<Card> charactersList; //gli effetti permanenti saranno attivati su ogni azione
    //private List<Card> venturesList; //gli effetti permanenti vengono attivati solo alla fine della partita

    //lista degli effetti ottenuti in seguito a scomuniche
    private List<ExcomEffect> excomEffectList; //vengono attivati ogni azione

    //id del giocatore e quindi della plancia
    private int id;

    //azione corrente
    private Action currentAction;
    /**
     *
     * @param id identifica il giocatore in ordine di connessione
     */
    public PersonalBoard(int id){
        this.id = id;
        initializeResources();
        initializeFamilyMembers();
        initializeCardsLists();
    }


    public Action getCurrentAction() {
        return currentAction;
    }

    //rimuovere
    public void setCurrentAction(Action action) {
        this.currentAction = action;
    }

    private void initializeCardsLists() {
        cardsMap = new HashMap<>();
        cardsMap.put(CardType.TERRITORY, new ArrayList<>());
        cardsMap.put(CardType.CHARACTER, new ArrayList<>());
        cardsMap.put(CardType.BUILDING, new ArrayList<>());
        cardsMap.put(CardType.VENTURES, new ArrayList<>());
    }

    private void initializeFamilyMembers() {
        familyMemberList = new HashMap<>();
        familyMemberList.put(ORANGE_DICE, new FamilyMember(this, ORANGE_DICE));
        familyMemberList.put(WHITE_DICE, new FamilyMember(this, WHITE_DICE));
        familyMemberList.put(BLACK_DICE, new FamilyMember(this, BLACK_DICE));
        familyMemberList.put(NEUTRAL_DICE, new FamilyMember(this, NEUTRAL_DICE));
    }

    private void initializeResources(){
        //resourceList = new ArrayList<>();
        resourceList = new HashMap<>();
        resourceList.put(WOOD, new Resource(2, WOOD));
        resourceList.put(ResourceType.STONE, new Resource(2, ResourceType.STONE));
        resourceList.put(ResourceType.SERVANTS, new Resource(3, ResourceType.SERVANTS));
        int qta = 4;
        resourceList.put(ResourceType.COINS, new Resource(qta+id, ResourceType.COINS));
        resourceList.put(ResourceType.VICTORY, new Resource(0, ResourceType.VICTORY));
        resourceList.put(ResourceType.FAITH, new Resource(0, ResourceType.FAITH));
        resourceList.put(ResourceType.MILITARY, new Resource(0, ResourceType.MILITARY));
    }

    /**
     * metodo che mi ritorna la lista di carte in possesso del tipo passato
     * come parametro
     * @param cardType tipo di carte che voglio
     * @return la lista corretta
     */
    public List<Card> getCardsList(CardType cardType){
        return cardsMap.get(cardType);
    }

    /**
     * mi aggiunge la carta alla lista delle mie carte
     * @param card carta da aggiungere
     */
    public void addCard(Card card) {
        cardsMap.get(card.getType()).add(card);
    }

    /**
     * metodo che mi rimuove dal tabellone tutti i miei familiari
     */
    public void removeAllFamilyMembers() {
        familyMemberList.forEach(((familyMemberType, familyMember) -> familyMember.setPositioned(false)));
    }

    /**
     * viene richiamato da Effect e modifica la risorsa passata come parametro
     * @param effectResource mi indica la variazione dell'effetto
     */
    public void modifyResources(Field effectResource){
        resourceList.get(effectResource.getType()).modify(effectResource);
    }

    /**
     * mi controlla se ho le risorse necessarie
     * @param cost risorsa da verificare
     * @return true se le ho, false altrimenti
     */
    public boolean checkResources(Field cost){
        return resourceList.get(cost.getType()).getQta() >= Math.abs(cost.getQta());
    }

    /**
     * mi ritona il familiare del tipo passato come paramentro
     * @param type tipo del familaire
     * @return il familiare corretto
     */
    public FamilyMember getFamilyMember(FamilyMemberType type){
        return familyMemberList.get(type);
    }

    /**
     * metodo che mi ritorna la lista delle qta delle mie risorse
     * @return Lista delle quantità
     */
    public Map<ResourceType,Integer> getQtaResources() {
        Map<ResourceType,Integer> qtaResourceMap = new HashMap<>();
        resourceList.forEach(((resourceType, resource) -> {
            qtaResourceMap.put(resourceType, resource.getQta());
        }));
        return qtaResourceMap;
    }

    public void activeTerritoriesEffects(Action action) throws RemoteException, NewActionException {
        this.currentAction = action;
        cardsMap.get(CardType.TERRITORY).get(0).activePermanentEffects();
//        for (Card card : cardsMap.get(CardType.TERRITORY)) {
//            card.activePermanentEffects();
//        }
    }

    public void activeBuildingsEffects(Action action) throws RemoteException, NewActionException {
        this.currentAction = action;
        for (Card card : cardsMap.get(CardType.BUILDING)) {
            card.activePermanentEffects();
        }
    }

    public void activeCharacterEffects(Action action) throws RemoteException, NewActionException {
        this.currentAction = action;
        for (Card card : cardsMap.get(CardType.CHARACTER)) {
            card.activePermanentEffects();
        }
    }

    private void activeVentueresEffects() throws RemoteException, NewActionException {
        for (Card card : cardsMap.get(CardType.VENTURES)){
            card.activePermanentEffects();
        }
    }

    public void setDiceValues(int orange , int white, int black){
        familyMemberList.get(ORANGE_DICE).setValue(orange);
        familyMemberList.get(WHITE_DICE).setValue(white);
        familyMemberList.get(BLACK_DICE).setValue(black);
        familyMemberList.get(NEUTRAL_DICE).setValue(0);
    }

    public int calculateVictoryPoints() throws RemoteException, NewActionException {
        activeVentueresEffects();
        int sum = resourceList.get(ResourceType.VICTORY).getQta(); //aggiungo i punti vittoria
        //adesso devo convertire gli altri
        int sumResources = 0;
        //conto le risorse totali
        sumResources += resourceList.get(ResourceType.WOOD).getQta();
        sumResources += resourceList.get(ResourceType.STONE).getQta();
        sumResources += resourceList.get(ResourceType.SERVANTS).getQta();
        sumResources += resourceList.get(ResourceType.COINS).getQta();
        sumResources = sumResources/5;
        sum += sumResources;
        sum += calcNumOfCharacters();
        sum += calcNumOfTerritories();
        return sum;
    }

    private int calcNumOfCharacters(){
        int tmp = 0;
        switch (cardsMap.get(CardType.CHARACTER).size()){
            case 1:
                tmp += 1;
                break;
            case 2:
                tmp += 3;
                break;
            case 3:
                tmp += 6;
                break;
            case 4:
                tmp += 10;
                break;
            case 5:
                tmp += 15;
                break;
            case 6:
                tmp += 21;
                break;
            default:
                break;
        }
        return tmp;
    }

    private int calcNumOfTerritories(){
        int tmp = 0;
        switch (cardsMap.get(CardType.TERRITORY).size()){
            case 3:
                tmp += 1;
                break;
            case 4:
                tmp += 4;
                break;
            case 5:
                tmp += 10;
                break;
            case 6:
                tmp += 20;
                break;
            default:
                break;
        }
        return tmp;
    }

}
