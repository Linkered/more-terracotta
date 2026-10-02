package com.linkered.moreterracotta.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

/**
 * Generates the blockstates, block models and item models for every stairs and slab.
 *
 * <p>Every model has a vanilla parent ({@code minecraft:block/slab}, {@code minecraft:block/stairs}, ...) and points
 * at the vanilla {@code minecraft:block/terracotta} or {@code minecraft:block/<color>_terracotta} texture. The double
 * slab reuses the vanilla full-block model directly.
 */
final class ModModelProvider extends FabricModelProvider {
	ModModelProvider(FabricPackOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators generators) {
		for (TerracottaSet set : TerracottaSet.ALL) {
			TextureMapping textures = TextureMapping.cube(set.base());
			stairs(generators, set.stairs(), textures);
			slab(generators, set.slab(), set.base(), textures);
		}
	}

	private static void stairs(BlockModelGenerators generators, Block stairs, TextureMapping textures) {
		MultiVariant inner = BlockModelGenerators.plainVariant(ModelTemplates.STAIRS_INNER.create(stairs, textures, generators.modelOutput));
		Identifier straight = ModelTemplates.STAIRS_STRAIGHT.create(stairs, textures, generators.modelOutput);
		MultiVariant outer = BlockModelGenerators.plainVariant(ModelTemplates.STAIRS_OUTER.create(stairs, textures, generators.modelOutput));
		generators.blockStateOutput.accept(BlockModelGenerators.createStairs(stairs, inner, BlockModelGenerators.plainVariant(straight), outer));
		generators.registerSimpleItemModel(stairs, straight);
	}

	private static void slab(BlockModelGenerators generators, Block slab, Block base, TextureMapping textures) {
		Identifier bottom = ModelTemplates.SLAB_BOTTOM.create(slab, textures, generators.modelOutput);
		MultiVariant top = BlockModelGenerators.plainVariant(ModelTemplates.SLAB_TOP.create(slab, textures, generators.modelOutput));
		MultiVariant full = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(base));
		generators.blockStateOutput.accept(BlockModelGenerators.createSlab(slab, BlockModelGenerators.plainVariant(bottom), top, full));
		generators.registerSimpleItemModel(slab, bottom);
	}

	/**
	 * Intentionally empty: item models are emitted alongside the block models in
	 * {@link #generateBlockStateModels(BlockModelGenerators)}.
	 */
	@Override
	public void generateItemModels(ItemModelGenerators generators) {
	}
}
