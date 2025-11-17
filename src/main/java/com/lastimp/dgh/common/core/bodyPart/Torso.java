package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.client.player.PlayerHealthCapability;
import com.lastimp.dgh.common.core.Enums.BodyComponents;

public class Torso extends AnyBody{
    public Torso() {
        super();
    }

    public Torso (Void v) {
        this();
    }

    public static PlayerHealthCapability updateTorso(PlayerHealthCapability health, PlayerHealthCapability nextTickHealth) {
        health = updateAnyBody(health, nextTickHealth, BodyComponents.TORSO);
        return health;
    }
}
