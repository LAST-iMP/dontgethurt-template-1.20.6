package com.lastimp.dgh.source.core.player;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.source.Register.ModCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DontGetHurt.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEventHandler {
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            LazyOptional<PlayerHealthCapability> oldHealth = event.getOriginal().getCapability(ModCapabilities.PLAYER_HEALTH);
            LazyOptional<PlayerHealthCapability> newHealth = event.getEntity().getCapability(ModCapabilities.PLAYER_HEALTH);
            if (oldHealth.isPresent() && newHealth.isPresent()) {
                newHealth.ifPresent((newCap) -> {
                    oldHealth.ifPresent((oldCap) -> {
                        newCap.deserializeNBT(oldCap.serializeNBT());
                    });
                });
            }
        }
    }
}
