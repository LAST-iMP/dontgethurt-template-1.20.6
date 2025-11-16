package com.lastimp.dgh.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class HealthConditionWidget extends AbstractWidget {
    private final ResourceLocation texture;
    private final int iconSize = 16;
    private final int iconTextureSize = 64;
    private float severity = 0f;

    public HealthConditionWidget(int width, int height, ResourceLocation texture) {
        super(0, 0, width, height, Component.empty());
        this.texture = texture;
        this.visible = false;
    }

    public void setSeverity(float severity) {
        this.severity = Mth.clamp(severity, 0f, 1f);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        // draw icon from texture (if you want to use atlas, supply proper tex size)
        guiGraphics.blitSprite(texture, this.getX(), this.getY(), iconTextureSize, iconTextureSize, iconTextureSize, iconTextureSize, iconSize, iconSize);

        // draw bar background
        int bgColor = 0x3A3C3B; // ARGB
        guiGraphics.fill(this.getX(), this.getY(), this.width, this.height, bgColor);

        // draw filled portion
        int filled = (int) (this.width * severity);
        int fgColor = 0xFF0700;
        guiGraphics.fill(this.getX(), this.getY(), filled, this.height, fgColor);

        // optional border
        int borderColor = 0xFF000000;
        guiGraphics.renderOutline(this.getX(), this.getY(), this.width, this.height, borderColor);

        // show tooltip when hovered
//        if (this.isMouseOver(mouseX, mouseY)) {
//            this.renderTooltip(poseStack, Component.literal(String.format("严重度: %.0f%%", severity * 100f)), mouseX, mouseY);
//        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
