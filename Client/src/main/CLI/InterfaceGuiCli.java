package main.CLI;

import main.api.types.*;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * @author Andrea
 * @author Luca
 */
public interface InterfaceGuiCli {

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
    void modifyPoints(Map<ResourceType, Integer> map);

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
     * mi rende visibile l'alert realtivo alla scelta nella fase scomunica
     */
     void showExcommunicatingAlert() ;

    /**
     * mi rende visibile l'alert relativo alla scelta di conversione del privilegio
     */
     void showPrivilegeAlert() ;


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
     * inizializza il tabellone
     * @throws InterruptedException
     */
     void initialize() throws InterruptedException ;



}


