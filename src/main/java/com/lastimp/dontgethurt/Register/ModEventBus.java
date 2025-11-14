package com.lastimp.dontgethurt.Register;

import com.lastimp.dontgethurt.DontGetHurt;
import com.lastimp.dontgethurt.player.PlayerHealthProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = DontGetHurt.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBus {

    @SubscribeEvent
    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(ModCapabilities.PLAYER_HEALTH_HANDLER,
                EntityType.PLAYER,
                new PlayerHealthProvider()
                );
    }

}
