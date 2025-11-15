package com.lastimp.dgh.core;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface IAbstractBody extends INBTSerializable<CompoundTag> {
    float getCondition(BodyCondition key);
    IAbstractBody setCondition(BodyCondition key, float value);
    IAbstractBody addCondition(BodyCondition key, float value);
}
