package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.api.bodyPart.AbstractExtremities;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;

public class RightLeg extends AbstractExtremities {
    public RightLeg() {
        super();
    }

    public RightLeg(Void unused) {
        this();
    }

    public static PlayerHealthCapability updateRightLeg(PlayerHealthCapability health, PlayerHealthCapability nextTickHealth) {
        health = updateExtremities(health, nextTickHealth, BodyComponents.RIGHT_LEG);
        return health;
    }
}
