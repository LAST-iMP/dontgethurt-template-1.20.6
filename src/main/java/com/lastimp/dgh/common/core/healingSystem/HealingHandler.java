package com.lastimp.dgh.common.core.healingSystem;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.api.healingItems.AbstractDirectHealItems;
import com.lastimp.dgh.api.healingItems.AbstractHealingItem;
import com.lastimp.dgh.api.healingItems.AbstractPartlyHealItem;
import com.lastimp.dgh.client.gui.HealthScreen;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.network.DataPack.MyHealingItemUseData;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = DontGetHurt.MODID, bus = EventBusSubscriber.Bus.GAME)
public class HealingHandler {
    private static HealthScreen healthScreen = null;

    @SubscribeEvent
    public static void onScannerHealing(ScreenEvent.MouseButtonPressed.Pre event) {
        if (event.getButton() != 1) return;
        if (!screenHealingCheck()) return;

        assert healthScreen.getSlotUnderMouse() != null;
        int index = healthScreen.getSlotUnderMouse().getSlotIndex();
        PacketDistributor.sendToServer(MyHealingItemUseData.getInstance(
                healthScreen.getMenu().targetPlayer, index, healthScreen.getSelectedComponent()
        ));
        event.setCanceled(true);
    }

    private static boolean screenHealingCheck() {
        Minecraft mc = Minecraft.getInstance();
        if (!mc.level.isClientSide()) return false;
        if (mc.player == null) return false;
        if (healthScreen == null) return false;

        Slot slot = healthScreen.getSlotUnderMouse();
        if (slot == null) return false;
        if (slot.getItem().isEmpty()) return false;
        if (!(slot.getItem().getItem() instanceof AbstractHealingItem)) return false;

        return true;
    }

    public static boolean useHealingItemOn(ItemStack itemStack, ServerPlayer target, BodyComponents component) {
        AbstractHealingItem healingItem = (AbstractHealingItem) itemStack.getItem();
        if (healingItem instanceof AbstractDirectHealItems item) {
            return item.heal(target);
        } else if (healingItem instanceof AbstractPartlyHealItem item) {
            return item.heal(target, component);
        } else {
            return false;
        }
    }

    public static HealthScreen getHealthScreen() {
        return HealingHandler.healthScreen;
    }

    public static void setHealthScreen(HealthScreen healthScreen) {
        HealingHandler.healthScreen = healthScreen;
    }
}
