package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.api.bodyPart.AbstractBody;
import com.lastimp.dgh.api.bodyPart.ConditionState;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import com.lastimp.dgh.api.enums.BodyCondition;
import net.minecraft.util.Mth;

import java.util.List;

import static com.lastimp.dgh.DontGetHurt.DELTA;
import static com.lastimp.dgh.DontGetHurt.EPS;
import static com.lastimp.dgh.api.enums.BodyCondition.*;

public class PlayerBlood extends AbstractBody {
    private static List<BodyCondition> BLOOD_CONDITIONS;

    public PlayerBlood() {
        super();
    }

    public PlayerBlood(Void v) {
        this();
    }

    @Override
    public List<BodyCondition> getBodyConditions() {
        if (BLOOD_CONDITIONS == null) {
            BLOOD_CONDITIONS = List.of(new BodyCondition[]{
                    BLOOD_VOLUME,
                    SEPSIS,
                    HEMOTRANSFUSION,
                    BLOOD_LOSS,
                    BLOOD_PRESSURE,
                    PH_LEVEL,
                    IMMUNITY
            });
        }
        return BLOOD_CONDITIONS;
    }

    @Override
    public AbstractBody update(PlayerHealthCapability health) {
        this.handleBloodVolume(health);
        return this;
    }

    private void handleBloodVolume(PlayerHealthCapability health) {
        if (!this.abnormalWithHidden(BLOOD_VOLUME)) return;
        if (this.isBleeding(health)) return;

        ConditionState state = this.getCondition(BLOOD_VOLUME);
        if (state.getValue() >= BLOOD_VOLUME.defaultValue + EPS)
            state.setValue(Mth.clamp(state.getValue() - BLOOD_VOLUME.healingSpeed * DELTA, BLOOD_VOLUME.minValue, BLOOD_VOLUME.maxValue));
        else
            state.setValue(Mth.clamp(state.getValue() + BLOOD_VOLUME.healingSpeed * DELTA, BLOOD_VOLUME.minValue, BLOOD_VOLUME.maxValue));
    }

    private boolean isBleeding(PlayerHealthCapability health) {
        for (AbstractBody body : health.visibleParts()) {
            if (BLEED.abnormal(body.getConditionValue(BLEED)))
                return true;
        }
        return false;
    }
}
