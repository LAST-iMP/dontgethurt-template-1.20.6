package com.lastimp.dgh.common.Register;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.client.player.PlayerHealthProvider;
import com.lastimp.dgh.network.ClientPayloadHandler;
import com.lastimp.dgh.network.DataPack.MySelectBodyData;
import com.lastimp.dgh.network.DataPack.MySynBodyConditionData;
import com.lastimp.dgh.network.ServerPayloadHandler;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = DontGetHurt.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBus {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(ModCapabilities.PLAYER_HEALTH_HANDLER,
                EntityType.PLAYER,
                new PlayerHealthProvider()
                );
    }

    @SubscribeEvent
    public static void registerNetwork(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(DontGetHurt.MODID);
        registrar.playBidirectional(
                MySelectBodyData.TYPE,
                MySelectBodyData.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientPayloadHandler::handleSelectBodyData,
                        ServerPayloadHandler::handleSelectBodyData
                )
        );
        registrar.playBidirectional(
                MySynBodyConditionData.TYPE,
                MySynBodyConditionData.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientPayloadHandler::handleSynBodyConditionData,
                        ServerPayloadHandler::handleSynBodyConditionData
                )
        );
    }

}
