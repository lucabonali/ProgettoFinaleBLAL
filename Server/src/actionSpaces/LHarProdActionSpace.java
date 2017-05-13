package actionSpaces;

import effects.Effect;
import fields.Resource;
import fields.ResourceType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica le zone multiple del mio tabellone
 * che possono essere di produzione e di raccolta
 */
public class LHarProdActionSpace extends LargeActionSpace {
    private List<Effect> bonusEffectList;
    private AreaType type;

    public LHarProdActionSpace(int value, AreaType type){
        super(value+3);
        this.type = type;
        initializeBonus();
    }

    /**
     * in base al tipo chiamo initializeHarvest o initializeProdution
     */
    private void initializeBonus() {
        bonusEffectList = new ArrayList<>();
        if(this.type == AreaType.HARVEST)
            initializeHarvestBonus();
        else
            initializeProductionBonus();
    }

    /**
     * metodo di servizio che richiamo quando creo lo spazio azione raccolta
     */
    private void initializeHarvestBonus(){
        bonusEffectList.add(new Effect(
                new Resource(1, ResourceType.WOOD)
        ));
        bonusEffectList.add(new Effect(
                new Resource(1, ResourceType.STONE)
        ));
        bonusEffectList.add(new Effect(
                new Resource(1, ResourceType.SERVANTS)
        ));
    }
    /**
     * Metodo di servizio che chiamo quando creo lo spazio azione produzione
     */
    private void initializeProductionBonus(){
        bonusEffectList.add(new Effect(
                new Resource(1, ResourceType.MILITARY)
        ));
        bonusEffectList.add(new Effect(
                new Resource(1, ResourceType.COINS)
        ));
    }
    @Override
    public void doAction(Action action) {
        //Da implementare
    }

}
