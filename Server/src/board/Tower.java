package board;

import actionSpaces.FloorActionSpace;

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

    public Tower(CardType type){
        this.towerType = type;
        this.floorActionSpaces = new FloorActionSpace[4];
        for(int i = 0 ; i<4 ; i++){
            //devo aggiungere gli effetti ai piani 3 e 4 di ciascuna torre
            floorActionSpaces[i] = new FloorActionSpace(i);
        }
    }

    /**
     * setta le carte di ogni piano
     * @param card
     */
    public void setCards(Card[] card){
        for(int i = 0 ; i<4 ; i++){
            floorActionSpaces[i].setCard(card[i]);
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









}
