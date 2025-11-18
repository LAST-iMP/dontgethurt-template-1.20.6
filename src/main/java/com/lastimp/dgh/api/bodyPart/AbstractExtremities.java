package com.lastimp.dgh.api.bodyPart;

import com.lastimp.dgh.common.core.player.PlayerHealthCapability;

public abstract class AbstractExtremities extends AbstractAnyBody {
    public AbstractExtremities() {
        super();
    }

    public AbstractExtremities(Void unused) {
        this();
    }

    @Override
    public void update(PlayerHealthCapability health) {
        super.update(health);
    }
}
