package com.lastimp.dgh.common.core.HealingSystem;

import com.lastimp.dgh.common.core.Enums.BodyComponents;
import net.minecraft.world.item.Item;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractHealingItem extends Item {
//    protected final HashSet<HealingEffect> healingEffects = new HashSet<>();

    public AbstractHealingItem(Properties properties) {
        super(properties);
    }

//    public HashSet<HealingEffect> getHealing() {
//        return this.healingEffects;
//    }
}
