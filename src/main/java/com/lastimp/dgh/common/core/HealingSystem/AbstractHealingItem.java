package com.lastimp.dgh.common.core.HealingSystem;

import com.lastimp.dgh.common.core.Enums.BodyComponents;
import com.lastimp.dgh.common.core.bodyPart.AbstractBody;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class AbstractHealingItem extends Item {
    protected final HashSet<HealingAbility> healingAbilities = new HashSet<>();

    public AbstractHealingItem(Properties properties) {
        super(properties);
        this.init();
    }

    protected abstract void init();

    protected Set<BodyComponents> getApplicableComponents() {
        return new HashSet<>();
    }

    public boolean heal(UUID id, BodyComponents component) {
        if (component != null && !this.getApplicableComponents().contains(component)) return false;
//        PacketDistributor
        return true;
    }

    public HashSet<HealingAbility> getHealing() {
        return this.healingAbilities;
    }
}
