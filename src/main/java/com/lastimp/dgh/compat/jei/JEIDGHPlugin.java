package com.lastimp.dgh.compat.jei;

import com.lastimp.dgh.DontGetHurt;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

public class JEIDGHPlugin implements IModPlugin{
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(DontGetHurt.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        // 注册自定义配方类别

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        // 注册具体的配方实例

    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        // 注册GUI交互器

    }

    // 可选项，但建议加上这一项，可以增强你mod的引导性
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        // 注册配方催化剂

    }
}
