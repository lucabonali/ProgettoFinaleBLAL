package main.controller.effects;

import main.controller.fields.Field;
import main.game.AbstractPlayer;

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
    public void active(AbstractPlayer player) {
        if (player.getPersonalBoard().getCurrentAction().getValue() >= minValue)
            player.getPersonalBoard().modifyResources(field);
    }
}
