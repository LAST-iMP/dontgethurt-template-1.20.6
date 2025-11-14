package com.lastimp.dgh.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.util.Lazy;

public class GuiOpenWrapper {
    // 懒加载Minecraft实例（避免提前初始化）
    public static final Lazy<Minecraft> MINECRAFT = Lazy.of(Minecraft::getInstance);

    public static void openHealthGui() {
        MINECRAFT.get().setScreen(new HealthGUI(Component.translatable("gui.dgh.health_gui.title")));
    }
}
