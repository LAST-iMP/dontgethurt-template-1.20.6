package com.lastimp.dgh.api.bodyPart;

import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import com.lastimp.dgh.api.enums.BodyComponents;

public abstract class AbstractExtremities extends AbstractAnyBody {
    public AbstractExtremities() {
        super();
    }

    public AbstractExtremities(Void unused) {
        this();
    }

    protected static PlayerHealthCapability updateExtremities(PlayerHealthCapability health, PlayerHealthCapability nextTickHealth, BodyComponents component) {
        health = updateAnyBody(health, nextTickHealth, component);
        return health;
    }
}
