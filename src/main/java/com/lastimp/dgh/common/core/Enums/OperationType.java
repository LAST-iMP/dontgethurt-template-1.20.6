package com.lastimp.dgh.common.core.Enums;

import net.minecraft.network.chat.Component;

public enum OperationType {
    HEALTH_SCANN,
    BLOOD_SCANN
    ;
    @Override
    public String toString() {
        return Component.translatable(this.name()).getString();
    }
}
