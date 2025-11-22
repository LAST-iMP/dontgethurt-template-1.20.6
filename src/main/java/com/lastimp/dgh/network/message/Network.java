package com.lastimp.dgh.network.message;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.network.ClientPayloadHandler;
import com.lastimp.dgh.network.ServerPayloadHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class Network {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                ResourceLocation.fromNamespaceAndPath(DontGetHurt.MODID, "networking"),
                () -> VERSION,
                (s) -> true,
                (s) -> true
        );
        INSTANCE.registerMessage(
                nextID(),
                MyReadAllConditionData.class,
                MyReadAllConditionData::toBytes,
                MyReadAllConditionData::new,
                MyReadAllConditionData::handlerClient,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
        INSTANCE.registerMessage(
                nextID(),
                MyReadAllConditionData.class,
                MyReadAllConditionData::toBytes,
                MyReadAllConditionData::new,
                MyReadAllConditionData::handlerServer,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
//        INSTANCE.registerMessage(
//                nextID(),
//                MyReadAllConditionData.class,
//                MyReadAllConditionData::toBytes,
//                MyReadAllConditionData::new,
//                MyReadAllConditionData::handler
//        );
//        INSTANCE.registerMessage(
//                nextID(),
//                MyReadAllConditionData.class,
//                MyReadAllConditionData::toBytes,
//                MyReadAllConditionData::new,
//                MyReadAllConditionData::handler
//        );
//        INSTANCE.registerMessage(
//                nextID(),
//                MyReadAllConditionData.class,
//                MyReadAllConditionData::toBytes,
//                MyReadAllConditionData::new,
//                MyReadAllConditionData::handler
//        );





        registrar.playBidirectional(
                MyReadAllConditionData.TYPE,
                MyReadAllConditionData.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientPayloadHandler::handleReadAllConditionData,
                        ServerPayloadHandler::handleReadAllConditionData
                )
        );
        registrar.playToServer(
                MyHealingItemUseData.TYPE,
                MyHealingItemUseData.STREAM_CODEC,
                ServerPayloadHandler::handleHealingItemUsageData
        );
        registrar.playToServer(
                MyKeyPressedData.TYPE,
                MyKeyPressedData.STREAM_CODEC,
                ServerPayloadHandler::handleClientPress
        );
    }
}
