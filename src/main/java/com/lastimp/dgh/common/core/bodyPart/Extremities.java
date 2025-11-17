package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.client.player.PlayerHealthCapability;
import com.lastimp.dgh.common.core.Enums.BodyComponents;

public class Extremities extends AnyBody {
    public Extremities() {
        super();
    }

    public Extremities(Void unused) {
        this();
    }


    public static PlayerHealthCapability updateRightArm(PlayerHealthCapability health, PlayerHealthCapability nextTickHealth) {
        health = updateExtremities(health, nextTickHealth, BodyComponents.RIGHT_ARM);
        return health;
    }

    public static PlayerHealthCapability updateLeftArm(PlayerHealthCapability health, PlayerHealthCapability nextTickHealth) {
        health = updateExtremities(health, nextTickHealth, BodyComponents.LEFT_ARM);
        return health;
    }

    public static PlayerHealthCapability updateRightLeg(PlayerHealthCapability health, PlayerHealthCapability nextTickHealth) {
        health = updateExtremities(health, nextTickHealth, BodyComponents.RIGHT_LEG);
        return health;
    }

    public static PlayerHealthCapability updateLeftLeg(PlayerHealthCapability health, PlayerHealthCapability nextTickHealth) {
        health = updateExtremities(health, nextTickHealth, BodyComponents.LEFT_LEG);
        return health;
    }

    protected static PlayerHealthCapability updateExtremities(PlayerHealthCapability health, PlayerHealthCapability nextTickHealth, BodyComponents component) {
        health = updateAnyBody(health, nextTickHealth, component);
        return health;
    }
}
