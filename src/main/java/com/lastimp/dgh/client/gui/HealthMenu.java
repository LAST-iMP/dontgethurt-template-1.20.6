package com.lastimp.dgh.client.gui;

import com.lastimp.dgh.common.Register.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class HealthMenu extends AbstractContainerMenu {
    public final UUID targetPlayer;

    public HealthMenu(int pContainerId, Inventory inv, FriendlyByteBuf buf) {
        this(pContainerId, inv, buf.readUUID());
    }

    public HealthMenu(int pContainerId, Inventory inv, UUID targetPlayer) {
        super(ModMenus.HEALTH_MENU.get(), pContainerId);
        this.targetPlayer = targetPlayer;
        layoutPlayerInventorySlots(inv);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    // 添加玩家背包的slot和热键的栏的slot
    private void layoutPlayerInventorySlots(Inventory playerInventory) {
        // Player inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 41 + col * 18, 130 + row * 18));
            }
        }
        // Hotbar
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 41 + i * 18, 188));
        }
    }
}
