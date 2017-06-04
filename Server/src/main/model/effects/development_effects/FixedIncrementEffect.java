package main.model.effects.development_effects;

import main.api.types.ResourceType;
import main.model.fields.Field;
import main.model.fields.Resource;
import main.game_server.AbstractPlayer;
import org.jetbrains.annotations.NotNull;

import java.rmi.RemoteException;

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
    public void active(AbstractPlayer player) throws RemoteException {
        player.getPersonalBoard().setCurrentField(field);
        if (field  != null) {
            if (field.getType() != ResourceType.PRIVILEGE)
                player.getPersonalBoard().modifyResources(field);
            else
                player.notifyPrivilege();
        }
        player.activeExcommunicationEffects(player.getPersonalBoard().getCurrentAction(), 2);
    }

    @NotNull
    public static FixedIncrementEffect createInstance(String code){
        return new FixedIncrementEffect(Resource.createResource(code, false));
    }

    @NotNull
    public static Effect createInstance(String substring, boolean incDec) {
        if(incDec)
            return FixedIncrementEffect.createInstance(substring);
        else
            return new FixedIncrementEffect(Resource.createResource(substring, true));
    }
}
