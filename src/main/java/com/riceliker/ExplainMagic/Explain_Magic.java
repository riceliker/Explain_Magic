package com.riceliker.ExplainMagic;

import com.riceliker.ExplainMagic.Block.BlockRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.riceliker.ExplainMagic.CreativeTab.CreativeTab.creative_tab;
import static com.riceliker.ExplainMagic.Item.ItemRegistry.registerModItems;

@SuppressWarnings("ALL")
public class Explain_Magic implements ModInitializer {
	public static final String MOD_ID = "explain_magic";
	private RecipeExporter exporter;


	@Override
	public void onInitialize() {
		new BlockRegistry();
		registerModItems();

		Registry.register(Registries.ITEM_GROUP, new Identifier(Explain_Magic.MOD_ID, "custom_tab"), creative_tab);
	}
}