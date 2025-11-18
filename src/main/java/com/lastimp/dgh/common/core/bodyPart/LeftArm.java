package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.api.bodyPart.AbstractExtremities;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;

public class LeftArm extends AbstractExtremities {
    public LeftArm() {
        super();
    }

    public LeftArm(Void unused) {
        this();
    }

    public static PlayerHealthCapability updateLeftArm(PlayerHealthCapability health, PlayerHealthCapability nextTickHealth) {
        health = updateExtremities(health, nextTickHealth, BodyComponents.LEFT_ARM);
        return health;
    }
}
