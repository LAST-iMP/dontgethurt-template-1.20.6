package com.lastimp.dgh.api.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<Item> SHEARS = ItemTags.create(new ResourceLocation("c", "shears"));
    public static final TagKey<Item> NUGGETS = ItemTags.create(new ResourceLocation("c", "nuggets"));
    public static final TagKey<Item> STRING = ItemTags.create(new ResourceLocation("c", "string"));
    public static final TagKey<Item> IRON_INGOT = ItemTags.create(new ResourceLocation("c", "iron_ingots"));
    public static final TagKey<Item> IRON_NUGGET = ItemTags.create(new ResourceLocation("c", "iron_nuggets"));
}
