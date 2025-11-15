package com.lastimp.dgh.data;

import com.lastimp.dgh.Register.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        this.add(ModItems.OPERATING_BED_BLOCK.get(), "Operating Bed");

        this.add(ModItems.OPERATING_BED_BLOCK_ITEM.get(), "Operating Bed");
        this.add(ModItems.HEALTH_SCANNER.get(), "Health Scanner");

        this.add("gui.dgh.health_gui.title", "Health Scanner");
        this.add("gui.dgh.health_menu", "Health Menu");
    }
}
