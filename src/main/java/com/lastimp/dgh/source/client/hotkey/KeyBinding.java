package com.lastimp.dgh.source.client.hotkey;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_DGH = "key.category.dgh";
    public static final String KEY_HEALTH_MENU = "key.dgh.health_menu";

    public static final KeyMapping OPEN_MENU_KEY = new KeyMapping(
            KEY_HEALTH_MENU, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O,KEY_CATEGORY_DGH
    );
}
