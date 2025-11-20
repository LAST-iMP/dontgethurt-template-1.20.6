package com.lastimp.dgh.api.bodyPart;

import com.lastimp.dgh.Config;
import com.lastimp.dgh.source.core.player.PlayerHealthCapability;
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
            innerInjury.setValue(Mth.clamp(innerInjury.getValue() + burn.getHiddenValue() * Config.dirty_bandage_ratio * DELTA, INTERNAL_INJURY.minValue, INTERNAL_INJURY.maxValue));
        } else if (this.abnormalWithHidden(BANDAGED_DIRTY) && OPEN_WOUND.abnormal(openWound.getHiddenValue())) {
            innerInjury.setValue(Mth.clamp(innerInjury.getValue() + openWound.getHiddenValue() * Config.dirty_bandage_ratio * DELTA, INTERNAL_INJURY.minValue, INTERNAL_INJURY.maxValue));
        }
    }

    private void handleBurning(PlayerHealthCapability health) {
        if (!this.abnormalWithHidden(BURN)) return;
        this.handleCover(BURN, Config.bandage_burn_acc);

        if (isBandaged()) return;
        ConditionState bleed = this.getCondition(BLEED);
        bleed.setValue(bleed.getValue() + Mth.clamp(bleed.getValue() + this.getCondition(BURN).getValue() * Config.burn_bleed_ratio, BLEED.minValue, BLEED.maxValue));
    }

    private void handleInternalInjury(PlayerHealthCapability health, Player player) {
        if (!this.abnormalWithHidden(INTERNAL_INJURY)) return;
        this.handleCover(INTERNAL_INJURY, Config.bandage_internal_acc);

        ConditionState bleed = this.getCondition(BLEED);
        bleed.setValue(bleed.getValue() + Mth.clamp(bleed.getValue() + this.getCondition(INTERNAL_INJURY).getValue() * Config.internal_bleed_ratio, BLEED.minValue, BLEED.maxValue));

        float saturation = player.getFoodData().getSaturationLevel();
        float delta = INTERNAL_INJURY.healingSpeed * DELTA;
        if (saturation > 0) {
            if (INTERNAL_INJURY.abnormal(this.getCondition(INTERNAL_INJURY).getHiddenValue()))
                this.injuryHidden(INTERNAL_INJURY, -delta);
            else
                this.injury(INTERNAL_INJURY, -delta * Config.internal_food_healing);
            player.causeFoodExhaustion(delta * Config.internal_food_healing * 2);
        }
    }

    private void handleOpenWound(PlayerHealthCapability health) {
        if (!this.abnormalWithHidden(OPEN_WOUND)) return;
        this.handleCover(OPEN_WOUND, Config.bandage_open_wound_acc);

        if (isBandaged()) return;
        ConditionState bleed = this.getCondition(BLEED);
        bleed.setValue(bleed.getValue() + Mth.clamp(bleed.getValue() + this.getCondition(OPEN_WOUND).getValue() * Config.open_wound_bleed_ratio, BLEED.minValue, BLEED.maxValue));
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
        bloodVolume.setValue(bloodVolume.getValue() - this.getCondition(BLEED).getValue() * DELTA * Config.bleed_volume_ratio);
    }
}
