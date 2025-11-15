package com.lastimp.dgh.core;

import net.minecraft.network.chat.Component;

public enum BodyCondition {
    //any body conditions
    BURN,
    BLEED,
    INTERNAL_INJURY,
    OPEN_WOUND,
    INFECTION,
    FOREIGN_OBJECT,

    BANDAGED,
    OINMENTED,

    // blood conditions
    BLOOD_VOLUME,
    SEPSIS,
    HEMOTRANSFUSION_SHOCK,
    BLOOD_LOSS,
    BLOOD_PRESSURE,
    PH_LEVEL,
    IMMUNITY
    ;

    public final float defaultValue;
    public final float minValue;
    public final float maxValue;

    private final float EPS = 0.005f;

    BodyCondition() {
        this(0.0f, 0.0f, 1.0f);
    }

    BodyCondition(float defaultValue, float minValue, float maxValue) {
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public boolean abnormal(float value) {
        return true;
//        return defaultValue < value - EPS || defaultValue > value + EPS;
    }

    @Override
    public String toString() {
        return Component.translatable(this.name()).getString();
    }

    public static BodyCondition[] anyBodyConditions() {
        return new BodyCondition[]{
                BURN,
                BLEED,
                INTERNAL_INJURY,
                OPEN_WOUND,
                INFECTION,
                FOREIGN_OBJECT,
                BANDAGED,
                OINMENTED
        };
    }

    public static BodyCondition[] bloodConditions() {
        return new BodyCondition[]{
                BLOOD_VOLUME,
                SEPSIS,
                HEMOTRANSFUSION_SHOCK,
                BLOOD_LOSS,
                BLOOD_PRESSURE,
                PH_LEVEL,
                IMMUNITY
        };
    }
}


