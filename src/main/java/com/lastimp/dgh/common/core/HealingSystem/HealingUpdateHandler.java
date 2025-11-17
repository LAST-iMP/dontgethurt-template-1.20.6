package com.lastimp.dgh.common.core.HealingSystem;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.client.player.PlayerHealthCapability;
import com.lastimp.dgh.common.core.bodyPart.WholeBody;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;


@EventBusSubscriber(modid = DontGetHurt.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.DEDICATED_SERVER)
public class HealingUpdateHandler {
    private static final PlayerHealthCapability nextTickHealth = new PlayerHealthCapability();

    @SubscribeEvent
    public static void onHealingUpdate(PlayerTickEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();

        PlayerHealthCapability.getAndSet(player, health -> {
            nextTickHealth.deserializeNBT(player.registryAccess(), health.serializeNBT(player.registryAccess()));
            health = WholeBody.update(health, nextTickHealth);
            health.deserializeNBT(player.registryAccess(), nextTickHealth.serializeNBT(player.registryAccess()));
            return health;
        });
    }
}
