package com.lastimp.dgh.api.bodyPart;

import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import net.minecraft.world.entity.player.Player;

public abstract class AbstractExtremities extends AbstractVisibleBody {
    public AbstractExtremities() {
        super();
    }

    public AbstractExtremities(Void unused) {
        this();
    }

    @Override
    public AbstractBody update(PlayerHealthCapability health, Player player) {
        return super.update(health, player);
    }
}
