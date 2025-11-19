package com.lastimp.dgh.common.item;

import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import com.lastimp.dgh.api.healingItems.AbstractDirectHealItems;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class Morphine extends AbstractDirectHealItems {

    public Morphine(Properties properties) {
        super(properties);
    }

    @Override
    public boolean heal(@NotNull ServerPlayer source, @NotNull ServerPlayer target) {
        return PlayerHealthCapability.getAndSet(source, health -> {

            return false;
        });
    }
}
