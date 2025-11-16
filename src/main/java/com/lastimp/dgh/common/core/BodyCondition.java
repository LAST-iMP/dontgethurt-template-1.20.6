package com.lastimp.dgh.common.core;

import com.lastimp.dgh.DontGetHurt;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

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
    public final ResourceLocation texture;

    private static List<BodyCondition> BLOOD_CONDITIONS;
    private static List<BodyCondition> HEALTH_SCANNER_CONDITIONS;
    private static List<BodyCondition> BLOOD_SCANNER_CONDITIONS;


    private final float EPS = 0.005f;

    BodyCondition() {
        this(0.5f, 0.0f, 1.0f, "container/condition_icons/burn");
    }

    BodyCondition(String path) {
        this(0.0f, 0.0f, 1.0f, path);
    }

    BodyCondition(float defaultValue, float minValue, float maxValue, String path) {
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.texture = new ResourceLocation(DontGetHurt.MODID, path);
    }

    public boolean abnormal(float value) {
        return true;
//        return defaultValue < value - EPS || defaultValue > value + EPS;
    }

    @Override
    public String toString() {
        return Component.translatable(this.name()).getString();
    }

    public static List<BodyCondition> bloodConditions() {
        if (BLOOD_CONDITIONS == null) {
            BLOOD_CONDITIONS = List.of(new BodyCondition[]{
                    BLOOD_VOLUME,
                    SEPSIS,
                    HEMOTRANSFUSION_SHOCK,
                    BLOOD_LOSS,
                    BLOOD_PRESSURE,
                    PH_LEVEL,
                    IMMUNITY
            });
        }
        return BLOOD_CONDITIONS;
    }

    public static List<BodyCondition> healthScannerConditions() {
        if (HEALTH_SCANNER_CONDITIONS == null) {
            HEALTH_SCANNER_CONDITIONS = List.of(new BodyCondition[]{
                    BURN,
                    INTERNAL_INJURY,
                    OPEN_WOUND,
                    INFECTION,
                    FOREIGN_OBJECT,

                    BANDAGED,
                    OINMENTED,
            });
        }
        return HEALTH_SCANNER_CONDITIONS;
    }

    public static List<BodyCondition> bloodScannerConditions() {
        if (BLOOD_SCANNER_CONDITIONS == null) {
            BLOOD_SCANNER_CONDITIONS = List.of(new BodyCondition[]{
                    BLOOD_VOLUME,
                    SEPSIS,
                    HEMOTRANSFUSION_SHOCK,
                    BLOOD_LOSS,
                    BLOOD_PRESSURE,
                    PH_LEVEL,
                    IMMUNITY
            });
        }
        return BLOOD_SCANNER_CONDITIONS;
    }

    public static BodyCondition[] allConditions() {
        return values();
    }
}


