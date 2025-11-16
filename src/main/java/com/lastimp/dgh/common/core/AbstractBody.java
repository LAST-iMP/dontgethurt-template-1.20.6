package com.lastimp.dgh.common.core;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.UnknownNullability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBody implements IAbstractBody{

    private final HashMap<BodyCondition, Float> state = new HashMap<>();

    public AbstractBody() {
        for (BodyCondition condition : this.getBodyConditions()) {
            state.put(condition, condition.defaultValue);
        }
    }

    public abstract List<BodyCondition> getBodyConditions();

    @Override
    public float getCondition(BodyCondition key) {
        return state.getOrDefault(key, 0.0f);
    }

    @Override
    public IAbstractBody setCondition(BodyCondition key, float value) {
        state.put(key, Mth.clamp(value, key.minValue, key.maxValue));
        return this;
    }

    @Override
    public IAbstractBody addCondition(BodyCondition key, float value) {
        float newValue = this.getCondition(key) + value;
        return this.setCondition(key, newValue);
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<BodyCondition, Float> e : state.entrySet()) {
            tag.putFloat(e.getKey().name(), e.getValue());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        if (nbt == null) return;
        for (BodyCondition condition : this.getBodyConditions()) {
            if (nbt.contains(condition.name())) {
                state.put(condition, nbt.getFloat(condition.name()));
            } else {
                state.put(condition, condition.defaultValue);
            }
        }
    }
}
