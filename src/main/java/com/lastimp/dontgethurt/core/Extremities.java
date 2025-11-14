package com.lastimp.dontgethurt.core;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public class Extremities extends AnyPart implements IExtermities {

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = super.serializeNBT(provider);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        super.deserializeNBT(provider, nbt);
    }
}
