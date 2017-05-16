package controller.effects;

import controller.board.PersonalBoard;
import controller.fields.Field;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi rappresenta un effetto che mi incrementa
 * di un valore prefissato una risorsa specifica passata come
 * parametro nel costruttore
 */
public class FixedIncrementEffect implements Effect{
    private Field field;

    public FixedIncrementEffect(Field field){
        this.field = field;
    }

    @Override
    public void active(PersonalBoard personalBoard) {
        personalBoard.modifyResources(field);
    }
}
