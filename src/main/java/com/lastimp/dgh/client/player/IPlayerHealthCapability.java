package com.lastimp.dgh.client.player;

import com.lastimp.dgh.common.core.Enums.BodyComponents;
import com.lastimp.dgh.common.core.Enums.BodyCondition;
import com.lastimp.dgh.common.core.bodyPart.IAbstractBody;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface IPlayerHealthCapability extends INBTSerializable<CompoundTag> {
    IAbstractBody getComponent(BodyComponents component);
}
