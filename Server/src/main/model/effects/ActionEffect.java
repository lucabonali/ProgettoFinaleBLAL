package main.model.effects;

import main.api.exceptions.NewActionException;
import main.servergame.AbstractPlayer;

import java.rmi.RemoteException;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi rappresenta un effetto che mi consente
 * di eseguire una nuova azione senza dover spostare un
 * mio familiare.
 */
public class ActionEffect implements Effect{
    private int value;
    private char codActionSpace;

    public ActionEffect(char codActionSpace, int value) {
        this.codActionSpace = codActionSpace;
        this.value = value;
    }


    /**
     * metodo che mi esegue una nuova azione chiedendo al clientGame quale
     * @param player giocatore che esegue l'azione
     */
    @Override
    public void active(AbstractPlayer player) throws RemoteException, NewActionException {
        player.notifyNewAction(value, codActionSpace);
        throw new NewActionException();
    }

    public static ActionEffect createInstance(String code){
        int value = Integer.parseInt(code.substring(0,1));
        return new ActionEffect(code.charAt(1), value);
    }
}
