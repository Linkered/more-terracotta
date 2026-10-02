package com.linkered.moreterracotta.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;

final class ModLootTableProvider extends FabricBlockLootSubProvider {
	ModLootTableProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	/**
	 * Stairs drop themselves; slabs drop two items when broken as a double slab.
	 */
	@Override
	public void generate() {
		for (TerracottaSet set : TerracottaSet.ALL) {
			dropSelf(set.stairs());
			add(set.slab(), createSlabItemTable(set.slab()));
		}
	}
}
