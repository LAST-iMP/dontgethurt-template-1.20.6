package com.lastimp.dgh.player;

import com.lastimp.dgh.core.AnyPart;
import com.lastimp.dgh.core.BodyComponents;
import com.lastimp.dgh.core.AnyBodyCondition;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface IPlayerHealthCapability extends INBTSerializable<CompoundTag> {
    boolean isOnBed();

    AnyPart setComponent(BodyComponents component, AnyBodyCondition condition, float value);
    AnyPart addComponent(BodyComponents component, AnyBodyCondition condition, float value);
    AnyPart getComponent(BodyComponents component);
    float getComponentCondition(BodyComponents component, AnyBodyCondition condition);
}
