package com.lastimp.dgh.data;

import com.lastimp.dgh.common.Register.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        this.add(ModItems.OPERATING_BED_BLOCK.get(), "手术床");
        this.add(ModItems.OPERATING_BED_BLOCK_ITEM.get(), "手术床");
        this.add(ModItems.HEALTH_SCANNER.get(), "健康扫描仪");
        this.add(ModItems.BLOOD_PACK.get(), "血袋");
        this.add(ModItems.BLOOD_SCANNER.get(), "血液扫描仪");

    }
}
