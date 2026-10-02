package com.linkered.moreterracotta.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

final class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
	ModBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		TagAppender<Block> stairs = builder(BlockItemTags.STAIRS.block());
		TagAppender<Block> slabs = builder(BlockItemTags.SLABS.block());
		TagAppender<Block> pickaxe = builder(BlockTags.MINEABLE_WITH_PICKAXE);

		for (TerracottaSet set : TerracottaSet.ALL) {
			stairs.add(key(set.stairs()));
			slabs.add(key(set.slab()));
			pickaxe.add(key(set.stairs())).add(key(set.slab()));
		}
	}

	private static ResourceKey<Block> key(Block block) {
		return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
	}
}
