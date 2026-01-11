package com.riceliker.ExplainMagic.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Map;
/*
    There are tell you what item can be fired.
 */

public class FuelHelper {
    private static final Map<Item, Integer> FUEL_MAP = new HashMap<>();

    static {

        FUEL_MAP.put(Items.STICK, 100);
        FUEL_MAP.put(Items.WOODEN_PICKAXE, 200);
        FUEL_MAP.put(Items.WOODEN_AXE, 200);
        FUEL_MAP.put(Items.WOODEN_SWORD, 200);
        FUEL_MAP.put(Items.WOODEN_SHOVEL, 200);
        FUEL_MAP.put(Items.WOODEN_HOE, 200);
        FUEL_MAP.put(Items.OAK_PLANKS, 300);
        FUEL_MAP.put(Items.SPRUCE_PLANKS, 300);
        FUEL_MAP.put(Items.BIRCH_PLANKS, 300);
        FUEL_MAP.put(Items.JUNGLE_PLANKS, 300);
        FUEL_MAP.put(Items.ACACIA_PLANKS, 300);
        FUEL_MAP.put(Items.DARK_OAK_PLANKS, 300);
        FUEL_MAP.put(Items.CHERRY_PLANKS, 300);
        FUEL_MAP.put(Items.MANGROVE_PLANKS, 300);
        FUEL_MAP.put(Items.BAMBOO_PLANKS, 300);
        FUEL_MAP.put(Items.ACACIA_BOAT, 1200);
        FUEL_MAP.put(Items.BIRCH_BOAT, 1200);
        FUEL_MAP.put(Items.CHERRY_BOAT, 1200);
        FUEL_MAP.put(Items.DARK_OAK_BOAT, 1200);
        FUEL_MAP.put(Items.JUNGLE_BOAT, 1200);
        FUEL_MAP.put(Items.SPRUCE_BOAT, 1200);


        FUEL_MAP.put(Items.COAL, 1600);
        FUEL_MAP.put(Items.CHARCOAL, 1600);
        FUEL_MAP.put(Items.COAL_BLOCK, 16000);


        FUEL_MAP.put(Items.BLAZE_ROD, 2400);
        FUEL_MAP.put(Items.LAVA_BUCKET, 20000);


        FUEL_MAP.put(Items.BAMBOO, 50);
        FUEL_MAP.put(Items.SCAFFOLDING, 100);
        FUEL_MAP.put(Items.HAY_BLOCK, 3000);
        FUEL_MAP.put(Items.DRIED_KELP_BLOCK, 4000);
    }

    public static boolean isFuel(Item item) {
        return FUEL_MAP.containsKey(item);
    }

    public static int getBurnTimeInTicks(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        return FUEL_MAP.getOrDefault(stack.getItem(), 0);
    }

    public static double getBurnTimeInSeconds(ItemStack stack) {
        int ticks = getBurnTimeInTicks(stack);
        return (double) ticks / 20;
    }

    public static void addCustomFuel(Item item, int burnTimeTicks) {
        FUEL_MAP.put(item, burnTimeTicks);
    }
}
