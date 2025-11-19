package com.lastimp.dgh.api.healingItems;

import com.lastimp.dgh.api.enums.BodyComponents;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public abstract class AbstractPartlyHealItem extends AbstractHealingItem{
    public AbstractPartlyHealItem(Properties properties) {
        super(properties);
    }

    public boolean heal(@NotNull ServerPlayer source, @NotNull ServerPlayer target, BodyComponents component) {
        if (component == null) return false;
        return this.healOn(source, target, component);
    }

    protected abstract boolean healOn(@NotNull ServerPlayer source, @NotNull ServerPlayer target, BodyComponents component);

    public abstract HashSet<BodyComponents> getApplicableComponents();
}
