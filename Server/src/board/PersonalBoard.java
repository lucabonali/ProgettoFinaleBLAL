package board;

import actionSpaces.Action;
import effects.ExcomEffect;
import fields.Field;
import fields.Resource;
import types.ResourceType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luca
 * @author Andrea
 *
 * rappresenta la plancia personale del singolo giocatore
 */
public class PersonalBoard {
    private static final char ORANGE_DICE = 'o';
    private static final char BLACK_DICE = 'b';
    private static final char WHITE_DICE = 'w';
    private static final char NEUTRAL_DICE = 'n';

    //lista dei familiari in possesso, uno neutro e tre personali
    private List<FamilyMember> familyMemberList;

    // WOOD , STONE , SERVANTS , COINS , VICTORY , FAITH , MIlITARY
    private List<Resource> resourceList;

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

    private void initializeCardsLists() {
        territoriesList = new ArrayList<>();
        buildingsList = new ArrayList<>();
        charactersList = new ArrayList<>();
        venturesList = new ArrayList<>();
    }

    private void initializeFamilyMembers() {
        familyMemberList.add(new FamilyMember(this, ORANGE_DICE));
        familyMemberList.add(new FamilyMember(this, BLACK_DICE));
        familyMemberList.add(new FamilyMember(this, WHITE_DICE));
        familyMemberList.add(new FamilyMember(this, NEUTRAL_DICE));
    }

    private void initializeResources(){
        resourceList.add(new Resource(2, ResourceType.WOOD));
        resourceList.add(new Resource(2, ResourceType.STONE));
        resourceList.add(new Resource(3, ResourceType.SERVANTS));
        int qta = 4;
        resourceList.add(new Resource(qta+id, ResourceType.WOOD));
        resourceList.add(new Resource(0, ResourceType.VICTORY));
        resourceList.add(new Resource(0, ResourceType.FAITH));
        resourceList.add(new Resource(0, ResourceType.MILITARY));
    }

    /**
     * viene richiamato da Effect e modifica la risorsa passata come parametro
     * @param field
     */
    public void modifyResources(Field field){
        for(Resource r : resourceList){
            r.modify(field);
        }
    }

    public void doAction(Action action){
    // da implementare
    }





}
