package com.lastimp.dgh.api.bodyPart;

import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.api.enums.BodyCondition;

import java.util.List;

import static com.lastimp.dgh.DontGetHurt.DELTA;
import static com.lastimp.dgh.DontGetHurt.EPS;
import static com.lastimp.dgh.api.enums.BodyCondition.*;

public abstract class AbstractAnyBody extends AbstractBody {
    private static List<BodyCondition> ANY_BODY_CONDITIONS;

    @Override
    public List<BodyCondition> getBodyConditions() {
        if (ANY_BODY_CONDITIONS == null) {
            ANY_BODY_CONDITIONS = List.of(new BodyCondition[]{
                    BURN,
                    BLEED,
                    INTERNAL_INJURY,
                    OPEN_WOUND,
                    INFECTION,
                    FOREIGN_OBJECT,
                    BANDAGED,
                    OINMENTED
            });
        }
        return ANY_BODY_CONDITIONS;
    }

    protected static PlayerHealthCapability updateAnyBody(PlayerHealthCapability health, PlayerHealthCapability nextTickHealth, BodyComponents component) {
        AbstractAnyBody body = (AbstractAnyBody) health.getComponent(component);
        nextTickHealth.getComponent(component).setConditionValue(BURN, handleBurning(health, body));
        nextTickHealth.getComponent(component).setConditionValue(INTERNAL_INJURY, handleInternalInjury(health, body));
        return health;
    }

    private static float handleBurning(PlayerHealthCapability health, AbstractAnyBody body) {
        float value = body.getConditionValue(BURN);
        if (!BURN.abnormal(value)) return value;

        if (body.getConditionValue(BANDAGED) > BANDAGED.defaultValue + EPS) {
            value -= BURN.selfHealingSpeed * 2 * DELTA;
        } else if (value < BURN.selfHealingTS + EPS) {
            value -= BURN.selfHealingSpeed * DELTA;
        }
        return value;
    }

    private static float handleInternalInjury(PlayerHealthCapability health, AbstractAnyBody body) {
        float value = body.getConditionValue(INTERNAL_INJURY);
        if (!INTERNAL_INJURY.abnormal(value)) return value;

        if (value < INTERNAL_INJURY.selfHealingTS + EPS) {
            value -= INTERNAL_INJURY.selfHealingSpeed * DELTA;
        }
        return value;
    }
}
