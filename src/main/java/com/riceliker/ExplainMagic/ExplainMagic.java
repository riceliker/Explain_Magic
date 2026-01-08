package com.riceliker.ExplainMagic;

import com.riceliker.ExplainMagic.Block.BlockRegistry;
import com.riceliker.ExplainMagic.BlockEntity.BlockEntityRegistry;
import com.riceliker.ExplainMagic.CreativeTab.CreativeTab;
import com.riceliker.ExplainMagic.Item.ItemRegistry;
import com.riceliker.ExplainMagic.Network.NetworkRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.data.server.recipe.RecipeExporter;

@SuppressWarnings("ALL")
public class ExplainMagic implements ModInitializer {
	public static final String MOD_ID = "explain_magic";

	private RecipeExporter exporter;


	@Override
	public void onInitialize() {
		// All registy will start here
		new ItemRegistry();
		new BlockRegistry();
		new BlockEntityRegistry();
		new CreativeTab();
		new NetworkRegistry();

	}
}