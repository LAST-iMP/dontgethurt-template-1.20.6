package com.lastimp.dgh.client.gui.MenuProvider;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.client.gui.HealthMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class HealthMenuProvider implements MenuProvider {
    public final UUID targetPlayer;

    public HealthMenuProvider(UUID targetPlayer) {
        this.targetPlayer = targetPlayer;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("gui." + DontGetHurt.MODID + ".health_menu");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new HealthMenu(i, inventory, targetPlayer);
    }
}
