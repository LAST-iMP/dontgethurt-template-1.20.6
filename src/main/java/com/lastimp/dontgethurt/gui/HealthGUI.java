package com.lastimp.dontgethurt.gui;

import com.lastimp.dontgethurt.DontGetHurt;
import com.lastimp.dontgethurt.Register.ModCapabilities;
import com.lastimp.dontgethurt.core.AnyBodyCondition;
import com.lastimp.dontgethurt.core.BodyComponents;
import com.lastimp.dontgethurt.player.IPlayerHealthCapability;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.common.util.Lazy;

@OnlyIn(Dist.CLIENT)
public class HealthGUI extends Screen {
    // 纹理资源路径（替换为你的模组ID和纹理文件名）
    private static final ResourceLocation HUD_BACKGROUND = new ResourceLocation(DontGetHurt.MODID, "textures/gui/health_hud.png");

    // HUD尺寸配置（可自定义）
    private static final int PANEL_WIDTH = 130;   // 面板宽度
    private static final int PANEL_HEIGHT = 90;  // 面板高度
    private static final int ICON_SIZE = 16;      // 图标尺寸（16x16像素）
    private static final int ITEM_SPACING = 22;   // 状态项垂直间距
    private static final int PROGRESS_BAR_WIDTH = 85; // 进度条宽度
    private static final int PROGRESS_BAR_HEIGHT = 6; // 进度条高度

    // 懒加载Minecraft实例（避免提前初始化）
    private static final Lazy<Minecraft> MINECRAFT = Lazy.of(Minecraft::getInstance);

    protected HealthGUI(Component title) {
        super(title);
    }

    @Override
    protected void init () {
        super.init();
        addRenderableWidget(
                Button.builder(Component.literal("关闭"), b -> onClose())
                .bounds(width - 110, height - 30, 100, 20).build()
        );
    }

    // 订阅GUI渲染事件（Post表示默认GUI绘制完成后执行）
    @SubscribeEvent
    public void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = MINECRAFT.get();
        // 跳过：菜单界面、无玩家、隐藏GUI（按F1）
        if (mc.level == null || mc.player == null || mc.screen != null || mc.options.hideGui) {
            return;
        }

        Player player = mc.player;
        // 获取玩家身体状态数据（通过Capability）
        IPlayerHealthCapability healthData = player.getCapability(ModCapabilities.PLAYER_HEALTH_HANDLER);
        if (healthData == null) return;

        GuiGraphics guiGraphics = event.getGuiGraphics();
        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();

        // 计算HUD位置：右上角（距离屏幕边缘10像素）
        int panelX = screenWidth - PANEL_WIDTH - 10;
        int panelY = 10;

        // 1. 绘制HUD半透明背景
        drawPanelBackground(guiGraphics, panelX, panelY);

//        // 2. 绘制各个身体状态（垂直排列）
//        int currentY = panelY + 10; // 面板内上边距10像素
//        drawTemperature(poseStack, panelX + 10, currentY, healthData);
//        currentY += ITEM_SPACING;
//        drawHydration(poseStack, panelX + 10, currentY, healthData);
//        currentY += ITEM_SPACING;
//        drawFatigue(poseStack, panelX + 10, currentY, healthData);

//        // 3. 绘制受伤提示（如果受伤）
//        if (healthData.isWounded()) {
//            drawWoundedWarning(poseStack, panelX + 10, currentY + ITEM_SPACING);
//        }
    }

    private void drawPanelBackground(GuiGraphics guiGraphics, int x, int y) {
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
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        MINECRAFT.get().setScreen(null);
        super.onClose();
    }
}
