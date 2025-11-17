package com.lastimp.dgh.network;

import com.lastimp.dgh.client.gui.HealthScreen;
import com.lastimp.dgh.client.player.IPlayerHealthCapability;
import com.lastimp.dgh.client.player.PlayerHealthCapability;
import com.lastimp.dgh.common.core.Enums.OperationType;
import com.lastimp.dgh.common.core.bodyPart.AbstractBody;
import com.lastimp.dgh.common.core.Enums.BodyComponents;
import com.lastimp.dgh.common.core.Enums.BodyCondition;
import com.lastimp.dgh.common.item.BloodScanner;
import com.lastimp.dgh.network.DataPack.MyReadAllConditionData;
import com.lastimp.dgh.network.DataPack.MySelectBodyData;
import com.lastimp.dgh.network.DataPack.MySynBodyConditionData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public class ClientPayloadHandler {
    private static HealthScreen healthScreen = null;

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
                    IPlayerHealthCapability health = PlayerHealthCapability.get(player);
                    AbstractBody body = (AbstractBody) health.getComponent(BodyComponents.valueOf(data.component()));
                    body.setCondition(BodyCondition.valueOf(data.condition()), data.value());
                })
                .exceptionally(e -> {
                    context.disconnect(Component.translatable("dgh.networking.failed", e.getMessage()));
                    return null;
                });
    }

    public static void handleReadAllConditionData(final MyReadAllConditionData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
                    IPlayerHealthCapability health = MyReadAllConditionData.getHealthFromInstance(data.tag());
                    OperationType operation = OperationType.valueOf(data.oper());
                    if (operation == OperationType.HEALTH_SCANN) {
                        healthScreen.setHealthData(health);
                    } else if (operation == OperationType.BLOOD_SCANN) {
                        UUID uuid = new UUID(data.id_most(), data.id_least());
                        BloodScanner.scanHealth(context.player(), health, context.player().level().getPlayerByUUID(uuid).getScoreboardName());
                    }
                })
                .exceptionally(e -> {
                    context.disconnect(Component.translatable("dgh.networking.failed", e.getMessage()));
                    return null;
                });
    }

    public static HealthScreen getHealthScreen() {
        return healthScreen;
    }

    public static void setHealthScreen(HealthScreen healthScreen) {
        ClientPayloadHandler.healthScreen = healthScreen;
    }
}
