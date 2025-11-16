package com.lastimp.dgh.common.item;

import com.lastimp.dgh.common.Register.ModCapabilities;
import com.lastimp.dgh.common.core.Enums.BodyComponents;
import com.lastimp.dgh.common.core.Enums.BodyCondition;
import com.lastimp.dgh.common.core.bodyPart.PlayerBlood;
import com.lastimp.dgh.client.player.IPlayerHealthCapability;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BloodScanner extends Item {
    private boolean used = false;

    public BloodScanner(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (level.isClientSide && usedHand == InteractionHand.MAIN_HAND && !this.used)
            this.scanEntity(player, player);
        this.used = false;
        return super.use(level, player, usedHand);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (player.level().isClientSide && hand == InteractionHand.MAIN_HAND) {
            this.scanEntity(player, target);
            this.used = true;
        }
        return InteractionResult.PASS;
    }

    private void scanEntity(Player player, LivingEntity entity) {
        if (!(entity instanceof Player)) {
            player.sendSystemMessage(Component.literal("它的血液很正常"));
        } else {
            IPlayerHealthCapability playerHealth = entity.getCapability(ModCapabilities.PLAYER_HEALTH_HANDLER);
            PlayerBlood blood = (PlayerBlood) playerHealth.getComponent(BodyComponents.BLOOD);
            boolean hasAbnormal = false;
            for (BodyCondition condition : BodyCondition.bloodScannerConditions()) {
                float value = blood.getCondition(condition);
                if (condition.abnormal(value)) {
                    hasAbnormal = true;
                    entity.sendSystemMessage(
                            Component.literal(condition + ": " + String.format("%.2f", value))
                    );
                }
            }
            if (!hasAbnormal) {
                entity.sendSystemMessage(Component.literal(player.getName() + "的血液状态正常"));
            }
        }
    }
}
