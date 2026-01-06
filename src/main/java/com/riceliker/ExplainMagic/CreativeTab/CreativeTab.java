package com.riceliker.ExplainMagic.CreativeTab;

import com.riceliker.ExplainMagic.Item.ItemRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;


public class CreativeTab
{
    // 1. 声明自定义物品组（静态常量）
    // FabricItemGroup.builder() 用于构建自定义物品组
    public static final ItemGroup creative_tab = FabricItemGroup.builder()
            // 设置物品组的图标（这里用你之前注册的 custom_item 作为图标）
            .icon(() -> new ItemStack(ItemRegistry.mod_icon_item))
            // 设置物品组的显示名称（支持国际化，这里先写硬编码示例，后续可替换为语言文件键）
            .displayName(Text.literal("My Custom Tab"))
            // 定义物品组中显示的物品列表
            .entries((context, entries) -> {
                // Item Add There
                entries.add(ItemRegistry.compressed_bme_core);
                entries.add(ItemRegistry.enrichment_core);
            })
            .build();

}
