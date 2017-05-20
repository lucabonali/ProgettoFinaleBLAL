package main.model.effects.excommunicating_effects;

import main.model.effects.development_effects.ActionValueModifyingEffect;
import main.model.effects.development_effects.Effect;

import static main.model.effects.development_effects.EffectsCreator.*;

/**
 * @author Luca
 * @author Andrea
 */
public class ExcommunicatingEffectCreator {

    public static Effect createInstanceFirstPeriod(String codEffect) {
        switch (codEffect.charAt(0)){
            case CHAR_FAMILY_MEMBER:
                return new FamilyMemberValueDecrementEffect();
            case CHAR_HARVEST:
            case CHAR_PRODUCTION:
                return ActionValueModifyingEffect.createExcomInstance(codEffect);
            case CHAR_MILITARY:
            case CHAR_COIN:
            case CHAR_SERVANT:
                return ForEachGainDecrementEffect.createExcomInstance(codEffect);
        }
        return null;
    }

    public static Effect createInstanceSecondPeriod(String codEffect) {
        switch (codEffect.charAt(0)){
            case CHAR_BUILDINGS:
            case CHAR_CHARACTERS:
            case CHAR_VENTURES:
            case CHAR_TERRITORY:
                return ActionValueModifyingEffect.createExcomInstance(codEffect);
        }
        return null;
    }

    public static Effect createInstanceThirdPeriod(String codEffect) {
        switch (codEffect.charAt(0)){
            case CHAR_CHARACTERS:
            case CHAR_VENTURES:
            case CHAR_TERRITORY:
                return NotGainVictoryEffect.createExcomInstance(codEffect);
            case CHAR_MILITARY:
            case CHAR_VICTORY:
            case CHAR_RESOURCE:
                return VictoryPointsDecrementEffect.createExcomInstance(codEffect);
        }
        return null;
    }
}
