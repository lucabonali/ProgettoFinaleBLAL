package main.model.effects;

import com.sun.org.apache.regexp.internal.RE;
import main.api.exceptions.NewActionException;
import main.model.fields.Field;
import main.model.fields.Resource;
import main.servergame.AbstractPlayer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che implementa l' effetto di sostituzione di una o più risorse con altre
 * Created by Luca, Andrea on 19/05/2017.
 */
public class ConvertionEffect implements Effect{
    private List<Field> fieldToIncrement;
    private List<Field> fieldToDecrement;

    /**
     * Costruttore della classe
     * @param fieldToIncrement
     * @param fieldToDecrement
     */
    public ConvertionEffect(List<Field> fieldToIncrement, List<Field> fieldToDecrement){
        this.fieldToDecrement = fieldToDecrement;
        this.fieldToIncrement = fieldToIncrement;
    }


    /**
     * Attiva l' effetto della conversione andando a controllare se la conversione può essere effettuata facendo il
     * controllo sulla risorsa
     * @param player il giocatore che sta attivando l'effetto
     * @throws RemoteException
     * @throws NewActionException
     */
    @Override
    public void active(AbstractPlayer player) throws RemoteException, NewActionException {
        for(Field f : fieldToDecrement){
            if(!player.getPersonalBoard().checkResources(f)){
                return;
            }
        }
        for(Field f : fieldToDecrement){
            player.getPersonalBoard().modifyResources(f);
        }
        for(Field f : fieldToIncrement) {
            player.getPersonalBoard().modifyResources(f);
        }
    }


    /**
     * prende le due stringhe codificate e aggiunge le risorse alle due liste da inc e da dec
     * @param increment lista di risorse da incrementare
     * @param decrement lista di risorse da decrementare
     * @return
     */
    public static ConvertionEffect createInstance(String increment, String decrement){
        List<Field> fieldToIncrement = new ArrayList<>();
        List<Field> fieldToDecrement = new ArrayList<>();

        fieldToIncrement.add(Resource.createResource(increment.substring(0,2),false));
        if(increment.length() == 4){
            fieldToIncrement.add(Resource.createResource(increment.substring(2,4),false));
        }
        if(increment.length() == 6){
            fieldToIncrement.add(Resource.createResource(increment.substring(4,6),false));
        }

        fieldToDecrement.add(Resource.createResource(decrement.substring(0,2),true));
        if(decrement.length() == 4){
            fieldToDecrement.add(Resource.createResource(decrement.substring(2,4),true));
        }
        return new ConvertionEffect(fieldToIncrement,fieldToDecrement);

    }

}
