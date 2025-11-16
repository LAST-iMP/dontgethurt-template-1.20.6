package com.lastimp.dgh.common.core;


import net.minecraft.network.chat.Component;

public enum BodyComponents {
    LEFT_ARM,
    RIGHT_ARM,
    LEFT_LEG,
    RIGHT_LEG,
    HEAD,
    TORSO,

    BLOOD;

    @Override
    public String toString() {
        return Component.translatable(this.name()).getString();
    }
}