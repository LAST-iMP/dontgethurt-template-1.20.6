package com.lastimp.dgh.client.player;

import com.lastimp.dgh.common.core.BodyComponents;
import com.lastimp.dgh.common.core.BodyCondition;
import com.lastimp.dgh.common.core.IAbstractBody;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface IPlayerHealthCapability extends INBTSerializable<CompoundTag> {
    boolean isOnBed();

    IAbstractBody setComponent(BodyComponents component, BodyCondition condition, float value);
    IAbstractBody addComponent(BodyComponents component, BodyCondition condition, float value);
    IAbstractBody getComponent(BodyComponents component);
    float getComponentCondition(BodyComponents component, BodyCondition condition);
}
