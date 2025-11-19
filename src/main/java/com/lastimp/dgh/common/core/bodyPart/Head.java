package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.api.bodyPart.AbstractBody;
import com.lastimp.dgh.api.bodyPart.AbstractVisibleBody;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;

public class Head extends AbstractVisibleBody {
    public Head() {
        super();
    }

    @Override
    public AbstractBody update(PlayerHealthCapability health) {
        return super.update(health);
    }

    public Head(Void v) {
        this();
    }
}
