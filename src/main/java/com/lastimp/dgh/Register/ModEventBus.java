package com.lastimp.dgh.Register;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.player.PlayerHealthProvider;
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
