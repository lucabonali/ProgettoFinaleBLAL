package main.model.board;

import main.api.types.CardType;
import main.api.types.ResourceType;
import main.model.actionSpaces.singleActionSpaces.ActionSpace;
import main.model.actionSpaces.singleActionSpaces.FloorActionSpace;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi rappresenta una singola torre del mio tabellone
 * esso sarà composto da 4 piani ciascuno dei quali sarà uno
 * spazio azione, ciascuna torre avrà carte sviluppo di un solo tipo
 */
public class Tower {
    private FloorActionSpace[] floorActionSpaces;
    private CardType towerType;

    public Tower(CardType type, ResourceType resourceTypeQuickEffect){
        this.towerType = type;
        this.floorActionSpaces = new FloorActionSpace[4];
        for(int i = 0 ; i<4 ; i++){
            //devo aggiungere gli effetti ai piani 3 e 4 di ciascuna torre
            floorActionSpaces[i] = new FloorActionSpace(i, towerType, resourceTypeQuickEffect);
        }
    }

    /**
     * setta le carte di ogni piano
     * @param cards
     */
    public void setCards(List<Card> cards){
        for(int i = 0 ; i<4 ; i++){
            floorActionSpaces[i].setCard(cards.get(i));
        }
    }

    public void removeCards(){
        for(int i = 0 ; i<4 ; i++){
            floorActionSpaces[i].removeCard();
        }
    }

    /**
     * viene chiamato quando tutti i controlli vanno a buon fine e la mette nella plancia del giocatore che la ha ricevuta
     * @param n
     * @return carta pescata
     */
    private Card draw(int n){
        return floorActionSpaces[n].getCard();
    }

    /**
     * questo metodo farà i controlli per determinare se c'è un altro tuo familiare o se dove lo vuoi mettere non è nullo !!!!!!!! fam.getGangPlank
     * @param n numero piano
     * @param fam familiare
     * @return true se va a buon fine, else false
     */
    public boolean setFamilyMembers(int n, FamilyMember fam){
        return true;
    }

    /**
     * metodo che elimina tutti i familiari alla fine di ogni turno
     */
    public void removeFamilyMembers(){
        for(int i = 0 ; i<4 ; i++){
            floorActionSpaces[i].removeFamilyMember();
        }
    }

    public ActionSpace getFloor(int numFloor) {
        return floorActionSpaces[numFloor];
    }

    public List<Card> getCards() {
        List<Card> list = new ArrayList<>();
        for (int i=0; i < 4; i++){
            list.add(floorActionSpaces[i].getCard());
        }
        return list;
    }
}