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
    private List<Card> territoriesList; //gli effetti permanenti verranno attivati solo dopo azione raccolta
    private List<Card> buildingsList; //gli effetti permanenti verranno attivati solo dopo azione produzione
    private List<Card> charactersList; //gli effetti permanenti saranno attivati su ogni azione
    private List<Card> venturesList; //gli effetti permanenti vengono attivati solo alla fine della partita

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

    //Metodi Getter e setter da mettere
    public Action getCurrentAction() {
        return currentAction;
    }

    private void initializeCardsLists() {
        territoriesList = new ArrayList<>();
        buildingsList = new ArrayList<>();
        charactersList = new ArrayList<>();
        venturesList = new ArrayList<>();
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

    public List<Card> getCardsList(CardType cardType){
        switch (cardType){
            case BUILDING:
                return buildingsList;
            case CHARACTER:
                return charactersList;
            case TERRITORY:
                return territoriesList;
            default: //ventures
                return venturesList;
        }
    }

    /**
     * metodo che mi rimuove dal tabellone tutti i miei familiari
     */
    public void removeAllFamilyMembers() {
        familyMemberList.forEach(((familyMemberType, familyMember) -> familyMember.setPositioned(false)));
    }

    /**
     * viene richiamato da Effect e modifica la risorsa passata come parametro
     * @param effectResource
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
        if(resourceList.get(cost.getType()).getQta() < cost.getQta())
            return false;
        return true;
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
//        List<Integer> qtaResourcesList = new ArrayList<>();
//        for(Resource res : resourceList.values()) {
//            qtaResourcesList.add(res.getQta());
//        }
        return qtaResourceMap;
    }

    public void activeTerritoriesEffects(Action action) throws RemoteException, NewActionException {
        this.currentAction = action;
        for (Card card : territoriesList) {
            card.activePermanentEffects();
        }
    }

    public void activeBuildingsEffects(Action action) throws RemoteException, NewActionException {
        this.currentAction = action;
        for (Card card : buildingsList) {
            card.activePermanentEffects();
        }
    }

    public void activeCharacterEffects(Action action) throws RemoteException, NewActionException {
        this.currentAction = action;
        for (Card card : charactersList) {
            card.activePermanentEffects();
        }
    }

    private void activeVentueresEffects() throws RemoteException, NewActionException {
        for (Card card : venturesList){
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
        switch (charactersList.size()){
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
        switch (territoriesList.size()){
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
