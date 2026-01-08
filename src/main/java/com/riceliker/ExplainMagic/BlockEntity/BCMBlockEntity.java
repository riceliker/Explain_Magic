package com.riceliker.ExplainMagic.BlockEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

import static com.riceliker.ExplainMagic.BlockEntity.BlockEntityRegistry.bme_block_entity_type;

public class BCMBlockEntity extends BlockEntity
{

    public BCMBlockEntity(BlockPos pos, BlockState state)
    {
        super(bme_block_entity_type, pos, state);
    }
}
