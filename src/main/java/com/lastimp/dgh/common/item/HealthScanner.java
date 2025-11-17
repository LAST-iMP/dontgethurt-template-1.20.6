package com.lastimp.dgh.common.item;

import com.lastimp.dgh.client.gui.MenuProvider.HealthMenuProvider;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class HealthScanner extends Item {
    private boolean used = false;

    public HealthScanner(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide && !this.used) {
            player.openMenu(new HealthMenuProvider(player.getUUID()), buf -> buf.writeUUID(player.getUUID()));
        }
        this.used = false;
        return super.use(level, player, usedHand);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!(target instanceof Player)) {
            return InteractionResult.PASS;
        }

        if (!player.level().isClientSide) {
            this.used = true;
            player.openMenu(new HealthMenuProvider(target.getUUID()), buf -> buf.writeUUID(target.getUUID()));
        }
        return InteractionResult.PASS;
    }


}
