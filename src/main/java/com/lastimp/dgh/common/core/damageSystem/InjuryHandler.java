package com.lastimp.dgh.common.core.damageSystem;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.api.bodyPart.AbstractBody;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.common.core.Utils;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;

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
            handleDrowning(damageAmount, player, event);
        } else if (source.is(DamageTypeTags.IS_EXPLOSION)) {
            handleExplosion(damageAmount, player, event);
        } else if (source.getEntity() != null && source.getEntity() instanceof LivingEntity) {
            handleEntityAttack(damageAmount, player, event);
        } else {
            handleDefaultDamage(damageAmount, player, event);
        }
    }

    public static void handleFalling(float damageAmount, Player player, LivingHurtEvent event) {
        PlayerHealthCapability.getAndSet(player, h -> {
            AbstractBody[] legs = h.legs();
            float[] weight = Utils.getRandom(1, 1);
            for (int i = 0; i < legs.length; i++) {
                legs[i].injury(INTERNAL_INJURY, damageAmount * weight[i] / INTERNAL_INJURY.factor);
            }
            return h;
        });
        event.setAmount(0f);
    }

    public static void handleBurning(float damageAmount, Player player, LivingHurtEvent event) {
        PlayerHealthCapability.getAndSet(player, h -> {
            BodyComponents randomComponent = BodyComponents.random();
            AbstractBody body = h.getComponent(randomComponent);

            body.injury(BURN, damageAmount / BURN.factor);
            return h;
        });
        event.setAmount(0f);
    }

    public static void handleDrowning(float damageAmount, Player player, LivingHurtEvent event) {
        PlayerHealthCapability.getAndSet(player, h -> {
            AbstractBody torso = h.getComponent(BodyComponents.TORSO);
            torso.injury(INTERNAL_INJURY, damageAmount / INTERNAL_INJURY.factor);
            return h;
        });
        event.setAmount(0f);
    }

    public static void handleExplosion(float damageAmount, Player player, LivingHurtEvent event) {
        PlayerHealthCapability.getAndSet(player, h -> {
            AbstractBody[] body = h.visibleParts();
            float[] weight = Utils.getRandom(1.5f,3,2,2,1.5f,1.5f);
            for (int i = 0; i < body.length; i++) {
                body[i].injury(OPEN_WOUND,      0.5f * damageAmount * weight[i] / OPEN_WOUND.factor);
                body[i].injury(INTERNAL_INJURY, 0.5f * damageAmount * weight[i] / INTERNAL_INJURY.factor);
            }
            return h;
        });
        event.setAmount(0f);
    }

    public static void handleEntityAttack(float damageAmount, Player player, LivingHurtEvent event) {
        PlayerHealthCapability.getAndSet(player, h -> {
            AbstractBody[] body = h.visibleParts();
            float[] weight = Utils.getRandom(1,2,2,2,0.5f,0.5f);
            for (int i = 0; i < body.length; i++) {
                body[i].injury(OPEN_WOUND, damageAmount * weight[i] / OPEN_WOUND.factor);
            }
            return h;
        });
        event.setAmount(0f);
    }

    public static void handleDefaultDamage(float damageAmount, Player player, LivingHurtEvent event) {
        PlayerHealthCapability.getAndSet(player, h -> {
            AbstractBody[] body = h.visibleParts();
            float[] weight = Utils.getRandom(1,2,2,2,0.5f,0.5f);
            for (int i = 0; i < body.length; i++) {
                body[i].injury(INTERNAL_INJURY, damageAmount * weight[i] / INTERNAL_INJURY.factor);
            }
            return h;
        });
        event.setAmount(0f);
    }
}
