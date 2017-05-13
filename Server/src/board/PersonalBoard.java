package board;

import actionSpaces.Action;
import fields.Field;
import fields.Resource;
import fields.ResourceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca, Andrea on 10/05/2017.
 */
public class PersonalBoard {
    private static final char ORANGE_DICE = 'o';
    private static final char BLACK_DICE = 'b';
    private static final char WHITE_DICE = 'w';
    private static final char NEUTRAL_DICE = 'n';
    private List<FamilyMember> familyMemberList;
    // WOOD , STONE , SERVANTS , COINS , VICTORY , FAITH , MIlITARY
    private List<Resource> resourceList;
    private List<Card> territoriesList;
    private List<Card> buildingsList;
    private List<Card> charactersList;
    private List<Card> venturesList;

    private Board board;

    private int id;


    /**
     *
     * @param id identifica il giocatore in ordine di connessione
     */
    public PersonalBoard(int id, Board board){
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
