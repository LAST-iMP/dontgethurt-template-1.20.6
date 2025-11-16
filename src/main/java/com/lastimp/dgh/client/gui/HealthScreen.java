package com.lastimp.dgh.client.gui;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.client.player.IPlayerHealthCapability;
import com.lastimp.dgh.common.Register.ModCapabilities;
import com.lastimp.dgh.common.core.AbstractBody;
import com.lastimp.dgh.common.core.BodyComponents;
import com.lastimp.dgh.common.core.BodyCondition;
import com.lastimp.dgh.common.core.IAbstractBody;
import com.lastimp.dgh.network.MySelectBodyData;
import com.lastimp.dgh.network.ModNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    // 新增：组件列表与选中索引
    private final List<HealthComponentWidget> componentWidgets = new ArrayList<>();
    private final HashMap<BodyCondition, HealthConditionWidget> conditionWidgets = new HashMap<>();
    private BodyComponents selectedComponent = null;

    public HealthScreen(HealthMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init () {
        this.imageWidth = PANEL_WIDTH;
        this.imageHeight = PANEL_HEIGHT;
        super.init();

        // 清理旧的
        componentWidgets.clear();
        this.addHealthWidget(35, 8, 27, 29, BodyComponents.HEAD);
        this.addHealthWidget(34,41, 28, 38, BodyComponents.TORSO);
        this.addHealthWidget(21, 42, 10, 44, BodyComponents.LEFT_ARM);
        this.addHealthWidget(66, 42, 10, 44, BodyComponents.RIGHT_ARM);
        this.addHealthWidget(33, 80, 11, 43, BodyComponents.LEFT_LEG);
        this.addHealthWidget(52, 80, 11, 43, BodyComponents.RIGHT_LEG);

        conditionWidgets.clear();
        for (BodyCondition condition : BodyCondition.healthScannerConditions()) {
            addConditionWidget(condition);
        }
    }

    private void addHealthWidget(int x, int y, int width, int height, BodyComponents idx) {
        HealthComponentWidget w = new HealthComponentWidget(
                this.leftPos + x, this.topPos + y, width, height,
                Component.literal(idx.toString()),
                (button) -> {
                    this.selectedComponent = idx;
                },
                idx
        );
        componentWidgets.add(w);
        this.addRenderableWidget(w);
    }

    private void addConditionWidget(BodyCondition condition) {
        HealthConditionWidget w = new HealthConditionWidget(
                70, 16, condition.getComponent(), condition.texture
        );
        conditionWidgets.put(condition, w);
        this.addRenderableWidget(w);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (!check()) return;

        this.renderCondition(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    private void renderCondition(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (selectedComponent == null) return;
        Player player = GuiOpenWrapper.MINECRAFT.get().player;
        IPlayerHealthCapability healthData = player.getCapability(ModCapabilities.PLAYER_HEALTH_HANDLER);

        for (HealthConditionWidget widget : this.conditionWidgets.values()){
            widget.visible = false;
        }

        int widgetCount = 0;
        AbstractBody bodyPart = (AbstractBody) healthData.getComponent(this.selectedComponent);
        List<BodyCondition> conditions = bodyPart.getBodyConditions();
        for (BodyCondition condition : conditions) {
            HealthConditionWidget widget = this.conditionWidgets.get(condition);
            if (!BodyCondition.healthScannerConditions().contains(condition)) continue;
            if (!condition.abnormal(bodyPart.getCondition(condition))) continue;
            if (widgetCount > 12) break;

            widget.setPosition(
                    this.leftPos + 85 + (widgetCount % 2) * 72,
                    this.topPos + 11 + (widgetCount / 2) * 18
            );
            widget.setSeverity(bodyPart.getCondition(condition));
            widget.visible = true;
            widgetCount += 1;
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick,  int mouseX, int mouseY) {
        int panelX = (guiGraphics.guiWidth() - PANEL_WIDTH) / 2;
        int panelY = (guiGraphics.guiHeight() - PANEL_HEIGHT) / 2;

        drawPanelBackground(guiGraphics, panelX, panelY);
    }

    private static void drawPanelBackground(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.blit(
                HUD_BACKGROUND,
                x, y,  // 屏幕上的绘制位置
                0, 0,  // 纹理中要绘制的区域起始坐标（左上角）
                PANEL_WIDTH, PANEL_HEIGHT  // 绘制的宽高（需与纹理对应区域尺寸一致）
        );
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
    }

    @Override
    protected void containerTick() {
        super.containerTick();
    }

    private boolean check() {
        Minecraft mc = GuiOpenWrapper.MINECRAFT.get();
        // 跳过：菜单界面、无玩家、隐藏GUI（按F1)
        return !(mc.level == null || mc.player == null || mc.options.hideGui);
    }
}
