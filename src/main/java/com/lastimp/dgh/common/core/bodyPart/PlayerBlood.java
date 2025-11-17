package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.client.player.PlayerHealthCapability;
import com.lastimp.dgh.common.core.Enums.BodyCondition;

import java.util.List;

import static com.lastimp.dgh.common.core.Enums.BodyCondition.*;

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
                    HEMOTRANSFUSION_SHOCK,
                    BLOOD_LOSS,
                    BLOOD_PRESSURE,
                    PH_LEVEL,
                    IMMUNITY
            });
        }
        return BLOOD_CONDITIONS;
    }

    public static PlayerHealthCapability updateBlood(PlayerHealthCapability health, PlayerHealthCapability nextTickHealth) {
        return health;
    }
}
