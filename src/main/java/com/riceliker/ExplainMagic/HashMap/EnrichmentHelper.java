package com.riceliker.ExplainMagic.HashMap;

import com.riceliker.ExplainMagic.Item.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EnrichmentHelper
{
    private static final Map<Item, Integer> ENRICHMENT_MAP = new HashMap<>();

    static {
        ENRICHMENT_MAP.put(ItemRegistry.enrichment_core, 1);

    }

    public static boolean isEnrichment(Item item) {
        return ENRICHMENT_MAP.containsKey(item);
    }

    public static int getEnrichmentSpeed(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        return ENRICHMENT_MAP.getOrDefault(stack.getItem(), 0);
    }

}

