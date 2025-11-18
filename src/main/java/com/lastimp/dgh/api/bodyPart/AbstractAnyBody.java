package com.lastimp.dgh.api.bodyPart;

import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.api.enums.BodyCondition;
import net.minecraft.util.Mth;

import java.util.List;

import static com.lastimp.dgh.DontGetHurt.DELTA;
import static com.lastimp.dgh.DontGetHurt.EPS;
import static com.lastimp.dgh.api.enums.BodyComponents.*;
import static com.lastimp.dgh.api.enums.BodyCondition.*;

public abstract class AbstractAnyBody extends AbstractBody {
    private static List<BodyCondition> ANY_BODY_CONDITIONS;

    @Override
    public List<BodyCondition> getBodyConditions() {
        if (ANY_BODY_CONDITIONS == null) {
            ANY_BODY_CONDITIONS = List.of(new BodyCondition[]{
                    BURN,
                    INTERNAL_INJURY,
                    OPEN_WOUND,
                    BLEED,
                    INFECTION,
                    FOREIGN_OBJECT,
                    BANDAGED,
                    BANDAGED_DIRTY,
                    OINMENTED
            });
        }
        return ANY_BODY_CONDITIONS;
    }

    @Override
    public void update(PlayerHealthCapability health) {
        this.updateInit(health);
        handleBurning(health);
        handleInternalInjury(health);
        handleOpenWound(health);
    }

    @Override
    public void updateInit(PlayerHealthCapability health) {
        super.updateInit(health);
        this.getCondition(BLEED).value = 0;
    }

    private void handleBurning(PlayerHealthCapability health) {
        if (!this.abnormalWithHidden(BURN)) return;
        this.handleCover(BURN, 2.0f);

        if (isBandaged()) return;
        ConditionState bleed = this.getCondition(BLEED);
        bleed.value += Mth.clamp(bleed.value + this.getCondition(BURN).value * 0.3f, BLEED.minValue, BLEED.maxValue);
    }

    private void handleInternalInjury(PlayerHealthCapability health) {
        if (!this.abnormalWithHidden(INTERNAL_INJURY)) return;
        this.handleCover(INTERNAL_INJURY, 2.0f);

        ConditionState bleed = this.getCondition(INTERNAL_INJURY);
        bleed.value += Mth.clamp(bleed.value + this.getCondition(INTERNAL_INJURY).value * 0.2f, BLEED.minValue, BLEED.maxValue);
    }

    private void handleOpenWound(PlayerHealthCapability health) {
        if (!this.abnormalWithHidden(OPEN_WOUND)) return;
        this.handleCover(OPEN_WOUND, 2.0f);

        if (isBandaged()) return;
        ConditionState bleed = this.getCondition(INTERNAL_INJURY);
        bleed.value += Mth.clamp(bleed.value + this.getCondition(INTERNAL_INJURY).value * 0.5f, BLEED.minValue, BLEED.maxValue);
    }

    private void handleCover(BodyCondition condition, float acc) {
        ConditionState state = this.getCondition(condition);

        if (isBandaged()) {
            state.hiddenValue = Mth.clamp(state.hiddenValue + state.value, condition.minValue, condition.maxValue);
            state.value = condition.defaultValue;
            state.hiddenValue -= condition.healingSpeed * acc * DELTA;
        } else {
            state.value = condition.abnormal(state.hiddenValue)? state.hiddenValue : state.value;
            state.hiddenValue = condition.defaultValue;
            if (state.value < condition.healingTS + EPS) {
                state.value -= condition.healingSpeed * DELTA;
            }
        }
    }

    private boolean isBandaged() {
        return this.getConditionValue(BANDAGED) > BANDAGED.defaultValue + EPS;
    }

    private void handleBleeding(PlayerHealthCapability health) {
        if (!this.abnormalWithHidden(BLEED)) return;

        ConditionState bloodVolume = health.getComponent(BLOOD).getCondition(BLOOD_VOLUME);
        bloodVolume.value -= this.getCondition(BLEED).value * DELTA;
    }
}
