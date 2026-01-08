package com.riceliker.ExplainMagic.BlockEntity;

import com.riceliker.ExplainMagic.Block.FacingPlayerBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import static com.riceliker.ExplainMagic.ExplainMagic.MOD_ID;

public class BlockEntityRegistry
{
    public BlockEntityRegistry()
    {
        registryBlock("bme_collection_machine",bme_block);
        bme_block_entity_type = BlockEntityType.Builder.create(BCMBlockEntity::new, bme_block).build();
        Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(MOD_ID, "bme_collection_machine_entity"), bme_block_entity_type);
    }
    public static void registryBlock(String name,Block block)
    {
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, name), block);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }
    public static final Block bme_block =
            new FacingPlayerBlock(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.DRIPSTONE_BLOCK)
                    .strength(1.0F, 3.0F)
            );
    public static BlockEntityType<BCMBlockEntity> bme_block_entity_type;
}
