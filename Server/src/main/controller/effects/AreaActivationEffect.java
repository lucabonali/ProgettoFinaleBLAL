package main.controller.effects;

import main.controller.board.PersonalBoard;
import main.controller.fields.Field;

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
