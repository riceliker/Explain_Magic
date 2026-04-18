package com.riceliker.ExplainMagic.ScreenHandler;

import com.riceliker.ExplainMagic.BlockEntity.BCMBlockEntity;
import com.riceliker.ExplainMagic.BlockEntity.BlockEntityRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;

public class BCMGUIHandler extends ScreenHandler
{
    private final ScreenHandlerContext context;
    private static BlockPos blockPos;
    private final Property create_bme_core_property = Property.create();
    private final Property power_number_property = Property.create();
    private final Property enrichment_speed_property = Property.create();
    public BCMGUIHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context)
    {
        super(ScreenHandlerRegistry.bcm_gui_handler, syncId);
        this.context = context;
        // 从上下文提取方块位置
        this.context.get((world, pos) -> this.blockPos = pos);
        // 注册同步属性（自动同步到客户端）
        this.addProperty(this.create_bme_core_property);
        this.addProperty(this.power_number_property);
        this.addProperty(this.enrichment_speed_property);
        // 初始化同步数值
        this.updateValueFromBlockEntity();
        System.out.println();
    }
    // <=== BLOCK ENTITY
    public void updateValueFromBlockEntity() {
        this.context.get((world, pos) -> {
            BCMBlockEntity be = BCMBlockEntity.getFromWorld(world, pos);
            if (be != null) {
                this.create_bme_core_property.set(be.getCustomValue(pos,"create_bme_core_count"));
                this.power_number_property.set(be.getCustomValue(pos,"power_number"));
                this.enrichment_speed_property.set(be.getCustomValue(pos,"enrichment_speed"));
            }
            return be;
        });
    }

    // Get block
    public static BlockPos getBlockPos() {
        return blockPos;
    }
    // ===> SCREEN
    public int getSyncedValue(String key) {
        return switch (key) {
            case "create_bme_core_count" -> this.create_bme_core_property.get();
            case "power_number" -> this.power_number_property.get();
            case "enrichment_speed" -> this.enrichment_speed_property.get();
            default -> 0;
        };
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, BlockEntityRegistry.bme_block);
    }
}
