package com.lastimp.dgh.core;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.UnknownNullability;

import java.util.HashMap;
import java.util.Map;

public class AnyPart  implements IAnyPart {

    private final HashMap<AnyBodyCondition, Float> state = new HashMap<>();

    public AnyPart() {
        state.put(AnyBodyCondition.BURN, 0.0f);
        state.put(AnyBodyCondition.BLEED, 0.0f);
        state.put(AnyBodyCondition.INTERNAL_INJURY, 0.0f);
        state.put(AnyBodyCondition.OPEN_WOUND, 0.0f);
        state.put(AnyBodyCondition.INFECTION, 0.0f);
        state.put(AnyBodyCondition.FOREIGN_OBJECT, 0.0f);

        state.put(AnyBodyCondition.BANDAGED, 0.0f);
        state.put(AnyBodyCondition.OINMENTED, 0.0f);
    }

    @Override
    public float getCondition(AnyBodyCondition key) {
        return state.getOrDefault(key, 0.0f);
    }

    @Override
    public IAnyPart setCondition(AnyBodyCondition key, float value) {
        if (value < 0.0f) value = 0.0f;
        if (value > 1.0f) value = 1.0f;
        state.put(key, value);
        return this;
    }

    @Override
    public IAnyPart addCondition(AnyBodyCondition key, float value) {
        float newValue = getCondition(key) + value;
        return this.setCondition(key, newValue);
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<AnyBodyCondition, Float> e : state.entrySet()) {
            tag.putFloat(e.getKey().name(), e.getValue());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        if (nbt == null) return;
        for (AnyBodyCondition condition : AnyBodyCondition.values()) {
            if (nbt.contains(condition.name())) {
                state.put(condition, nbt.getFloat(condition.name()));
            } else {
                state.put(condition, 0.0f);
            }
        }
    }
}
