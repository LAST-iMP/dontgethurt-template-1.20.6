package com.lastimp.dgh.api.healingItems;

import com.lastimp.dgh.api.enums.BodyComponents;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public abstract class AbstractPartlyHealItem extends AbstractHealingItem{
    public AbstractPartlyHealItem(Properties properties) {
        super(properties);
    }

    public boolean heal(ServerPlayer player, BodyComponents component) {
        if (component == null) return false;
        return this.healOn(player, component);
    }

    protected abstract boolean healOn(@NotNull ServerPlayer player, BodyComponents component);

    public abstract HashSet<BodyComponents> getApplicableComponents();
}
