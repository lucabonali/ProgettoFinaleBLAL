package test.model.effects;

import main.api.types.CardType;
import main.api.types.FamilyMemberType;
import main.api.types.ResourceType;
import main.model.action_spaces.Action;
import main.model.action_spaces.singleActionSpaces.HarvestActionSpace;
import main.model.board.DevelopmentCard;
import main.model.effects.development_effects.AreaActivationEffect;
import main.model.effects.development_effects.Effect;
import main.model.effects.development_effects.FixedIncrementEffect;
import main.model.fields.Resource;
import main.servergame.socket.PlayerSocket;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * @author lampa
 */
public class AreaActivationEffectTest {
    @Test
    public void active() throws Exception {
        FixedIncrementEffect ef1 = new FixedIncrementEffect(new Resource(5, ResourceType.WOOD));
        AreaActivationEffect ef2 = new AreaActivationEffect(ef1,  3);
        List<Effect> effectList = new ArrayList<>();
        effectList.add(ef2);
        DevelopmentCard DevelopmentCard = new DevelopmentCard(CardType.TERRITORY, "valle", null, null, effectList, 1);
        PlayerSocket p = new PlayerSocket("andrea");
        p.createPersonalBoard(1);
        DevelopmentCard.setPlayer(p);
        HarvestActionSpace has = new HarvestActionSpace(1);
        Action action = new Action(has, 4, p.getFamilyMember(FamilyMemberType.ORANGE_DICE), p);
        has.doAction(action);
        assertEquals(Optional.of(8), Optional.of(p.getPersonalBoard().getQtaResources().get(ResourceType.WOOD)));
        assertEquals(Optional.of(4), Optional.of(p.getPersonalBoard().getQtaResources().get(ResourceType.SERVANTS)));
    }

}