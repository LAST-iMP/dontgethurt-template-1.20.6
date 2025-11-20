package com.lastimp.dgh.api.bodyPart;

import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import com.lastimp.dgh.api.enums.BodyCondition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.List;

import static com.lastimp.dgh.DontGetHurt.DELTA;
import static com.lastimp.dgh.DontGetHurt.EPS;
import static com.lastimp.dgh.api.enums.BodyComponents.*;
import static com.lastimp.dgh.api.enums.BodyCondition.*;

public abstract class AbstractVisibleBody extends AbstractBody {
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
    public AbstractBody update(PlayerHealthCapability health, Player player) {
        handleBandaged(health);
        handleBurning(health);
        handleInternalInjury(health, player);
        handleOpenWound(health);

        handleBleeding(health);
        return this;
    }

    @Override
    public AbstractBody updatePre(PlayerHealthCapability health, Player player) {
        super.updatePre(health, player);
        this.getCondition(BLEED).setValue(0);
        return this;
    }

    private void handleBandaged(PlayerHealthCapability health) {
        if (isBandaged()) {
            ConditionState state = this.getCondition(BANDAGED);
            state.setValue(state.getValue() - BANDAGED.healingSpeed * DELTA);
            if (!isBandaged()) {
                this.getCondition(BANDAGED_DIRTY).setValue(BANDAGED_DIRTY.maxValue);
            }
        }
        ConditionState burn = this.getCondition(BURN);
        ConditionState openWound = this.getCondition(OPEN_WOUND);
        ConditionState innerInjury = this.getCondition(INTERNAL_INJURY);
        if (this.abnormalWithHidden(BANDAGED_DIRTY) && BURN.abnormal(burn.getHiddenValue())) {
            innerInjury.setValue(Mth.clamp(innerInjury.getValue() + burn.getHiddenValue() * 0.1f * DELTA, INTERNAL_INJURY.minValue, INTERNAL_INJURY.maxValue));
        } else if (this.abnormalWithHidden(BANDAGED_DIRTY) && OPEN_WOUND.abnormal(openWound.getHiddenValue())) {
            innerInjury.setValue(Mth.clamp(innerInjury.getValue() + openWound.getHiddenValue() * 0.1f * DELTA, INTERNAL_INJURY.minValue, INTERNAL_INJURY.maxValue));
        }
    }

    private void handleBurning(PlayerHealthCapability health) {
        if (!this.abnormalWithHidden(BURN)) return;
        this.handleCover(BURN, 2.0f);

        if (isBandaged()) return;
        ConditionState bleed = this.getCondition(BLEED);
        bleed.setValue(bleed.getValue() + Mth.clamp(bleed.getValue() + this.getCondition(BURN).getValue() * 0.2f, BLEED.minValue, BLEED.maxValue));
    }

    private void handleInternalInjury(PlayerHealthCapability health, Player player) {
        if (!this.abnormalWithHidden(INTERNAL_INJURY)) return;
        this.handleCover(INTERNAL_INJURY, 1.0f);

        ConditionState bleed = this.getCondition(BLEED);
        bleed.setValue(bleed.getValue() + Mth.clamp(bleed.getValue() + this.getCondition(INTERNAL_INJURY).getValue() * 0.1f, BLEED.minValue, BLEED.maxValue));

        float saturation = player.getFoodData().getSaturationLevel();
        float delta = INTERNAL_INJURY.healingSpeed * DELTA;
        if (saturation > 0) {
            if (INTERNAL_INJURY.abnormal(this.getCondition(INTERNAL_INJURY).getHiddenValue()))
                this.injuryHidden(INTERNAL_INJURY, -delta);
            else
                this.injury(INTERNAL_INJURY, -delta * 4);
            player.getFoodData().addExhaustion(delta * 4);
        }
    }

    private void handleOpenWound(PlayerHealthCapability health) {
        if (!this.abnormalWithHidden(OPEN_WOUND)) return;
        this.handleCover(OPEN_WOUND, 1.2f);

        if (isBandaged()) return;
        ConditionState bleed = this.getCondition(BLEED);
        bleed.setValue(bleed.getValue() + Mth.clamp(bleed.getValue() + this.getCondition(OPEN_WOUND).getValue() * 0.3f, BLEED.minValue, BLEED.maxValue));
    }

    private void handleCover(BodyCondition condition, float acc) {
        ConditionState state = this.getCondition(condition);

        if (isBandaged() || isBadBandaged()) {
            state.setHiddenValue(Mth.clamp(state.getHiddenValue() + state.getValue(), condition.minValue, condition.maxValue));
            state.setValue(condition.defaultValue);
            state.setHiddenValue(
                    Mth.clamp(state.getHiddenValue() - condition.healingSpeed * DELTA * (isBadBandaged() ? 1.0f : acc),
                    condition.minValue, condition.maxValue)
            );
        } else {
            state.setValue(condition.abnormal(state.getHiddenValue())? state.getHiddenValue() : state.getValue());
            state.setHiddenValue(condition.defaultValue);
            if (state.getValue() < condition.healingTS + EPS) {
                state.setValue(state.getValue() - condition.healingSpeed * DELTA);
            }
        }
    }

    private boolean isBandaged() {
        return this.getConditionValue(BANDAGED) > BANDAGED.defaultValue + EPS;
    }

    private boolean isBadBandaged() {
        return this.getConditionValue(BANDAGED_DIRTY) > BANDAGED.defaultValue + EPS;
    }

    private void handleBleeding(PlayerHealthCapability health) {
        if (!this.abnormalWithHidden(BLEED)) return;

        ConditionState bloodVolume = health.getComponent(BLOOD).getCondition(BLOOD_VOLUME);
        bloodVolume.setValue(bloodVolume.getValue() - this.getCondition(BLEED).getValue() * DELTA * 0.2f);
    }
}
