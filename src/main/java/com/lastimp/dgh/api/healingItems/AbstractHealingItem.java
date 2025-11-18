package com.lastimp.dgh.api.healingItems;

import net.minecraft.world.item.Item;

public abstract class AbstractHealingItem extends Item {
//    protected final HashSet<ConditionState> healingEffects = new HashSet<>();

    public AbstractHealingItem(Properties properties) {
        super(properties);
    }

//    public HashSet<ConditionState> getHealing() {
//        return this.healingEffects;
//    }
}
