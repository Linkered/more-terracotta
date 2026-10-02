package com.linkered.moreterracotta.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class MoreTerracottaDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		ModBlockTagProvider blockTags = pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider((output, registries) -> new ModItemTagProvider(output, registries, blockTags));
		pack.addProvider(ModLanguageProvider.English::new);
		pack.addProvider(ModLanguageProvider.Spanish::new);
	}
}
