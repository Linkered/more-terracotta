package com.linkered.moreterracotta;

import com.linkered.moreterracotta.block.ModBlocks;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;

/**
 * Main entrypoint of More Terracotta, run by Fabric Loader on both client and server.
 */
public final class MoreTerracotta implements ModInitializer {
	/**
	 * The mod ID, also used as the namespace of every block, item and data file the mod adds.
	 */
	public static final String MOD_ID = "more-terracotta";

	/**
	 * Created by Fabric Loader through the {@code main} entrypoint declared in {@code fabric.mod.json}.
	 */
	public MoreTerracotta() {
	}

	@Override
	public void onInitialize() {
		ModBlocks.init();
	}

	/**
	 * Creates an identifier in the mod's namespace.
	 *
	 * @param path the path of the identifier
	 * @return {@code more-terracotta:<path>}
	 */
	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
