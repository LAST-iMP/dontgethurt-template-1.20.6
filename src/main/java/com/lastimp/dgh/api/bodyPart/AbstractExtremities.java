package com.lastimp.dgh.api.bodyPart;

import com.lastimp.dgh.common.core.player.PlayerHealthCapability;

public abstract class AbstractExtremities extends AbstractVisibleBody {
    public AbstractExtremities() {
        super();
    }

    public AbstractExtremities(Void unused) {
        this();
    }

    @Override
    public AbstractBody update(PlayerHealthCapability health) {
        return super.update(health);
    }
}
