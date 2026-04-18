package com.riceliker.ExplainMagic.CreativeTab;

import com.riceliker.ExplainMagic.Block.BlockRegistry;
import com.riceliker.ExplainMagic.BlockEntity.BlockEntityRegistry;
import com.riceliker.ExplainMagic.ExplainMagic;
import com.riceliker.ExplainMagic.Item.ItemRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


public class CreativeTab
{
    public CreativeTab()
    {
        Registry.register(Registries.ITEM_GROUP, new Identifier(ExplainMagic.MOD_ID, "custom_tab"), creative_tab);
    }
    public static final ItemGroup creative_tab = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ItemRegistry.mod_icon_item))
            .displayName(Text.translatable("itemGroup.explain_magic.custom_tab"))
            .entries((context, entries) -> {
                // Item Add There
                entries.add(ItemRegistry.compressed_bme_core);
                entries.add(ItemRegistry.enrichment_core);
                entries.add(BlockRegistry.enrichment_core_ore.asItem());
                entries.add(BlockRegistry.enrichment_core_block.asItem());
                entries.add(BlockRegistry.compressed_bme_block.asItem());
                entries.add(BlockEntityRegistry.bme_block.asItem());
            })
            .build();

}
