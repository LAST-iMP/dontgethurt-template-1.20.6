package com.lastimp.dgh.gui;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.Register.ModCapabilities;
import com.lastimp.dgh.player.IPlayerHealthCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractFurnaceMenu;

public class HealthScreen extends AbstractContainerScreen<HealthMenu> {
    // 纹理资源路径（替换为你的模组ID和纹理文件名）
    private static final ResourceLocation HUD_BACKGROUND = new ResourceLocation(DontGetHurt.MODID, "textures/gui/health_hud.png");

    // HUD尺寸配置（可自定义）
    private static final int PANEL_WIDTH = 238;   // 面板宽度
    private static final int PANEL_HEIGHT = 214;  // 面板高度
    private static final int ICON_SIZE = 16;      // 图标尺寸（16x16像素）
    private static final int ITEM_SPACING = 22;   // 状态项垂直间距
    private static final int PROGRESS_BAR_WIDTH = 85; // 进度条宽度
    private static final int PROGRESS_BAR_HEIGHT = 6; // 进度条高度

    public HealthScreen(HealthMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init () {
        super.init();
//        addRenderableWidget(
//                Button.builder(Component.literal("关闭"), b -> onClose())
//                .bounds(width - 110, height - 30, 100, 20).build()
//        );
    }

    private static void drawPanelBackground(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.fill(x, y, x + PANEL_WIDTH, y + PANEL_HEIGHT, 0xCC000000);
        guiGraphics.blit(
                HUD_BACKGROUND,
                x, y,  // 屏幕上的绘制位置
                0, 0,  // 纹理中要绘制的区域起始坐标（左上角）
                PANEL_WIDTH, PANEL_HEIGHT  // 绘制的宽高（需与纹理对应区域尺寸一致）
        );
    }



    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (!check()) return;
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        Player player = GuiOpenWrapper.MINECRAFT.get().player;
        // 获取玩家身体状态数据（通过Capability）
        IPlayerHealthCapability healthData = player.getCapability(ModCapabilities.PLAYER_HEALTH_HANDLER);
        if (healthData == null) return;

        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick,  int mouseX, int mouseY) {
        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();

        int panelX = (screenWidth - PANEL_WIDTH) / 2;
        int panelY = (screenHeight - PANEL_HEIGHT) / 2;

        drawPanelBackground(guiGraphics, panelX, panelY);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        GuiOpenWrapper.MINECRAFT.get().setScreen(null);
        super.onClose();
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        return;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
    }

    private boolean check() {
        Minecraft mc = GuiOpenWrapper.MINECRAFT.get();
        // 跳过：菜单界面、无玩家、隐藏GUI（按F1）
        return !(mc.level == null || mc.player == null || mc.options.hideGui);
    }
}
