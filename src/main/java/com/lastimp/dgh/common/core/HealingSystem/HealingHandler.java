package com.lastimp.dgh.common.core.HealingSystem;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.client.gui.GuiOpenWrapper;
import com.lastimp.dgh.client.gui.HealthScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = DontGetHurt.MODID, bus = EventBusSubscriber.Bus.GAME)
public class HealingHandler {
    private static HealthScreen healthScreen = null;

    @SubscribeEvent
    public static void onScannerHealing(ScreenEvent.MouseButtonPressed.Pre event) {
        if (event.getButton() != 1) return;
        if (!screenHealingcheck()) return;

        Slot slot = healthScreen.getSlotUnderMouse();
        AbstractHealingItem healingItem = (AbstractHealingItem) slot.getItem().getItem();
//        healingItem.heal();
//        if (!(item instanceof AbstractHealingItem)) return;
        event.setCanceled(true);
    }

    private static boolean screenHealingcheck() {
        Player player = GuiOpenWrapper.MINECRAFT.get().player;
        if (player == null) return false;
        if (!player.level().isClientSide) return false;
        if (healthScreen == null) return false;

        Slot slot = healthScreen.getSlotUnderMouse();
        if (slot == null) return false;
        if (slot.getItem().isEmpty()) return false;
        if (!(slot.getItem().getItem() instanceof AbstractHealingItem)) return false;

        return true;
    }

    public static HealthScreen getHealthScreen() {
        return healthScreen;
    }

    public static void setHealthScreen(HealthScreen healthScreen) {
        HealingHandler.healthScreen = healthScreen;
    }
}
