package com.lastimp.dgh.network;

import com.lastimp.dgh.client.player.IPlayerHealthCapability;
import com.lastimp.dgh.common.Register.ModCapabilities;
import com.lastimp.dgh.common.core.bodyPart.AbstractBody;
import com.lastimp.dgh.common.core.Enums.BodyComponents;
import com.lastimp.dgh.common.core.Enums.BodyCondition;
import com.lastimp.dgh.network.DataPack.MySelectBodyData;
import com.lastimp.dgh.network.DataPack.MySynBodyConditionData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {

    public static void handleSelectBodyData(final MySelectBodyData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
//                    blah(data.selectBodyPart());
                })
                .exceptionally(e -> {
                    context.disconnect(Component.translatable("dgh.networking.failed", e.getMessage()));
                    return null;
                });
    }

    public static void handleSynBodyConditionData(final MySynBodyConditionData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
                    Player player = context.player();
                    IPlayerHealthCapability health = player.getCapability(ModCapabilities.PLAYER_HEALTH_HANDLER);
                    AbstractBody body = (AbstractBody) health.getComponent(BodyComponents.valueOf(data.component()));
                    body.setCondition(BodyCondition.valueOf(data.condition()), data.value());
                })
                .exceptionally(e -> {
                    context.disconnect(Component.translatable("dgh.networking.failed", e.getMessage()));
                    return null;
                });
    }
}
