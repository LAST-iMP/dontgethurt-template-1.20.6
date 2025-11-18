package com.lastimp.dgh.common.item;

import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.api.healingItems.AbstractDirectHealItems;
import com.lastimp.dgh.common.core.bodyPart.PlayerBlood;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import static com.lastimp.dgh.api.enums.BodyCondition.*;

public class BloodPacks extends AbstractDirectHealItems {

    public BloodPacks(Properties properties) {
        super(properties);
    }

    @Override
    public boolean heal(@NotNull ServerPlayer player) {
        return PlayerHealthCapability.getAndSet(player, health -> {
            PlayerBlood blood = (PlayerBlood) health.getComponent(BodyComponents.BLOOD);
            float currCondition = blood.getConditionValue(BLOOD_VOLUME);
            if (currCondition >= 2.0f) return false;

            blood.addConditionValue(BLOOD_VOLUME, 0.25f);
            return true;
        });
    }
}
