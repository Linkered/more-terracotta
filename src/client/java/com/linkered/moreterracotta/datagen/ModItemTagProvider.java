package com.linkered.moreterracotta.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockItemTags;

/**
 * Mirrors the {@code stairs} and {@code slabs} block tags into their item counterparts.
 */
final class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
	ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries, ModBlockTagProvider blockTags) {
		super(output, registries, blockTags);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		copy(BlockItemTags.STAIRS);
		copy(BlockItemTags.SLABS);
	}
}
