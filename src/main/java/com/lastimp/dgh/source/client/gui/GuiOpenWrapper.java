package com.lastimp.dgh.source.client.gui;

import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.common.util.Lazy;

public class GuiOpenWrapper {
    // 懒加载Minecraft实例（避免提前初始化）
    public static final Lazy<Minecraft> MINECRAFT = Lazy.of(Minecraft::getInstance);

    public static void openHealthScreen() {
//        MINECRAFT.get().setScreen(new HealthScreen(Component.translatable("gui." + DontGetHurt.MODID + "health_screen.title")));
    }
}
