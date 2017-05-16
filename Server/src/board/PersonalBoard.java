package board;

import actionSpaces.Action;
import api.FamilyMemberType;
import effects.ExcomEffect;
import fields.Field;
import fields.Resource;
import types.ResourceType;

import java.util.ArrayList;
import java.util.List;

import static api.FamilyMemberType.*;

/**
 * @author Luca
 * @author Andrea
 *
 * rappresenta la plancia personale del singolo giocatore
 */
public class PersonalBoard {
    //lista dei familiari in possesso, uno neutro e tre personali
    private List<FamilyMember> familyMemberList;

    // WOOD , STONE , SERVANTS , COINS , VICTORY , FAITH , MILITARY
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
        familyMemberList = new ArrayList<>();
        familyMemberList.add(new FamilyMember(this, ORANGE_DICE));
        familyMemberList.add(new FamilyMember(this, WHITE_DICE));
        familyMemberList.add(new FamilyMember(this, BLACK_DICE));
        familyMemberList.add(new FamilyMember(this, NEUTRAL_DICE));
    }

    private void initializeResources(){
        resourceList = new ArrayList<>();
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

    /**
     * mi controlla se ho le risorse necessarie
     * @param cost risorsa da verificare
     * @return true se le ho, false altrimenti
     */
    public boolean checkResources(Field cost){
        for (Field res : resourceList) {
            if (res.getType() == cost.getType())
                if (res.getQta() < cost.getQta())
                    return false;
        }
        return true;
    }

    /**
     * mi ritona il familiare del tipo passato come paramentro
     * @param type tipo del familaire
     * @return il familiare corretto
     */
    public FamilyMember getFamilyMember(FamilyMemberType type){
        for(FamilyMember member: familyMemberList){
            if (member.getType() == type)
                return member;
        }
        return null;
    }

    /**
     * metodo che mi ritorna la lista delle qta delle mie risorse
     * @return Lista delle quantità
     */
    public List<Integer> getQtaResources() {
        List<Integer> qtaResourcesList = new ArrayList<>();
        for(Resource res : resourceList) {
            qtaResourcesList.add(res.getQta());
        }
        return qtaResourcesList;
    }

    public void activeCharacterEffects(Action action) {
        this.currentAction = action;
        for (Card card : charactersList) {
            card.activePermanentEffects();
        }
    }

    public void setDiceValues(int o , int w, int b){
        familyMemberList.get(0).setValue(o);
        familyMemberList.get(1).setValue(w);
        familyMemberList.get(2).setValue(b);
        familyMemberList.get(3).setValue(0);
    }

}
