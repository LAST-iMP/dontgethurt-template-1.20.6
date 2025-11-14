package com.lastimp.dgh.item;

import com.lastimp.dgh.gui.GuiOpenWrapper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HealthScanner extends Item {
    public HealthScanner() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (level.isClientSide) {
            GuiOpenWrapper.openHealthGui();
        }
        return super.use(level, player, usedHand);
    }
}
