package com.linkered.moreterracotta.datagen;

import com.linkered.moreterracotta.block.ModBlocks;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColorCollection;

/**
 * A vanilla terracotta block together with the stairs and slab derived from it.
 *
 * @param base   the vanilla terracotta block the variants are made from
 * @param stairs the stairs made from {@code base}
 * @param slab   the slab made from {@code base}
 */
record TerracottaSet(Block base, Block stairs, Block slab) {
	/**
	 * Every set: plain terracotta first, then the 16 dyed colors in {@link ColorCollection} order.
	 */
	static final List<TerracottaSet> ALL = createAll();

	private static List<TerracottaSet> createAll() {
		List<TerracottaSet> sets = new ArrayList<>(17);
		sets.add(new TerracottaSet(Blocks.TERRACOTTA, ModBlocks.TERRACOTTA_STAIRS, ModBlocks.TERRACOTTA_SLAB));
		ColorCollection.VALUES.forEach(color -> sets.add(new TerracottaSet(
			Blocks.DYED_TERRACOTTA.pick(color), ModBlocks.DYED_TERRACOTTA_STAIRS.pick(color), ModBlocks.DYED_TERRACOTTA_SLAB.pick(color)
		)));
		return List.copyOf(sets);
	}
}
