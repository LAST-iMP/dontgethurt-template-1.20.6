package com.lastimp.dgh.common.item;

import com.lastimp.dgh.client.player.PlayerHealthCapability;
import com.lastimp.dgh.common.core.Enums.BodyComponents;
import com.lastimp.dgh.common.core.HealingSystem.AbstractDirectHealItems;
import com.lastimp.dgh.common.core.HealingSystem.HealingEffect;
import com.lastimp.dgh.common.core.bodyPart.IAbstractBody;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

import static com.lastimp.dgh.common.core.Enums.BodyCondition.*;

public class Morphine extends AbstractDirectHealItems {

    public Morphine(Properties properties) {
        super(properties);
    }

    @Override
    public boolean heal(@NotNull ServerPlayer player) {
        return PlayerHealthCapability.getAndSet(player, health -> {
//            IAbstractBody body = health.getComponent(BodyComponents.BLOOD);
//            float currCondition = body.getCondition(BLOOD_VOLUME);
//            if (currCondition >= 2.0f) return false;
//
//            body.addCondition(BLOOD_VOLUME, 0.25f);
//            return true;
            return false;
        });
    }
}
