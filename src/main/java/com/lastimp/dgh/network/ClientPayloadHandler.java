package com.lastimp.dgh.network;

import com.lastimp.dgh.client.gui.HealthScreen;
import com.lastimp.dgh.api.enums.OperationType;
import com.lastimp.dgh.api.bodyPart.AbstractBody;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.api.enums.BodyCondition;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import com.lastimp.dgh.common.item.BloodScanner;
import com.lastimp.dgh.network.DataPack.MyReadAllConditionData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public class ClientPayloadHandler {
    private static HealthScreen healthScreen = null;

    public static void handleReadAllConditionData(final MyReadAllConditionData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
                    PlayerHealthCapability health = MyReadAllConditionData.getHealthFromInstance(data.tag(), context.player().registryAccess());
                    OperationType operation = OperationType.valueOf(data.oper());
                    if (operation == OperationType.HEALTH_SCANN && healthScreen != null) {
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
