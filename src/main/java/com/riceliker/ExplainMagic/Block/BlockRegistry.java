package com.riceliker.ExplainMagic.Block;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import static com.riceliker.ExplainMagic.Explain_Magic.MOD_ID;

public class BlockRegistry
{
    // Registry Init -> Main Class
    public BlockRegistry()
    {
        // Block Add There
        registryBlock("enrichment_core_ore",enrichment_core_ore);
        registryBlock("enrichment_core_block",enrichment_core_block);
        registryBlock("compressed_bme_block",compressed_bme_block);
        registryBlock("bme_collection_machine",bme_block);
    }
    public static void registryBlock(String name,Block block)
    {
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, name), block);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    // <---Block Information--->
    public static final Block enrichment_core_ore =
            new Block(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.NETHER_ORE)
                    .strength(1.0F, 3.0F)
                    .requiresTool()
    );
    public static final Block enrichment_core_block =
            new Block(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.DRIPSTONE_BLOCK)
                    .strength(2.0F, 3.0F)
                    .requiresTool()

    );
    public static final Block compressed_bme_block =
            new Block(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.DRIPSTONE_BLOCK)
                    .strength(2.0F, 3.0F)
                    .requiresTool()

    );
    public static final Block bme_block =
            new FacingPlayerBlock(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.DRIPSTONE_BLOCK)
                    .strength(1.0F, 3.0F)

    );


}
