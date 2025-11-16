package com.lastimp.dgh.client.gui;

import com.lastimp.dgh.common.core.BodyComponents;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class HealthComponentWidget extends Button {
    public final BodyComponents id;
    public final Component label;
    private float conditionValue;

    public HealthComponentWidget(int x, int y, int width, int height, Component message, OnPress onPress, BodyComponents id) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        this.id = id;
        this.label = message;
        this.conditionValue = 0.8f;
    }

    @Override
    protected void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        gui.setColor(1.0F, 0.2F, 0.2F, this.conditionValue);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        gui.blitSprite(SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());

        gui.fill(this.getX() + this.getWidth() - 1, this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), 0x66FFFFFF);
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public float getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(float conditionValue) {
        this.conditionValue = conditionValue;
    }
}
