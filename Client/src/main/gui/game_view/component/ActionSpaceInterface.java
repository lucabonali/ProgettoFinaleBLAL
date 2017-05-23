package main.gui.game_view.component;

import main.api.types.ActionSpacesType;

/**
 * @author Luca
 * @author Andrea
 */
public interface ActionSpaceInterface {

    /**
     * metodo che aggiunge un familiare allo spazio azione
     * @param familyMember
     */
    void addFamilyMember(GraphicFamilyMember familyMember);

    /**
     * rimuove tutti i familiari, eventualmente uno solo se
     * spazio azione singolo
     */
    void removeAllFamilyMembers();

    /**
     * mi ritorna l'enumerazione corrispondente al tipo dello spazio azione
     * @return type
     */
    ActionSpacesType getType();
}
