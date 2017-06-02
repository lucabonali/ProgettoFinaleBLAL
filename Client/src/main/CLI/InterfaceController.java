package main.CLI;

import main.api.messages.MessageAction;
import main.api.types.*;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * @author Andrea
 * @author Luca
 */
public interface InterfaceController {

    /**
    * mi setta la lista delle carte sulle torri del tabellone
    * @param namesList lista dei nomi delle carte
    */
    void setBoardCards(List<String> namesList);



    /**
    * mi rimuove le carte che sono già state pescate
    * @param nameCards
    */
    void removeDrawnCards(Map<CardType, List<String>> nameCards) ;

    /**
    * mi setta i dadi, in base ai valori ricevuti dal server
    * @param orange
    * @param white
    * @param black
    */
    void setDices(int orange, int white, int black) ;

    /**
    * mi invia il risulato dei dadi, appena lanciati, al server
    */
    void sendDices();

    /**
    * mi genera e posiziona i dischetti dei giocatori avversari
    * @param id id del giocatore avversario
    */
    void createOpponentDiscs(int id) ;


    /**
    * metodo che mi crea e mi rende visibili i familiari
    * @param id id del giocatore, sulla base del quale si ricava il colore
    */
    void createFamilyMembers(int id);

    /**
    * mi riposiziona nella posizione di partenza i miei familiari
    */
    void relocateFamilyMembers();

    /**
    * mi sposta il mio familiare nello spazio azione corretto
    * @param actionSpacesType codice spazio azion
    * @param cardType codice torre
    * @param numFloor codice piano
    * @param marketActionType codice mercato
    * @param familyMemberType codice familiare
    */
    void moveFamilyMember(ActionSpacesType actionSpacesType, CardType cardType, int numFloor, MarketActionType marketActionType, FamilyMemberType familyMemberType);
    /**
    * mi modifica i punti del giocatore, cioè mi sposta i dischetti relativi a me stesso
    * @param map mappa dei valori
    */
    void modifyResources(Map<ResourceType, Integer> map);

    /**
    * mi modifica i punti di un avversario cioè mi sposta i dischetti relativi ad un giocatore avversario
    * @param map mappa dei valori
    */
    void modifyOpponentPoints(Map<ResourceType, Integer> map, int id);

    /**
    * mi rende visibili i dadi al giocatore che deve tirarli
    */
    void showDices();

    /**
    * mi crea i dischetto del colore in base all'id
    * @param id
    */
    void createDiscs(int id);

    /**
    * metodo che notifica la fine della mossa al server
    * @throws RemoteException
    */
    void endMoveAction() throws RemoteException;


    /**
    *
    * @throws RemoteException
    */
    void actionDoAction() throws RemoteException ;

    /**
    * mi esegue una nuova azione
    * @throws RemoteException
    */
    void actionDoNewAction() throws RemoteException ;

    /**
     *
     * @throws InterruptedException
     */
    void exit() throws InterruptedException;

    /**
     * chiamato quando inizia la partita
     * @param id id del giocatore
     */
    void startGame(int id);

    /**
     * mi notifica un messaggio proveniente dal server
     * @param msg
     */
    void notifyMessage(String msg);

    /**
     * mi notifica le tessere scomunica al giocatore
     * @param codeList lista dei codici
     */
    void showExcomCards(List<String> codeList);

    /**
     * abbandona la partita
     */
    void surrender();

    /**
     * aggiorna la lista delle mie carte
     * @param personalCardsMap mappa delle carte personali
     */
    void updateMyCards(Map<CardType, List<String>> personalCardsMap);

    /**
     * mi informa che un giocatore è stato scomunicato nel periodo period
     * @param id id del giocatore che è stato scomunicato
     * @param period periodo della scomunica
     */
    void excommunicate(int id, int period);

    /**
     * mi fa ritornare al menu di scelta del tipo di partita
     */
    void backToMenu();

    /**
     * in base alla mossa codificata nel messaggio, mi dice dove l'avversario ha mosso il suo familiare
     * e quale
     * @param id id del giocatore che ha mosso
     * @param msgAction messaggio dell'azione
     */
    void updateOpponentFamilyMemberMove(int id, MessageAction msgAction);

    /**
     * mi fa vedere l'ordine dei giocatori in questo turno
     * @param orderList lista di interi(id)
     */
    void showOrderList(List<Integer> orderList);

    /**
     * mi rende visibile l'alert realtivo alla scelta nella fase scomunica
     */
    void showExcommunicatingAlert() ;

    /**
     * mi rende visibile l'alert relativo alla scelta di conversione del privilegio
     */
    void showPrivilegeAlert();

    /**
     * mi rende visibile l'alert che mi notifica la fine della partita
     * @param msg messaggio da notificare al giocatore
     */
    void showGameEndedAlert(String msg);

    /**
     * aggiorna la plancia del giocatore con id
     * @param personalcardsMap mappa delle carte
     * @param resourcesMap mappa delle risorse
     * @param id id del giocatore
     */
    void updateOpponentPersonalBoard(Map<CardType, List<String>> personalcardsMap, Map<ResourceType, Integer> resourcesMap, int id);

    /**
     * aggiorna l'interfaccia eliminando il giocatore con id
     * @param surrenderId id del giocatore da eliminare
     */
    void opponentSurrender(int surrenderId);
}


