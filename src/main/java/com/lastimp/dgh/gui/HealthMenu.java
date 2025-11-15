package com.lastimp.dgh.gui;

import com.lastimp.dgh.Register.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class HealthMenu extends AbstractContainerMenu {
//    private final ContainerData data;
    // 还记得我们说过的对于menu存在两个端的调用吗，这一个构造就是我们给客户端调用的，也就是注册时候用的，当我们调用openmenu时候，服务端会发包给客户端，客户端调用我们注册的这个方法。
    // buf的内容就是我们写入的pos
    public HealthMenu(int pContainerId, Inventory inv, FriendlyByteBuf buf) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(buf.readBlockPos()), new SimpleContainerData(1));
    }
    // 这个就是我们blockentity中create返回的方法。
    public HealthMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        //构造这是使用到了MenuType和EntitiyType一样要是对Menu的注册，我们之后注册讲
        super(ModMenus.HEALTH_MENU.get(), pContainerId);
        // 检查slot的多少
        checkContainerSize(inv,1);
        // 赋值data
//        this.data = data;
//        FirstMenuBlockEntity firstMenuBlockEntity = (FirstMenuBlockEntity) entity;
//        ItemStackHandler itemHandler = firstMenuBlockEntity.getItemHandler();
//        // 添加一个slot在menu上。
//        this.addSlot(new SlotItemHandler(itemHandler,0,80,32));



        // 添加data 用于数据同步的。
        addDataSlots(data);
        // 添加玩家背包和热键栏的slot，关于slot的讲解在下面。
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
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
        // Hotbar
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
