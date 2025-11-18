package com.lastimp.dgh.api.bodyPart;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public class ConditionState implements INBTSerializable<CompoundTag> {
    public boolean isInjury;
    public float value;
    public float hiddenValue;
    public int stateLevel;

    public ConditionState() {
        this(true, 0f, 0f, 0);
    }

    public ConditionState(float defaultValue) {
        this(true, defaultValue, 0f, 0);
    }

    public ConditionState(boolean isInjury, float value, float hiddenValue, int stateLevel) {
        this.isInjury = isInjury;
        this.value = value;
        this.hiddenValue = hiddenValue;
        this.stateLevel = stateLevel;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("isInjury", this.isInjury);
        tag.putFloat("value", this.value);
        tag.putFloat("hiddenValue", this.hiddenValue);
        tag.putInt("stateLevel", this.stateLevel);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        this.isInjury = tag.getBoolean("isInjury");
        this.value = tag.getFloat("value");
        this.hiddenValue = tag.getFloat("hiddenValue");
        this.stateLevel = tag.getInt("stateLevel");
    }
}
