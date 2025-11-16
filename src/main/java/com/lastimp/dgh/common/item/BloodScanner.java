package com.lastimp.dgh.common.item;

import com.lastimp.dgh.common.Register.ModCapabilities;
import com.lastimp.dgh.common.core.BodyComponents;
import com.lastimp.dgh.common.core.BodyCondition;
import com.lastimp.dgh.common.core.PlayerBlood;
import com.lastimp.dgh.client.player.IPlayerHealthCapability;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class BloodScanner extends Item {
    public BloodScanner(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (level.isClientSide) {
            IPlayerHealthCapability playerHealth = player.getCapability(ModCapabilities.PLAYER_HEALTH_HANDLER);
            PlayerBlood blood = (PlayerBlood) playerHealth.getComponent(BodyComponents.BLOOD);
            for (BodyCondition condition : BodyCondition.bloodScannerConditions()) {
                float value = blood.getCondition(condition);
                if (condition.abnormal(value)) {
                    player.sendSystemMessage(
                            Component.literal(condition + ": " + String.format("%.2f", value))
                    );
                }
            }
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return super.useOn(context);
    }
}
