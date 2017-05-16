package controller.effects;

import controller.board.PersonalBoard;
import controller.fields.Field;

/**
 * @author lampa
 */
public class AreaActivationEffect implements Effect{
    private Field field;
    private int minValue;

    public AreaActivationEffect(Field field, int minValue){
        this.field = field;
        this.minValue = minValue;
    }

    @Override
    public void active(PersonalBoard personalBoard) {
        if (personalBoard.getCurrentAction().getValue() >= minValue)
            personalBoard.modifyResources(field);

    }
}
