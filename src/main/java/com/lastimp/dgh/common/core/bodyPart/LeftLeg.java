package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.api.bodyPart.AbstractBody;
import com.lastimp.dgh.api.bodyPart.AbstractExtremities;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import net.minecraft.world.entity.player.Player;

public class LeftLeg extends AbstractExtremities {
    public LeftLeg() {
        super();
    }

    public LeftLeg(Void unused) {
        this();
    }

    @Override
    public AbstractBody update(PlayerHealthCapability health, Player player) {
        return super.update(health, player);
    }
}
