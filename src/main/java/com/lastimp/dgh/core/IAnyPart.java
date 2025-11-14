package com.lastimp.dgh.core;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface IAnyPart extends INBTSerializable<CompoundTag> {
    float getCondition(AnyBodyCondition key);
    IAnyPart setCondition(AnyBodyCondition key, float value);
    IAnyPart addCondition(AnyBodyCondition key, float value);
}
