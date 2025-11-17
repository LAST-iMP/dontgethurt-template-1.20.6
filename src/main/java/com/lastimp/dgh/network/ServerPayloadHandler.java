package com.lastimp.dgh.network;

import com.lastimp.dgh.client.player.IPlayerHealthCapability;
import com.lastimp.dgh.client.player.PlayerHealthCapability;
import com.lastimp.dgh.common.core.Enums.OperationType;
import com.lastimp.dgh.common.core.HealingSystem.HealingHandler;
import com.lastimp.dgh.common.core.bodyPart.AbstractBody;
import com.lastimp.dgh.common.core.Enums.BodyComponents;
import com.lastimp.dgh.common.core.Enums.BodyCondition;
import com.lastimp.dgh.network.DataPack.MyHealingItemUseData;
import com.lastimp.dgh.network.DataPack.MyReadAllConditionData;
import com.lastimp.dgh.network.DataPack.MySelectBodyData;
import com.lastimp.dgh.network.DataPack.MySynBodyConditionData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public class ServerPayloadHandler {

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
                    UUID uuid = new UUID(data.id_most(), data.id_least());
                    ServerPlayer targetPlayer = (ServerPlayer) context.player().level().getPlayerByUUID(uuid);
                    IPlayerHealthCapability health = PlayerHealthCapability.get(targetPlayer);

                    PacketDistributor.sendToPlayer(
                            (ServerPlayer) context.player(),
                            MyReadAllConditionData.getInstance(uuid, health, OperationType.valueOf(data.oper()), context.player().registryAccess())
                    );
                })
                .exceptionally(e -> {
                    context.disconnect(Component.translatable("dgh.networking.failed", e.getMessage()));
                    return null;
                });
    }

    public static void handleHealingItemUsageData(final MyHealingItemUseData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
                    ServerPlayer sourcePlayer = (ServerPlayer) context.player();
                    UUID targetID = new UUID(data.id_most(), data.id_least());
                    ServerPlayer target = (ServerPlayer) sourcePlayer.level().getPlayerByUUID(targetID);
                    ItemStack stack = sourcePlayer.getInventory().getItem(data.slotNum());
                    BodyComponents component = data.component().equals("NONE") ? null : BodyComponents.valueOf(data.component());

                    boolean success = HealingHandler.useHealingItemOn(stack, target, component);
                    if (success) {
                        stack.consume(1, target);
                    }
        })
                .exceptionally(e -> {
                    context.disconnect(Component.translatable("dgh.networking.failed", e.getMessage()));
                    return null;
                });
    }
}
