package com.lastimp.dgh.core;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.UnknownNullability;

import java.util.HashMap;
import java.util.Map;

public class AnyBody extends AbstractBody {
    @Override
    public BodyCondition[] getBodyConditions() {
        return BodyCondition.anyBodyConditions();
    }
}
