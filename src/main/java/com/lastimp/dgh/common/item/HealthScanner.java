package com.lastimp.dgh.common.item;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.client.gui.HealthMenu;
import com.lastimp.dgh.client.gui.HealthScreen;
import com.lastimp.dgh.common.core.HealingSystem.HealingHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//@EventBusSubscriber(modid = DontGetHurt.MODID, bus = EventBusSubscriber.Bus.GAME)
public class HealthScanner extends Item implements MenuProvider {
    private boolean used = false;

    public HealthScanner(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide) {
            HealthScreen.targetPlayer = player;
            player.openMenu(this);
        }
        this.used = false;
        return super.use(level, player, usedHand);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!player.level().isClientSide) {
            player.openMenu(this);
            this.used = true;
        }
        return InteractionResult.PASS;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("gui." + DontGetHurt.MODID + ".health_menu");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new HealthMenu(i, inventory, null, null);
    }


}
