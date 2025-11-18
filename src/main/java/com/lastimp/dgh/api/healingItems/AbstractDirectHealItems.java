package com.lastimp.dgh.api.healingItems;

import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDirectHealItems extends AbstractHealingItem{
    public AbstractDirectHealItems(Properties properties) {
        super(properties);
    }

    public abstract boolean heal(@NotNull ServerPlayer player);
}
