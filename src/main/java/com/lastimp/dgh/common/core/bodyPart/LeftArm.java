package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.api.bodyPart.AbstractExtremities;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;

public class LeftArm extends AbstractExtremities {
    public LeftArm() {
        super();
    }

    public LeftArm(Void unused) {
        this();
    }

    @Override
    public void update(PlayerHealthCapability health) {
        super.update(health);
    }
}
