package com.lastimp.dgh.common.item;

import com.lastimp.dgh.client.player.PlayerHealthCapability;
import com.lastimp.dgh.common.Register.ModCapabilities;
import com.lastimp.dgh.common.core.Enums.BodyComponents;
import com.lastimp.dgh.common.core.Enums.BodyCondition;
import com.lastimp.dgh.common.core.Enums.OperationType;
import com.lastimp.dgh.common.core.bodyPart.PlayerBlood;
import com.lastimp.dgh.client.player.IPlayerHealthCapability;
import com.lastimp.dgh.network.DataPack.MyReadAllConditionData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class BloodScanner extends Item {
    private boolean used = false;

    public BloodScanner(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide && usedHand == InteractionHand.MAIN_HAND && !this.used)
            BloodScanner.scanHealth(player, PlayerHealthCapability.get(player), player.getScoreboardName());
        this.used = false;
        return super.use(level, player, usedHand);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!player.level().isClientSide && hand == InteractionHand.MAIN_HAND) {
            this.scanEntity(player, target);
            this.used = true;
        }
        return InteractionResult.PASS;
    }

    private void scanEntity(Player player, LivingEntity entity) {
        if (!(entity instanceof Player target)) {
            player.sendSystemMessage(Component.literal(entity.getName().getString() + "的血液很正常"));
        } else {
            PacketDistributor.sendToServer(MyReadAllConditionData.getInstance(
                    target.getUUID(), null, OperationType.BLOOD_SCANN
            ));
        }
    }

    public static void scanHealth(Player player, IPlayerHealthCapability health, String name) {
        PlayerBlood blood = (PlayerBlood) health.getComponent(BodyComponents.BLOOD);
        boolean hasAbnormal = false;
        for (BodyCondition condition : BodyCondition.bloodScannerConditions()) {
            float value = blood.getCondition(condition);
            if (condition.abnormal(value)) {
                hasAbnormal = true;
                player.sendSystemMessage(
                        Component.literal(condition + ": " + String.format("%.2f", value))
                );
            }
        }
        if (!hasAbnormal) {
            player.sendSystemMessage(Component.literal(name + "的血液状态正常"));
        }
    }
}
