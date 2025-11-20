package com.lastimp.dgh.api.enums;

import com.lastimp.dgh.DontGetHurt;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static com.lastimp.dgh.DontGetHurt.EPS;

public enum BodyCondition {
    //any body conditions
    BURN                ("烧伤", 1.0f / 500, 0.2f, "container/condition_icons/burn"),
    INTERNAL_INJURY     ("内伤", 1.0f / 500, 1.0f, "container/condition_icons/internal_injury"),
    OPEN_WOUND          ("开放伤", 1.0f / 500, 0.5f, "container/condition_icons/open_wound"),
    BLEED               ("出血", 0.0f, 0.0f, "container/condition_icons/bleeding"),
    INFECTION           ("感染", 0.0f, 0.0f),
    FOREIGN_OBJECT      ("体内异物", 0.0f, 0.0f),

    BANDAGED            ("绷带包扎", 0.01f, 1.0f, 0xFF7DFF49, "container/condition_icons/bandage"),
    BANDAGED_DIRTY      ("脏绷带", 0.0f, 1.0f, "container/condition_icons/bandage_dirty"),
    OINMENTED           ("药膏涂抹", 0.0f, 0.0f),

    // blood conditions
    BLOOD_VOLUME        ("血容量", 0.02f, 0.0f, 1.0f, 0.0f, 2.0f, 20.0f, null),
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
    public final int color;

    BodyCondition(String translation, float healingSpeed, float healingTS) {
        this(translation, healingSpeed, healingTS,"container/condition_icons/burn");
    }

    BodyCondition(String translation, float healingSpeed, float healingTS, String path) {
        this(translation, healingSpeed, healingTS, 0.0f, 0.0f, 1.0f, 20.0f, path);
    }

    BodyCondition(String translation, float healingSpeed, float healingTS, int color, String path) {
        this(translation, healingSpeed, healingTS, 0.0f, 0.0f, 1.0f, 20.0f, color, path);
    }

    BodyCondition(String translation, float healingSpeed, float healingTS, float defaultValue, float minValue, float maxValue, float factor, String path) {
        this(translation, healingSpeed, healingTS, defaultValue, minValue, maxValue, factor, 0xFFFF7471, path);
    }

    BodyCondition(String translation, float healingSpeed, float healingTS, float defaultValue, float minValue, float maxValue, float factor, int color, String path) {
        this.translation = translation;
        this.healingSpeed = healingSpeed;
        this.healingTS = healingTS;
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.factor = factor;
        this.color = color;
        this.texture = path == null ? null : new ResourceLocation(DontGetHurt.MODID, path);
    }

    public boolean abnormal(float value) {
        return defaultValue < value - EPS || defaultValue > value + EPS;
    }

    public boolean isInjury() {
        return this == BURN || this == INTERNAL_INJURY || this == OPEN_WOUND || this == BLEED ||
                this == INFECTION || this == FOREIGN_OBJECT || this == BANDAGED_DIRTY;
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


