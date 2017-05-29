package main.CLI;

import javafx.scene.layout.GridPane;
import main.api.types.*;
import main.gui.game_view.component.action_spaces.GuiFloorActionSpace;
import main.gui.game_view.component.action_spaces.GuiMarketActionSpace;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * @author Andrea
 * @author Luca
 */
public interface InterfaceGuiCli {

    /**
     * mi inizializza gli spazi azione di raccolta e produzione sia larghi
     * che singlo e del palazzo del consiglio
     */
    void initializeHarvestProduction() ;
    /**
     * mi inizializza gli spazi azione delle torri
     * @param cardType tipo di torre
     * @param gridPaneTower contenitore che mi identifica la posizione sul tabellone
     */
    void initializeTowers(CardType cardType, GridPane gridPaneTower) ;



    /**
     * mi inizializza il mercato
     * @param marketType tipo di mercato
     * @param gridPaneMarket contenitore sulla quale verrà inserito lo spazio azione
     */
    void initializeMarket(MarketActionType marketType, GridPane gridPaneMarket) ;

    /**
     * mi inizializza le imageView e me le posiziona sui gridPane corretti
     * e mi aggiunge gli effetti di zoom.
     */
     void initializeImageViewCards() ;


    /**
     * mi setta la lista delle carte sulle torri del tabellone
     * @param namesList lista dei nomi delle carte
     */
    public void setBoardCards(List<String> namesList);



    /**
     * mi rimuove le carte che sono già state pescate
     * @param nameCards
     */
    public void removeDrawnCards(Map<CardType, List<String>> nameCards) ;

    /**
     * mi setta i dadi, in base ai valori ricevuti dal server
     * @param orange
     * @param white
     * @param black
     */
    public void setDices(int orange, int white, int black) ;

    /**
     * mi invia il risulato dei dadi, appena lanciati, al server
     */
    public void sendDices();

     /**
     * mi genera e posiziona i dischetti dei giocatori avversari
     * @param id id del giocatore avversario
     */
    public void createOpponentDiscs(int id) ;


    /**
     * metodo che mi crea e mi rende visibili i familiari
     * @param id id del giocatore, sulla base del quale si ricava il colore
     */
    public void createFamilyMembers(int id);

    /**
     * mi riposiziona nella posizione di partenza i miei familiari
     */
    public void relocateFamilyMembers();

    /**
     * mi sposta il mio familiare nello spazio azione corretto
     * @param actionSpacesType codice spazio azion
     * @param cardType codice torre
     * @param numFloor codice piano
     * @param marketActionType codice mercato
     * @param familyMemberType codice familiare
     */
    public void moveFamilyMember(ActionSpacesType actionSpacesType, CardType cardType, int numFloor, MarketActionType marketActionType, FamilyMemberType familyMemberType);
    /**
     * mi modifica i punti del giocatore, cioè mi sposta i dischetti relativi a me stesso
     * @param map mappa dei valori
     */
    public void modifyPoints(Map<ResourceType, Integer> map);

    /**
     * mi modifica i punti di un avversario cioè mi sposta i dischetti relativi ad un giocatore avversario
     * @param map mappa dei valori
     */
    public void modifyOpponentPoints(Map<ResourceType, Integer> map, int id);

    /**
     * mi rende visibile l'alert realtivo alla scelta nella fase scomunica
     */
    public void showExcommunicatingAlert() ;

    /**
     * mi rende visibile l'alert relativo alla scelta di conversione del privilegio
     */
    public void showPrivilegeAlert() ;


    /**
     * metodo che notifica la fine della mossa al server
     * @throws RemoteException
     */
    public void endMoveAction() throws RemoteException;


    /**
     *
     * @throws RemoteException
     */
    public void actionDoAction() throws RemoteException ;


    public void actionDoNewAction() throws RemoteException ;


    /**
     * inizializza il tabellone
     * @throws InterruptedException
     */
    public void initialize() throws InterruptedException ;



}


