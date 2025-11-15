package com.lastimp.dgh.Register;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.block.OperatingBedBlock;
import com.lastimp.dgh.item.HealthScanner;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lastimp.dgh.DontGetHurt.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModItems {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredBlock<Block> OPERATING_BED_BLOCK = BLOCKS.registerBlock(
            "operating_bed",
            OperatingBedBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.RED_BED)
    );

    public static final DeferredItem<BlockItem> OPERATING_BED_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            "operating-bed",
            OPERATING_BED_BLOCK
    );

    public static final DeferredItem<Item> HEALTH_SCANNER = ITEMS.registerItem(
            "health_saner",
            HealthScanner::new,
            new Item.Properties()
                    .stacksTo(1)
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(OPERATING_BED_BLOCK_ITEM);
    }
}
