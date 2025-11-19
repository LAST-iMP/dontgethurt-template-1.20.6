package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.api.bodyPart.AbstractBody;
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

    @Override
    public AbstractBody update(PlayerHealthCapability health) {
        return super.update(health);
    }
}
