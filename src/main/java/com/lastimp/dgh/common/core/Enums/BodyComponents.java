package com.lastimp.dgh.common.core.Enums;


import net.minecraft.network.chat.Component;

public enum BodyComponents {
    LEFT_ARM,
    RIGHT_ARM,
    LEFT_LEG,
    RIGHT_LEG,
    HEAD,
    TORSO,

    BLOOD,

    WHOLE_BODY;

    @Override
    public String toString() {
        return Component.translatable(this.name()).getString();
    }

    public static BodyComponents random() {
        return values()[(int) (Math.random() * 6)];
    }
}