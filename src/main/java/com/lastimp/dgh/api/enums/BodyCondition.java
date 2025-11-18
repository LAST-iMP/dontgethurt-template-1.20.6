package com.lastimp.dgh.api.enums;

import com.lastimp.dgh.DontGetHurt;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static com.lastimp.dgh.DontGetHurt.EPS;

public enum BodyCondition {
    //any body conditions
    BURN                ("烧伤", 0.002f, 0.2f),
    INTERNAL_INJURY     ("内伤", 0.002f, 1.0f),
    OPEN_WOUND          ("开放伤口", 0.002f, 0.1f),
    BLEED               ("出血", 0.0f, 0.0f),
    INFECTION           ("感染", 0.0f, 0.0f),
    FOREIGN_OBJECT      ("体内异物", 0.0f, 0.0f),

    BANDAGED            ("已包扎", 0.01f, 1.0f),
    BANDAGED_DIRTY      ("被污染的绷带", 0.0f, 1.0f),
    OINMENTED           ("已涂药", 0.0f, 0.0f),

    // blood conditions
    BLOOD_VOLUME        ("血容量", 1.0f, 0.0f, 2.0f, 20.0f, 0.05f, 1.0f, null),
    SEPSIS              ("败血症", 0.0f, 0.0f),
    HEMOTRANSFUSION     ("输血性休克", 0.0f, 0.0f),
    BLOOD_LOSS          ("失血", 0.0f, 0.0f),
    BLOOD_PRESSURE      ("血压", 0.0f, 0.0f),
    PH_LEVEL            ("酸碱性", 0.0f, 0.0f),
    IMMUNITY            ("免疫力", 0.0f, 0.0f)
    ;

    public final String translation;
    public final float defaultValue;
    public final float minValue;
    public final float maxValue;
    public final float factor;

    public final float healingSpeed;
    public final float healingTS;

    public final ResourceLocation texture;

    BodyCondition(String translation, float healingSpeed, float selfHealingTSString) {
        this(translation, healingSpeed, selfHealingTSString, "container/condition_icons/burn");
    }

    BodyCondition(String translation, float healingSpeed, float selfHealingTSString, String path) {
        this(translation, 0.0f, 0.0f, 1.0f, 20.0f, healingSpeed, selfHealingTSString, path);
    }

    BodyCondition(String translation, float defaultValue, float minValue, float maxValue, float factor, float healingSpeed, float healingTS, String path) {
        this.translation = translation;
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.factor = factor;
        this.healingSpeed = healingSpeed;
        this.healingTS = healingTS;
        this.texture = path == null ? null : new ResourceLocation(DontGetHurt.MODID, path);
    }

    public boolean abnormal(float value) {
        return defaultValue < value - EPS || defaultValue > value + EPS;
    }

    @Override
    public String toString() {
        return getComponent().getString();
    }

    public Component getComponent() {
        return Component.translatable(this.name());
    }

    public static BodyCondition[] allConditions() {
        return values();
    }
}


