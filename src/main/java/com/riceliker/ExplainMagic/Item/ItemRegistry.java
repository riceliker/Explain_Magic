package com.riceliker.ExplainMagic.Item;

import com.riceliker.ExplainMagic.ExplainMagic;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemRegistry
{
    public ItemRegistry()
    {
        // Item Add There
        registerItem("compressed_bme_core", compressed_bme_core);
        registerItem("enrichment_core", enrichment_core);
        registerItem("mod_icon_item", mod_icon_item);
    }
    private static void registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, new Identifier(ExplainMagic.MOD_ID, name), item);
    }
    // <---Item Information--->
    // Mod icon item only an image, not an item which you can get.
    public static final Item mod_icon_item = new Item(
            new Item.Settings()
    );
    public static final Item compressed_bme_core = new Item(
            new Item.Settings()
                    .maxCount(64)
    );
    public static final Item enrichment_core = new Item(
            new Item.Settings()
                    .maxCount(64)
    );
}
