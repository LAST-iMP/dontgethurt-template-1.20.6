package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.api.bodyPart.AbstractExtremities;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;

public class LeftLeg extends AbstractExtremities {
    public LeftLeg() {
        super();
    }

    public LeftLeg(Void unused) {
        this();
    }

    public static PlayerHealthCapability updateLeftLeg(PlayerHealthCapability health, PlayerHealthCapability nextTickHealth) {
        health = updateExtremities(health, nextTickHealth, BodyComponents.LEFT_LEG);
        return health;
    }
}
