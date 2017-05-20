package main.model.board;

import main.model.effects.development_effects.Effect;
import main.servergame.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lampa
 */
public class ExcommunicatingCard{
    //lista dei giocatori scomunicati
    private List<AbstractPlayer> excomunicatedPlayersList;
    private Effect effect;

    public ExcommunicatingCard(Effect effect) {
        this.effect = effect;
        this.excomunicatedPlayersList = new ArrayList<>();
    }

    public void addPlayer(AbstractPlayer player) {
        if (!excomunicatedPlayersList.contains(player))
            excomunicatedPlayersList.add(player);
    }
}
