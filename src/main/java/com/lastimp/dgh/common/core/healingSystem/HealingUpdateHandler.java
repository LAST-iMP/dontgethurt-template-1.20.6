package com.lastimp.dgh.common.core.healingSystem;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.api.enums.BodyCondition;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.lastimp.dgh.api.enums.BodyComponents.*;


@EventBusSubscriber(modid = DontGetHurt.MODID, bus = EventBusSubscriber.Bus.GAME)
public class HealingUpdateHandler {

    @SubscribeEvent
    public static void onHealingUpdate(PlayerTickEvent.Pre event) {
        if (event.getEntity().level().isClientSide) return;

        ServerPlayer player = (ServerPlayer) event.getEntity();
        PlayerHealthCapability.getAndSet(player, health -> {
            health = health.update();
            return health;
        });
    }

    @SubscribeEvent
    public static void onHealingUpdate(PlayerTickEvent.Post event) {
        if (event.getEntity().level().isClientSide) return;
        ServerPlayer player = (ServerPlayer) event.getEntity();

        PlayerHealthCapability health = PlayerHealthCapability.get(player);
        float maxHealth = player.getMaxHealth() * health.getComponent(BLOOD).getConditionValue(BodyCondition.BLOOD_VOLUME);
        if (maxHealth < player.getHealth())
            player.setHealth(maxHealth);
        if (maxHealth <= 0)
            player.kill();
    }
}
