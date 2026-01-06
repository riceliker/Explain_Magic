package com.riceliker.ExplainMagic.Item;

import com.riceliker.ExplainMagic.Explain_Magic;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemRegistry
{
    // FUNC: Set Registry To Main Class
    private static void registerItem(String name, Item item) {
        Identifier id = new Identifier(Explain_Magic.MOD_ID, name);
        Registry.register(Registries.ITEM, id, item);
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
    // <---Go To Registry--->
    public static void registerModItems() {
        // Item Add There
        registerItem("compressed_bme_core", compressed_bme_core);
        registerItem("enrichment_core", enrichment_core);
        registerItem("mod_icon_item", mod_icon_item);

        addItemsToItemGroups();
    }
    // <---Set Group On Creative Tab--->
    private static void addItemsToItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS)
            .register(entries -> {
                // Item Add There
                entries.add(compressed_bme_core);
                entries.add(enrichment_core);
            });
    }

}
