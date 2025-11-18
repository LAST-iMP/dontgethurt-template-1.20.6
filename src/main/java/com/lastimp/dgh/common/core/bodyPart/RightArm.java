package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.api.bodyPart.AbstractExtremities;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;

public class RightArm extends AbstractExtremities {
    public RightArm() {
        super();
    }

    public RightArm(Void unused) {
        this();
    }


    public static PlayerHealthCapability updateRightArm(PlayerHealthCapability health, PlayerHealthCapability nextTickHealth) {
        health = updateExtremities(health, nextTickHealth, BodyComponents.RIGHT_ARM);
        return health;
    }
}
