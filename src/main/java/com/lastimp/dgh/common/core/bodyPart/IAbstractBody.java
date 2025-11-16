package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.common.core.Enums.BodyCondition;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface IAbstractBody extends INBTSerializable<CompoundTag> {
    boolean hasCondition(BodyCondition key);
    float getCondition(BodyCondition key);
    IAbstractBody setCondition(BodyCondition key, float value);
    IAbstractBody addCondition(BodyCondition key, float value);
}
