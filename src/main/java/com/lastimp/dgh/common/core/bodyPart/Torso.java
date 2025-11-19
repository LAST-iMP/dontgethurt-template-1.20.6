package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.api.bodyPart.AbstractBody;
import com.lastimp.dgh.api.bodyPart.AbstractVisibleBody;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;

public class Torso extends AbstractVisibleBody {
    public Torso() {
        super();
    }

    public Torso (Void v) {
        this();
    }

    @Override
    public AbstractBody update(PlayerHealthCapability health) {
        return super.update(health);
    }

}
