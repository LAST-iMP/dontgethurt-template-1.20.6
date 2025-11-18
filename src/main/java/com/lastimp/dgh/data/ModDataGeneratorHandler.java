package com.lastimp.dgh.data;

import com.lastimp.dgh.DontGetHurt;
import net.minecraft.data.DataProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = DontGetHurt.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModDataGeneratorHandler {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        ExistingFileHelper efh = event.getExistingFileHelper();

        event.getGenerator().addProvider(
                event.includeClient(),
                (DataProvider.Factory<ModLanguageProvider>) output -> new ModLanguageProvider(output, DontGetHurt.MODID, "zh_cn")
        );

        event.getGenerator().addProvider(
                event.includeClient(),
                (DataProvider.Factory<ModItemModelProvider>) output -> new ModItemModelProvider(output, DontGetHurt.MODID, efh)
        );

        event.getGenerator().addProvider(
                event.includeClient(),
                (DataProvider.Factory<ModBlockStateProvider>) output -> new ModBlockStateProvider(output, DontGetHurt.MODID, efh)
        );

        var lp = event.getLookupProvider();
        event.getGenerator().addProvider(
                event.includeServer(),
                (DataProvider.Factory<ModRecipeProvider>) output -> new ModRecipeProvider(output, lp)
        );
    }
}
