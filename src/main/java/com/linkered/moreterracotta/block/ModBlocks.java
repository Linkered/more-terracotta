package com.linkered.moreterracotta.block;

import com.linkered.moreterracotta.MoreTerracotta;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColorCollection;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockBehaviour.StateArgumentPredicate;
import net.minecraft.world.phys.AABB;

/**
 * Registers the stairs and slabs for plain terracotta and the 16 dyed terracotta colors.
 *
 * <p>The dyed variants are indexed by {@link DyeColor}, like vanilla's own colored blocks.
 */
public final class ModBlocks {
	/**
	 * Mirrors vanilla's private {@code Blocks.NEAR_PLANE_INTERSECTS_OUTLINE}, used by every vanilla stair and slab
	 * so partial blocks only obstruct the camera where their outline actually is.
	 */
	private static final StateArgumentPredicate<AABB> NEAR_PLANE_INTERSECTS_OUTLINE = (state, level, pos, nearPlaneBox) -> {
		for (AABB outlineBox : state.getOcclusionShape().toAabbs()) {
			if (outlineBox.move(pos).intersects(nearPlaneBox)) {
				return true;
			}
		}

		return false;
	};

	/**
	 * Vanilla's local {@code gameplayColorOrder} from {@code CreativeModeTabs.bootstrap}, used to order the
	 * Colored Blocks tab.
	 */
	private static final List<DyeColor> GAMEPLAY_COLOR_ORDER = List.of(
		DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK, DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW,
		DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE, DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK
	);

	/**
	 * Stairs made from plain {@link Blocks#TERRACOTTA}.
	 */
	public static final Block TERRACOTTA_STAIRS = registerStairs("terracotta_stairs", Blocks.TERRACOTTA);
	/**
	 * Slab made from plain {@link Blocks#TERRACOTTA}.
	 */
	public static final Block TERRACOTTA_SLAB = registerSlab("terracotta_slab", Blocks.TERRACOTTA);
	/**
	 * Stairs made from each block of {@link Blocks#DYED_TERRACOTTA}, one per {@link DyeColor}.
	 */
	public static final ColorCollection<Block> DYED_TERRACOTTA_STAIRS = ColorCollection.zipMap(
		ColorCollection.NAMES, Blocks.DYED_TERRACOTTA, (color, base) -> registerStairs(color + "_terracotta_stairs", base)
	);
	/**
	 * Slabs made from each block of {@link Blocks#DYED_TERRACOTTA}, one per {@link DyeColor}.
	 */
	public static final ColorCollection<Block> DYED_TERRACOTTA_SLAB = ColorCollection.zipMap(
		ColorCollection.NAMES, Blocks.DYED_TERRACOTTA, (color, base) -> registerSlab(color + "_terracotta_slab", base)
	);

	private ModBlocks() {
	}

	private static Block registerStairs(String name, Block base) {
		return register(name, p -> new StairBlock(base.defaultBlockState(), p), base);
	}

	private static Block registerSlab(String name, Block base) {
		return register(name, SlabBlock::new, base);
	}

	private static Block register(String name, Function<Properties, Block> factory, Block base) {
		BlockItemId id = BlockItemId.create(MoreTerracotta.id(name), MoreTerracotta.id(name));

		Block block = Registry.register(BuiltInRegistries.BLOCK, id.block(),
			factory.apply(Properties.ofFullCopy(base).isViewBlocking(NEAR_PLANE_INTERSECTS_OUTLINE).setId(id.block())));

		BlockItem item = new BlockItem(block, new Item.Properties().setId(id.item()).useBlockDescriptionPrefix());
		item.registerBlocks(Item.BY_BLOCK, item);
		Registry.register(BuiltInRegistries.ITEM, id.item(), item);
		return block;
	}

	/**
	 * Forces class loading (and thus registration) and places every variant in the Colored Blocks tab.
	 *
	 * <p>They go right after the last dyed terracotta, following vanilla's glass layout where the plain variant
	 * leads each shape group: plain stairs and the 16 dyed stairs, then plain slab and the 16 dyed slabs.
	 */
	public static void init() {
		Item lastDyedTerracotta = Items.DYED_TERRACOTTA.pick(GAMEPLAY_COLOR_ORDER.getLast());

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COLORED_BLOCKS).register(output -> {
			List<ItemStack> stacks = new ArrayList<>((GAMEPLAY_COLOR_ORDER.size() + 1) * 2);
			addGroup(stacks, TERRACOTTA_STAIRS, DYED_TERRACOTTA_STAIRS);
			addGroup(stacks, TERRACOTTA_SLAB, DYED_TERRACOTTA_SLAB);
			output.insertAfter(lastDyedTerracotta, stacks);
		});
	}

	private static void addGroup(List<ItemStack> stacks, Block plain, ColorCollection<Block> dyed) {
		stacks.add(new ItemStack(plain));
		GAMEPLAY_COLOR_ORDER.forEach(color -> stacks.add(new ItemStack(dyed.pick(color))));
	}
}
