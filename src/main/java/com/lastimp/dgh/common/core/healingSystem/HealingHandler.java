package com.lastimp.dgh.common.core.healingSystem;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.api.healingItems.AbstractDirectHealItems;
import com.lastimp.dgh.api.healingItems.AbstractHealingItem;
import com.lastimp.dgh.api.healingItems.AbstractPartlyHealItem;
import com.lastimp.dgh.api.tags.ModTags;
import com.lastimp.dgh.client.gui.HealthScreen;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.common.item.Bandages;
import com.lastimp.dgh.network.message.MyHealingItemUseData;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

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

        var slot = healthScreen.getSlotUnderMouse();
        if (slot == null) return false;

        var itemStack = slot.getItem();
        if (itemStack.isEmpty()) return false;
        if (itemStack.is(ModTags.SHEARS)) return true;
        if (!(itemStack.getItem() instanceof AbstractHealingItem)) return false;

        return true;
    }

    public static boolean isConsumableHealingItem(ItemStack itemStack) {
        return itemStack.getItem() instanceof AbstractHealingItem;
    }

    public static boolean useItemOn(ItemStack itemStack, @NotNull ServerPlayer source, ServerPlayer target, BodyComponents component) {
        if (target == null) return false;
        if (itemStack.is(ModTags.SHEARS)) {
            return Bandages.cut(target, component);
        }
        return false;
    }

    public static boolean useConsumableItemOn(ItemStack itemStack, @NotNull ServerPlayer source, ServerPlayer target, BodyComponents component) {
        if (target == null) return false;
        AbstractHealingItem healingItem = (AbstractHealingItem) itemStack.getItem();
        if (healingItem instanceof AbstractDirectHealItems item) {
            return item.heal(source, target);
        } else if (healingItem instanceof AbstractPartlyHealItem item) {
            return item.heal(source, target, component);
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
