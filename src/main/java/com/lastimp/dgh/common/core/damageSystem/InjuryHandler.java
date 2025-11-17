package com.lastimp.dgh.common.core.damageSystem;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.client.player.IPlayerHealthCapability;
import com.lastimp.dgh.client.player.PlayerHealthCapability;
import com.lastimp.dgh.common.Register.ModCapabilities;
import com.lastimp.dgh.common.core.bodyPart.AbstractBody;
import com.lastimp.dgh.common.core.Enums.BodyComponents;
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

import static com.lastimp.dgh.common.core.Enums.BodyComponents.LEFT_LEG;
import static com.lastimp.dgh.common.core.Enums.BodyComponents.RIGHT_LEG;
import static com.lastimp.dgh.common.core.Enums.BodyCondition.BURN;
import static com.lastimp.dgh.common.core.Enums.BodyCondition.INTERNAL_INJURY;

@EventBusSubscriber(modid = DontGetHurt.MODID, bus = EventBusSubscriber.Bus.GAME)
public class InjuryHandler {

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();

        float damageAmount = event.getAmount();
        DamageSource source = event.getSource();

        if (source.is(DamageTypeTags.IS_FALL)) {
            // Fall damage logic
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
        IPlayerHealthCapability health = PlayerHealthCapability.getAndSet(player, h -> {
            AbstractBody leftFeet = (AbstractBody) h.getComponent(LEFT_LEG);
            AbstractBody rightFeet = (AbstractBody) h.getComponent(RIGHT_LEG);

            leftFeet.addCondition(INTERNAL_INJURY, damageAmount * 0.5f / INTERNAL_INJURY.factor);
            rightFeet.addCondition(INTERNAL_INJURY, damageAmount * 0.5f / INTERNAL_INJURY.factor);
            return h;
        });

        PacketDistributor.sendToPlayer((ServerPlayer) player,
                MySynBodyConditionData.getInstance(health, LEFT_LEG, INTERNAL_INJURY),
                MySynBodyConditionData.getInstance(health, RIGHT_LEG, INTERNAL_INJURY)
        );
        event.setAmount(0f);
    }

    public static void handleBurning(float damageAmount, Player player, LivingHurtEvent event) {
        Pair<IPlayerHealthCapability, BodyComponents> result = PlayerHealthCapability.getAndSet(player, h -> {
            BodyComponents randomComponent = BodyComponents.random();
            AbstractBody body = (AbstractBody) h.getComponent(randomComponent);

            body.addCondition(BURN, damageAmount / BURN.factor);
            return new Pair<>(h, randomComponent);
        });

        PacketDistributor.sendToPlayer((ServerPlayer) player,
                MySynBodyConditionData.getInstance(result.getA(), result.getB(), BURN)
        );
        event.setAmount(0f);
    }
}
