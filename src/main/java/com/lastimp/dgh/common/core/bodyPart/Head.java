package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.api.bodyPart.AbstractAnyBody;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import com.lastimp.dgh.api.enums.BodyComponents;

public class Head extends AbstractAnyBody {
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
