package com.lastimp.dgh.data;

import com.lastimp.dgh.Register.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
//        this.simpleBlockWithItem(ModItems.OPERATING_BED_BLOCK.get(),cubeAll(ModBlocks.RUBY_BLOCK.get()));
//        this.propertyBlock(ModBlocks.LAMP_BLOCK.get());
    }

//    public void propertyBlock(Block block){
//        var block_off = models().cubeAll(&quot;lamp_off&quot;,new ResourceLocation(ExampleMod.MODID,ModelProvider.BLOCK_FOLDER+&quot;/&quot;+&quot;zircon_lamp_off&quot;));
//        var block_on = models().cubeAll(&quot;lamp_on&quot;,new ResourceLocation(ExampleMod.MODID, ModelProvider.BLOCK_FOLDER+&quot;/&quot;+&quot;zircon_lamp_on&quot;));
//        getVariantBuilder(block).partialState().with(LampBlock.LIT,true)
//                .modelForState().modelFile(block_on).addModel()
//                .partialState().with(LampBlock.LIT,false)
//                .modelForState().modelFile(block_off).addModel();
//        simpleBlockItem(block,block_off);
//    }
}
