package com.lastimp.dgh.common.core.damageSystem;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.api.bodyPart.AbstractBody;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import com.lastimp.dgh.network.DataPack.MySynBodyConditionData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import oshi.util.tuples.Pair;

import static com.lastimp.dgh.api.enums.BodyComponents.*;
import static com.lastimp.dgh.api.enums.BodyCondition.*;

@EventBusSubscriber(modid = DontGetHurt.MODID, bus = EventBusSubscriber.Bus.GAME)
public class InjuryHandler {

    @SubscribeEvent
    public static void onPlayerInjury(LivingHurtEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();

        float damageAmount = event.getAmount();
        DamageSource source = event.getSource();

        if (source.is(DamageTypeTags.IS_FALL)) {
            handleFalling(damageAmount, player, event);
        } else if (source.is(DamageTypeTags.IS_FIRE)) {
            handleBurning(damageAmount, player, event);
        } else if (source.is(DamageTypeTags.IS_DROWNING)) {
            // Drowning damage logic
        } else if (source.is(DamageTypeTags.IS_EXPLOSION)) {
            // Explosion damage logic
        } else if (source.is(DamageTypeTags.IS_PROJECTILE)){
            // Other damage types
        }
    }

    public static void handleFalling(float damageAmount, Player player, LivingHurtEvent event) {
        PlayerHealthCapability health = PlayerHealthCapability.getAndSet(player, h -> {
            AbstractBody leftFeet = h.getComponent(LEFT_LEG);
            AbstractBody rightFeet = h.getComponent(RIGHT_LEG);

            leftFeet.addConditionValue(INTERNAL_INJURY, damageAmount * 0.5f / INTERNAL_INJURY.healthFactor);
            rightFeet.addConditionValue(INTERNAL_INJURY, damageAmount * 0.5f / INTERNAL_INJURY.healthFactor);
            return h;
        });

        PacketDistributor.sendToPlayer((ServerPlayer) player,
                MySynBodyConditionData.getInstance(health, LEFT_LEG, INTERNAL_INJURY),
                MySynBodyConditionData.getInstance(health, RIGHT_LEG, INTERNAL_INJURY)
        );
        event.setAmount(0f);
    }

    public static void handleBurning(float damageAmount, Player player, LivingHurtEvent event) {
        Pair<PlayerHealthCapability, BodyComponents> result = PlayerHealthCapability.getAndSet(player, h -> {
            BodyComponents randomComponent = BodyComponents.random();
            AbstractBody body = (AbstractBody) h.getComponent(randomComponent);

            body.addConditionValue(BURN, damageAmount / BURN.healthFactor);
            return new Pair<>(h, randomComponent);
        });

        PacketDistributor.sendToPlayer((ServerPlayer) player,
                MySynBodyConditionData.getInstance(result.getA(), result.getB(), BURN)
        );
        event.setAmount(0f);
    }

    public static void handleExplosion(float damageAmount, Player player, LivingHurtEvent event) {
        PlayerHealthCapability health = PlayerHealthCapability.getAndSet(player, h -> {
            AbstractBody leftArm = h.getComponent(LEFT_ARM);
            AbstractBody rightArm = h.getComponent(RIGHT_ARM);
            AbstractBody leftFeet = h.getComponent(LEFT_LEG);
            AbstractBody rightFeet = h.getComponent(RIGHT_LEG);
            AbstractBody head = h.getComponent(HEAD);
            AbstractBody torso = h.getComponent(TORSO);

            leftFeet.addConditionValue(INTERNAL_INJURY, damageAmount * 0.5f / INTERNAL_INJURY.healthFactor);
            rightFeet.addConditionValue(INTERNAL_INJURY, damageAmount * 0.5f / INTERNAL_INJURY.healthFactor);
            return h;
        });

        PacketDistributor.sendToPlayer((ServerPlayer) player,
                MySynBodyConditionData.getInstance(health, LEFT_LEG, INTERNAL_INJURY),
                MySynBodyConditionData.getInstance(health, RIGHT_LEG, INTERNAL_INJURY)
        );
        event.setAmount(0f);
    }
}
