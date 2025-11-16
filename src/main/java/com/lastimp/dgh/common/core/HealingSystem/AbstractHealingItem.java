package com.lastimp.dgh.common.core.HealingSystem;

import com.lastimp.dgh.common.core.bodyPart.AbstractBody;
import net.minecraft.world.item.Item;

import java.util.HashSet;

public abstract class AbstractHealingItem extends Item {
    protected final HashSet<HealingAbility> healingAbilities = new HashSet<>();

    public AbstractHealingItem(Properties properties) {
        super(properties);
        this.init();
    }

    protected abstract void init();

    public HashSet<HealingAbility> getHealing() {
        return this.healingAbilities;
    }

    public boolean heal(AbstractBody body) {
        boolean success = false;
        for (HealingAbility ability : getHealing()) {
            if (!body.hasCondition(ability.condition())) continue;

            success = true;
            float currentLevel = body.getCondition(ability.condition());
            if (currentLevel <= ability.threshold()) {
                body.addCondition(ability.condition(), -ability.healingAmount());
            } else {
                body.addCondition(ability.condition(), -ability.healingAmount() * ability.factorAfterThreshold());
            }
        }
        return success;
    }
}
