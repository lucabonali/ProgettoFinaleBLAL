package main.gui.game_view.component.action_spaces;

import main.api.types.ActionSpacesType;
import main.gui.game_view.component.GuiFamilyMember;

/**
 * @author Luca
 * @author Andrea
 */
public interface ActionSpaceInterface {

    /**
     * metodo che aggiunge un familiare allo spazio azione
     * @param familyMember
     */
    void addFamilyMember(GuiFamilyMember familyMember);

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

    /**
     * mi setta lo spazio azione corrente
     */
    void setCurrentActionSpace();
}
