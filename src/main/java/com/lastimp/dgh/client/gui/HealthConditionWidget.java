package com.lastimp.dgh.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class HealthConditionWidget extends AbstractWidget {
    private final ResourceLocation texture;
    private final int iconSize = 12;
    private float severity = 0f;

    public HealthConditionWidget(int width, int height, Component message, ResourceLocation texture) {
        super(0, 0, width, height, message);
        this.texture = texture;
        this.visible = false;
    }

    public void setSeverity(float severity) {
        this.severity = Mth.clamp(severity, 0f, 1f);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        if (!this.visible || !this.active) return;
        // draw bar background
        int bgColor = 0xFF3A3C3B; // ARGB
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, bgColor);

        // optional border
        int borderColor = 0xFF000000;
        guiGraphics.renderOutline(this.getX(), this.getY(), this.width, this.height, borderColor);

        // draw filled portion
        int filled = (int) (this.width * severity);
        int fgColor = 0xFFFF7471;
        guiGraphics.fill(this.getX() + 1, this.getY() + 1, Mth.clamp(this.getX() + filled, this.getX() + 1, this.getX() + this.width - 1), this.getY() + this.height - 1, fgColor);

        // draw icon from texture (if you want to use atlas, supply proper tex size)
        guiGraphics.blitSprite(texture, this.getX() + 2, this.getY() + 2, iconSize, iconSize);

        int stringColor = 0xFF000000;
        Minecraft mc = GuiOpenWrapper.MINECRAFT.get();
        guiGraphics.drawCenteredString(mc.font, this.getMessage(),
                this.getX() + 3 + (this.width + iconSize) / 2,
                this.getY() + (this.height - mc.font.lineHeight) / 2,
                stringColor);

        // show tooltip when hovered
//        if (this.isMouseOver(mouseX, mouseY)) {
//            this.renderTooltip(poseStack, Component.literal(String.format("严重度: %.0f%%", severity * 100f)), mouseX, mouseY);
//        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
