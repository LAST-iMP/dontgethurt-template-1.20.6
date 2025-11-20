package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.api.bodyPart.AbstractBody;
import com.lastimp.dgh.api.bodyPart.AbstractVisibleBody;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import net.minecraft.world.entity.player.Player;

public class Head extends AbstractVisibleBody {
    public Head() {
        super();
    }

    @Override
    public AbstractBody update(PlayerHealthCapability health, Player player) {
        return super.update(health, player);
    }

    public Head(Void v) {
        this();
    }
}
