package com.lastimp.dgh.common.core.damageSystem;

import com.lastimp.dgh.DontGetHurt;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;

@EventBusSubscriber(modid = DontGetHurt.MODID, bus = EventBusSubscriber.Bus.GAME)
public class DamageHandler {

    @SubscribeEvent
    public static void onEntityAttack(LivingAttackEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (!(event.getEntity() instanceof Player)) return;

        ServerPlayer target = (ServerPlayer) event.getEntity();
        if (event.getSource().getDirectEntity() != null)
            target.sendSystemMessage(Component.literal( target.getScoreboardName()+ " attacked by " + event.getSource().getDirectEntity().getName().getString()));
    }
}
