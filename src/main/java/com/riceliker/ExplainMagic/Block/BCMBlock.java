package com.riceliker.ExplainMagic.Block;

import com.mojang.serialization.MapCodec;
import com.riceliker.ExplainMagic.BlockEntity.BCMBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BCMBlock extends BlockWithEntity {
    public static final DirectionProperty facing = Properties.HORIZONTAL_FACING;

    public BCMBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(facing, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(BCMBlock::new);
    }
    // Make sure the block will look at player when place block.
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

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BCMBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    // When player right check.
    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BCMBlockEntity bcmBlockEntity) {
            //<---Code will run when player right click--->
            bcmBlockEntity.addCustomData(1);
            player.sendMessage(Text.literal("CustomData: " + bcmBlockEntity.getCustomData()), true);
        }

        return ActionResult.CONSUME;
    }
}
