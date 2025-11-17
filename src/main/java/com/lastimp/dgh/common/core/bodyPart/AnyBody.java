package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.client.player.PlayerHealthCapability;
import com.lastimp.dgh.common.core.Enums.BodyComponents;
import com.lastimp.dgh.common.core.Enums.BodyCondition;

import java.util.List;

public class AnyBody extends AbstractBody {
    private static List<BodyCondition> ANY_BODY_CONDITIONS;

    @Override
    public List<BodyCondition> getBodyConditions() {
        if (ANY_BODY_CONDITIONS == null) {
            ANY_BODY_CONDITIONS = List.of(new BodyCondition[]{
                    BodyCondition.BURN,
                    BodyCondition.BLEED,
                    BodyCondition.INTERNAL_INJURY,
                    BodyCondition.OPEN_WOUND,
                    BodyCondition.INFECTION,
                    BodyCondition.FOREIGN_OBJECT,
                    BodyCondition.BANDAGED,
                    BodyCondition.OINMENTED
            });
        }
        return ANY_BODY_CONDITIONS;
    }

    protected static PlayerHealthCapability updateAnyBody(PlayerHealthCapability health, PlayerHealthCapability nextTickHealth, BodyComponents component) {
        AnyBody body = (AnyBody) health.getComponent(component);
        return health;
    }
}
