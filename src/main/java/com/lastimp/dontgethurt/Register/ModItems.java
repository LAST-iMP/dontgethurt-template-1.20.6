package com.lastimp.dontgethurt.Register;

import com.lastimp.dontgethurt.block.OperatingBedBlock;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lastimp.dontgethurt.DontGetHurt.MODID;


public class ModItems {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredBlock<Block> OPERATING_BED_BLOCK = BLOCKS.registerBlock(
            "operating_bed",
            OperatingBedBlock::new,
            BlockBehaviour.Properties.of()
                            .destroyTime(2.0f)
                            .explosionResistance(10.0f)
    );

    public static final DeferredItem<BlockItem> OPERATING_BED_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            "operating-bed",
            OPERATING_BED_BLOCK
    );

    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerItem(
            "example-item",
            Item::new,
            new Item.Properties()
                    .food(
                            new FoodProperties.Builder()
                                    .alwaysEdible()
                                    .nutrition(1)
                                    .saturationModifier(2f)
                                    .build()
                    )
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }

    // Add the example block item to the building blocks tab
    public static void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(OPERATING_BED_BLOCK_ITEM);
    }
}
