package com.lastimp.dgh.data;

import com.lastimp.dgh.api.enums.BodyCondition;
import com.lastimp.dgh.common.Register.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup.dgh", "dgh");

        for (BodyCondition condition : BodyCondition.values()) {
            this.add(condition.name(), condition.translation);
        }

        this.add("gui.dgh.health_gui.title", "健康状态");
        this.add(ModItems.HEALTH_SCANNER.get(), "健康扫描仪");
        this.add(ModItems.BLOOD_SCANNER.get(), "血液扫描仪");
        this.add(ModItems.BLOOD_PACK.get(), "血袋");
        this.add(ModItems.BANDAGE.get(), "绷带");
        this.add(ModItems.MORPHINE.get(), "吗啡");
        this.add(ModItems.OPERATING_BED_BLOCK_ITEM.get(), "手术床");

    }
}
