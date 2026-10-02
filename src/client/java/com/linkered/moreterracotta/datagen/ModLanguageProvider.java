package com.linkered.moreterracotta.datagen;

import com.linkered.moreterracotta.block.ModBlocks;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.ColorCollection;

abstract sealed class ModLanguageProvider extends FabricLanguageProvider {
	private final ColorCollection<String> colors;
	private final String stairs;
	private final String slab;
	private final String dyedStairs;
	private final String dyedSlab;

	private ModLanguageProvider(FabricPackOutput output, String languageCode, CompletableFuture<HolderLookup.Provider> registries,
			ColorCollection<String> colors, String stairs, String slab, String dyedStairs, String dyedSlab) {
		super(output, languageCode, registries);
		this.colors = colors;
		this.stairs = stairs;
		this.slab = slab;
		this.dyedStairs = dyedStairs;
		this.dyedSlab = dyedSlab;
	}

	@Override
	public void generateTranslations(HolderLookup.Provider registries, TranslationBuilder builder) {
		builder.add(ModBlocks.TERRACOTTA_STAIRS, stairs);
		builder.add(ModBlocks.TERRACOTTA_SLAB, slab);
		ColorCollection.VALUES.forEach(color -> {
			String name = colors.pick(color);
			builder.add(ModBlocks.DYED_TERRACOTTA_STAIRS.pick(color), dyedStairs.formatted(name));
			builder.add(ModBlocks.DYED_TERRACOTTA_SLAB.pick(color), dyedSlab.formatted(name));
		});
	}

	static final class English extends ModLanguageProvider {
		English(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
			super(output, "en_us", registries, new ColorCollection<>(
				"White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray",
				"Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black"
			), "Terracotta Stairs", "Terracotta Slab", "%s Terracotta Stairs", "%s Terracotta Slab");
		}
	}

	static final class Spanish extends ModLanguageProvider {
		Spanish(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
			super(output, "es_es", registries, new ColorCollection<>(
				"blanca", "naranja", "magenta", "azul claro", "amarilla", "verde lima", "rosa", "gris",
				"gris claro", "cian", "morada", "azul", "marrón", "verde", "roja", "negra"
			), "Escaleras de terracota", "Losa de terracota", "Escaleras de terracota %s", "Losa de terracota %s");
		}
	}
}
