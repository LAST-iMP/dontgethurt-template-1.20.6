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
    public static final float MAX_HEALTH = 20;

    @SubscribeEvent
    public static void onHealingUpdate(PlayerTickEvent.Pre event) {
        if (event.getEntity().level().isClientSide) return;

        ServerPlayer player = (ServerPlayer) event.getEntity();
        PlayerHealthCapability.getAndSet(player, health -> {
            health = health.update(player);
            return health;
        });
    }

    @SubscribeEvent
    public static void onHealingUpdate(PlayerTickEvent.Post event) {
        if (event.getEntity().level().isClientSide) return;
        ServerPlayer player = (ServerPlayer) event.getEntity();

        PlayerHealthCapability health = PlayerHealthCapability.get(player);
        float maxHealth = MAX_HEALTH * health.getComponent(BLOOD).getConditionValue(BodyCondition.BLOOD_VOLUME);

        if ((int)maxHealth != (int)player.getMaxHealth())
            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        if (maxHealth <= 0)
            player.kill();
    }
}
