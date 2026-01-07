package com.riceliker.ExplainMagic.Block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

public class BMECollectionMachine extends HorizontalFacingBlock
{
    public static final DirectionProperty facing = Properties.HORIZONTAL_FACING;
    public BMECollectionMachine(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(facing, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec()
    {
        return createCodec((java.util.function.Function<Settings, ? extends HorizontalFacingBlock>) BMECollectionMachine::new);
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction playerLook = ctx.getPlayerLookDirection();
        Direction finalFacing = playerLook.getAxis().isHorizontal() ? playerLook : Direction.NORTH;

        return this.getDefaultState().with(facing, finalFacing);
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(facing);
    }
}
