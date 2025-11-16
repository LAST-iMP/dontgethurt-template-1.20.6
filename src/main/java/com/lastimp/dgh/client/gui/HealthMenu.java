package com.lastimp.dgh.client.gui;

import com.lastimp.dgh.common.Register.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class HealthMenu extends AbstractContainerMenu {
    public HealthMenu(int pContainerId, Inventory inv, FriendlyByteBuf buf) {
        this(pContainerId, inv, null, null);
    }

    public HealthMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenus.HEALTH_MENU.get(), pContainerId);
        // 检查slot的多少
        checkContainerSize(inv,1);
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
