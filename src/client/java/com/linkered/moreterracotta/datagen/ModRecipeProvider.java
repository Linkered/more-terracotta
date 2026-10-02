package com.linkered.moreterracotta.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.crafting.Recipe;

/**
 * Builds one vanilla {@link BlockFamily} per terracotta block so the standard family pipeline emits every recipe.
 *
 * <ul>
 *     <li>Crafting table: 4 stairs from 6 blocks, 6 slabs from 3 blocks.</li>
 *     <li>Stonecutter: 1 stairs or 2 slabs per block.</li>
 * </ul>
 */
final class ModRecipeProvider extends FabricRecipeProvider {
	private static final List<BlockFamily> FAMILIES = TerracottaSet.ALL.stream()
		.map(set -> new BlockFamily.Builder(set.base())
			.stairs(set.stairs())
			.slab(set.slab())
			.recipeGroupPrefix("terracotta")
			.generateStonecutterRecipe()
			.getFamily())
		.toList();

	ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
		return new RecipeProvider(recipes, advancements) {
			@Override
			public void buildRecipes() {
				FAMILIES.forEach(family -> generateRecipes(family, FeatureFlags.VANILLA_SET));
			}
		};
	}

	@Override
	public String getName() {
		return "More Terracotta Recipes";
	}
}
