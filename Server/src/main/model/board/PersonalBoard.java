package main.model.board;

import main.api.types.CardType;
import main.api.types.FamilyMemberType;
import main.api.types.ResourceType;
import main.game_server.exceptions.NewActionException;
import main.model.action_spaces.Action;
import main.model.fields.Field;
import main.model.fields.Resource;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.api.types.FamilyMemberType.*;
import static main.api.types.ResourceType.WOOD;

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
    private Map<CardType, List<DevelopmentCard>> cardsMap;
    //id del giocatore e quindi della plancia
    private int id;
    //lista dei correnti effetti
    private Field currentField = null;
    //azione corrente
    private Action currentAction;

    /**
     * costruttore della classe
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

    public void setCurrentField(Field field) {
        this.currentField = field;
    }

    public Field getCurrentField() {
        return currentField;
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
    public List<DevelopmentCard> getCardsList(CardType cardType){
        return cardsMap.get(cardType);
    }


    /**
     * ritorna l'id della mia plancia
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * mi aggiunge la carta alla lista delle mie carte
     * @param DevelopmentCard carta da aggiungere
     */
    public void addCard(DevelopmentCard DevelopmentCard) {
        cardsMap.get(DevelopmentCard.getType()).add(DevelopmentCard);
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
     * metodo che mi resetta la risorsa del tipo passato come parametro
     * @param type tipo di risorsa a resettare
     */
    public void resetResource(ResourceType type) {
        resourceList.get(type).setQta(0);
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

    /**
     * metodo che mi ritorna una mappa delle carte, dove per ciascun tipo mi tiene
     * una lista delle stringhe dei nomi di esse
     * @return ritorna la lista
     */
    public Map<CardType,List<String>> getPersonalCardsMap() {
        Map<CardType, List<String>> cardsNameMap = new HashMap<>();
        cardsNameMap.put(CardType.TERRITORY, new ArrayList<>());
        cardsNameMap.put(CardType.CHARACTER, new ArrayList<>());
        cardsNameMap.put(CardType.BUILDING, new ArrayList<>());
        cardsNameMap.put(CardType.VENTURES, new ArrayList<>());
        cardsMap.forEach(((cardType, developmentCards) -> {
            developmentCards.forEach(developmentCard -> {
                cardsNameMap.get(cardType).add(developmentCard.getName());
            });
        }));
        return cardsNameMap;
    }

    /**
     * mi attiva tutti gli effetti permanenti delle carte territorio
     * @param action l'azione che me le ha attivate
     */
    public void activeTerritoriesEffects(Action action){
        this.currentAction = action;
        cardsMap.get(CardType.TERRITORY).forEach((developmentCard -> {
            try {
                developmentCard.activePermanentEffects();
            }
            catch (RemoteException | NewActionException e) {
                e.printStackTrace();
            }
        }));
    }

    /**
     * mi attiva gli effetti permanenti delle carte edificio
     * @param action azione appena eseguita
     */
    public void activeBuildingsEffects(Action action) {
        this.currentAction = action;
        cardsMap.get(CardType.BUILDING).forEach((developmentCard -> {
            try {
                developmentCard.activePermanentEffects();
            }
            catch (RemoteException | NewActionException e) {
                e.printStackTrace();
            }
        }));
    }

    /**
     * mi attiva gli effetti permanenti delle carte personaggio
     * @param action azione appena eseguita
     */
    public void activeCharacterEffects(Action action) {
        this.currentAction = action;
        cardsMap.get(CardType.CHARACTER).forEach((developmentCard -> {
            try {
                developmentCard.activePermanentEffects();
            }
            catch (RemoteException | NewActionException e) {
                e.printStackTrace();
            }
        }));
    }

    /**
     * mi attiva gli effetti permanenti delle carte impresa
     */
    private void activeVentueresEffects() {
        cardsMap.get(CardType.VENTURES).forEach((developmentCard -> {
            try {
                developmentCard.activePermanentEffects();
            }
            catch (RemoteException | NewActionException e) {
                e.printStackTrace();
            }
        }));
    }

    /**
     * mi setta i valori dei dadi ai rispettivi fmiliari
     * @param orange arancione
     * @param white bianco
     * @param black nero
     */
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
