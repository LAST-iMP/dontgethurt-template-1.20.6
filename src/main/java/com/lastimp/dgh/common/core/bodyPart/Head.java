package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.api.bodyPart.AbstractAnyBody;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import com.lastimp.dgh.api.enums.BodyComponents;

public class Head extends AbstractAnyBody {
    public Head() {
        super();
    }

    @Override
    public void update(PlayerHealthCapability health) {
        super.update(health);
    }

    public Head(Void v) {
        this();
    }
}
