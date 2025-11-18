package com.lastimp.dgh.common.core.player;

import com.lastimp.dgh.api.bodyPart.AbstractBody;
import com.lastimp.dgh.common.Register.ModCapabilities;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.common.core.bodyPart.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.function.Function;

public class PlayerHealthCapability implements INBTSerializable<CompoundTag> {
    private final WholeBody body = new WholeBody();

    public static PlayerHealthCapability get(Player player) {
        return player.getData(ModCapabilities.PLAYER_HEALTH);
    }

    public static void set(Player player, PlayerHealthCapability capability) {
        player.setData(ModCapabilities.PLAYER_HEALTH, capability);
    }

    public static <T> T getAndSet(Player player, Function<PlayerHealthCapability, T> function) {
        PlayerHealthCapability health = (PlayerHealthCapability) PlayerHealthCapability.get(player);
        T result = function.apply(health);
        PlayerHealthCapability.set(player, health);
        return result;
    }

    public AbstractBody getComponent(BodyComponents component) {
        return this.body.getComponent(component);
    }

    public PlayerHealthCapability update(PlayerHealthCapability nextTickHealth) {
        return WholeBody.update(this, nextTickHealth);
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return this.body.serializeNBT(provider);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        if (nbt == null) return;
        this.body.deserializeNBT(provider, nbt);
    }
}
