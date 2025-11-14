package com.lastimp.dontgethurt.player;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

public class PlayerHealthProvider implements ICapabilityProvider<Player, Void, IPlayerHealthCapability>, INBTSerializable<CompoundTag> {
    private final IPlayerHealthCapability impl = new PlayerHealthCapability();

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return impl.serializeNBT(provider);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        impl.deserializeNBT(provider, nbt);
    }

    @Override
    public boolean equals(Object o) {
        // optional helper if needed
        return this == o;
    }

    @Override
    public @Nullable IPlayerHealthCapability getCapability(Player o, Void unused) {
        return this.impl;
    }
}