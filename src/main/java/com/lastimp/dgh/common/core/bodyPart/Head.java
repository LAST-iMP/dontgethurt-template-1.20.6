package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.client.player.PlayerHealthCapability;
import com.lastimp.dgh.common.core.Enums.BodyComponents;

public class Head extends AnyBody{
    public Head() {
        super();
    }

    public Head(Void v) {
        this();
    }

    public static PlayerHealthCapability updateHead(PlayerHealthCapability health, PlayerHealthCapability nextTickHealth) {
        health = updateAnyBody(health, nextTickHealth, BodyComponents.HEAD);
        return health;
    }
}
