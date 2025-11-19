package com.lastimp.dgh.common.item;

import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.api.healingItems.AbstractDirectHealItems;
import com.lastimp.dgh.common.Register.ModItems;
import com.lastimp.dgh.common.core.bodyPart.PlayerBlood;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static com.lastimp.dgh.api.enums.BodyCondition.BLOOD_VOLUME;

public class BloodPacksEmpty extends AbstractDirectHealItems {
    public BloodPacksEmpty(Properties properties) {
        super(properties);
    }

    @Override
    public boolean heal(@NotNull ServerPlayer source, @NotNull ServerPlayer target) {
        return PlayerHealthCapability.getAndSet(target, health -> {
            PlayerBlood blood = (PlayerBlood) health.getComponent(BodyComponents.BLOOD);
            float currCondition = blood.getConditionValue(BLOOD_VOLUME);
            if (currCondition < BLOOD_VOLUME.minValue + 0.3f) return false;

            blood.healing(BLOOD_VOLUME, -0.25f);

            ItemStack stack = new ItemStack(ModItems.BLOOD_PACK.get());
            if (!source.addItem(stack)) {
                source.drop(stack, true, true);
            }
            return true;
        });
    }
}
