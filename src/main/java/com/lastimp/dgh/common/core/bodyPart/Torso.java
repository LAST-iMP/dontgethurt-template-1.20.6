package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.api.bodyPart.AbstractAnyBody;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import com.lastimp.dgh.api.enums.BodyComponents;

public class Torso extends AbstractAnyBody {
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
