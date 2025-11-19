package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.api.bodyPart.AbstractBody;
import com.lastimp.dgh.api.bodyPart.AbstractExtremities;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;

public class LeftLeg extends AbstractExtremities {
    public LeftLeg() {
        super();
    }

    public LeftLeg(Void unused) {
        this();
    }

    @Override
    public AbstractBody update(PlayerHealthCapability health) {
        return super.update(health);
    }
}
