package com.lastimp.dgh.common.core.healingSystem;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;


@EventBusSubscriber(modid = DontGetHurt.MODID, bus = EventBusSubscriber.Bus.GAME)
public class HealingUpdateHandler {
    private static final PlayerHealthCapability nextTickHealth = new PlayerHealthCapability();

    @SubscribeEvent
    public static void onHealingUpdate(PlayerTickEvent.Pre event) {
        if (event.getEntity().level().isClientSide) return;

        ServerPlayer player = (ServerPlayer) event.getEntity();
        PlayerHealthCapability.getAndSet(player, health -> {
            nextTickHealth.deserializeNBT(player.registryAccess(), health.serializeNBT(player.registryAccess()));
            health = health.update(nextTickHealth);
            health.deserializeNBT(player.registryAccess(), nextTickHealth.serializeNBT(player.registryAccess()));
            return health;
        });
    }
}
