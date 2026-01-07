package com.riceliker.ExplainMagic.Block;

import com.riceliker.ExplainMagic.Explain_Magic;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class BlockRegistry
{
    // Registry Init -> Main Class
    public BlockRegistry()
    {

    }
    // Block Register
    public static Block register(Block block, String name) {
        Identifier id = new Identifier(Explain_Magic.MOD_ID, name);
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        return Registry.register(Registries.BLOCK, id, block);
    }

    // <---Block Information--->
    public static final Block enrichment_core_ore = register(
            new Block(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.NETHER_ORE)
                    .strength(1.0F, 3.0F)
                    .requiresTool()
            ),
            "enrichment_core_ore"
    );
    public static final Block enrichment_core_block = register(
            new Block(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.DRIPSTONE_BLOCK)
                    .strength(2.0F, 3.0F)
                    .requiresTool()
            ),
            "enrichment_core_block"
    );
    public static final Block compressed_bme_block = register(
            new Block(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.DRIPSTONE_BLOCK)
                    .strength(2.0F, 3.0F)
                    .requiresTool()
            ),
            "compressed_bme_block"
    );

}
