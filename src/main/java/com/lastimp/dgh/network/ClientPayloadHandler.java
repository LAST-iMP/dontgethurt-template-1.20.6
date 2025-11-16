package com.lastimp.dgh.network;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {

    public static void handleData(final MySelectBodyData data, final IPayloadContext context) {
        // Do something with the data, on the network thread

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {
//                    blah(data.selectBodyPart());
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(Component.translatable("dgh.networking.failed", e.getMessage()));
                    return null;
                });
    }
}
