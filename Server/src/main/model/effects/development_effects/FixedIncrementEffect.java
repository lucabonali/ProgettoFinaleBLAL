package main.model.effects.development_effects;

import main.model.fields.Field;
import main.model.fields.Resource;
import main.servergame.AbstractPlayer;

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

    public static Effect createInstance(String substring, boolean incDec) {
        if(incDec)
            return FixedIncrementEffect.createInstance(substring);
        else
            return new FixedIncrementEffect(Resource.createResource(substring, true));
    }
}
