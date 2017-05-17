package main.controller.effects;

import main.controller.fields.Field;
import main.controller.fields.Resource;
import main.game.AbstractPlayer;

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
    public void active(AbstractPlayer player) {
        player.getPersonalBoard().modifyResources(field);
    }

    public static FixedIncrementEffect createInstance(String code){
        return new FixedIncrementEffect(Resource.createResource(code, false));
    }
}
