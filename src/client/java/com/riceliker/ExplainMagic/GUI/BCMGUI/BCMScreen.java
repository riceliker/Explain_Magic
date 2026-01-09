package com.riceliker.ExplainMagic.GUI.BCMGUI;

import com.mojang.blaze3d.systems.RenderSystem;
import com.riceliker.ExplainMagic.HashMap.FuelHelper;
import com.riceliker.ExplainMagic.Network.BCMDataPackage;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class BCMScreen extends Screen
{
    private int centerX;
    private int centerY;
    private static final int GUI_WIDTH = 350;
    private static final int GUI_HEIGHT = 200;
    private PlayerInventory playerInventory;
    private int clean_fuel_slot_index = -1;
    private boolean add_fuel_slot_back = false;
    public BCMScreen() {
        super(Text.literal("BME Collection Machine"));
    }
    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
    }

    @Override
    protected void init()
    {
        if (this.client != null && this.client.player != null) {
            this.playerInventory = this.client.player.getInventory();
        } else {
            this.playerInventory = null;
            return;
        }
        super.init();
        centerX = (this.width - GUI_WIDTH) / 2;
        centerY = (this.height - GUI_HEIGHT) / 2;

        // 1. 关闭按钮
        this.addDrawableChild(ButtonWidget.builder(
                        Text.literal("✕"),
                        button -> this.close()
                ).dimensions(centerX + 250, centerY + 180, 100, 20) // 界面底部居中
                .build());
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // 1. 渲染默认背景
        this.renderBackground(context, mouseX, mouseY, delta);
        // 2. 计算GUI左上角坐标（居中显示）
        int guiX = (this.width - GUI_WIDTH) / 2;
        int guiY = (this.height - GUI_HEIGHT) / 2;
        RenderSystem.enableBlend();
        // background
        context.fill(
                guiX, guiY,
                guiX + GUI_WIDTH, guiY + GUI_HEIGHT,
                0xFF404040
        );
        RenderSystem.disableBlend();
        guiX += 15;
        // 4. 渲染左侧大区域（示例：灰色背景块）
        context.fill(
                guiX + 20, guiY + 10,
                guiX + 100, guiY + 170,
                0xFF808080 // 灰色填充
        );

        // 5. 渲染右侧小模块
        renderRightModule(context, guiX, guiY);

        // 6. 渲染右侧物品栏（图片下方的格子区域）
        renderItemGrid(context, guiX, guiY, mouseX, mouseY);
        // 7. 渲染组件（按钮等）
        super.render(context, mouseX, mouseY, delta);
    }

    // Right
    private void renderRightModule(DrawContext context, int guiX, int guiY) {
        // background
        context.fill(
                guiX + 155, guiY + 10,
                guiX + 265, guiY + 50,
                0xFFAAAAAA
        );
        // input
        context.fill(
                guiX + 165, guiY + 20,
                guiX + 185, guiY + 40,
                0xFFCCCCCC
        );
        // output
        context.fill(
                guiX + 235, guiY + 20,
                guiX + 255, guiY + 40,
                0xFFCCCCCC
        );
        // enrichment_item
        context.fill(
                guiX + 200, guiY + 15,
                guiX + 220, guiY + 35,
                0xFFCCCCCC
        );
        // text "→"
        context.drawText(
                this.textRenderer,
                Text.literal("→"),
                guiX + 208, guiY + 40,
                0xFF000000,
                false
        );
    }


    // Item Tab
    private void renderItemGrid(DrawContext context, int guiX, int guiY, int mouseX, int mouseY) {
        int gridX = guiX + 120;
        int gridY = guiY + 65;

        // Slot
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 9; col++) {
                int x = gridX + col * 20;
                int y = gridY + row * 20;
                context.fill(
                        x, y,
                        x + 20, y + 20,
                        0xFFCCCCCC
                );
            }
        }
        for (int i = 0; i < 36; i++) {
            int row = i / 9;
            int col = i % 9;
            if (i != clean_fuel_slot_index)
            {

                context.drawItem(
                        playerInventory.getStack(i),
                        gridX + (col * 20) + 1,
                        gridY + (row * 20) + 1,
                        i
                );
                context.drawItemInSlot(
                        this.textRenderer,
                        playerInventory.getStack(i),
                        gridX + (col * 20) + 1,
                        gridY + (row * 20) + 1
                );
            }
            else
            {
                if (add_fuel_slot_back)
                {
                    context.drawItem(
                            playerInventory.getStack(i),
                            gridX + (col * 20) + 1,
                            gridY + (row * 20) + 1,
                            i
                    );
                    context.drawItemInSlot(
                            this.textRenderer,
                            playerInventory.getStack(i),
                            gridX + (col * 20) + 1,
                            gridY + (row * 20) + 1
                    );
                    context.fill(
                            guiX + 165, guiY + 20,
                            guiX + 185, guiY + 40,
                            0xFFCCCCCC
                    );
                }
                else
                {
                    context.drawItem(
                            playerInventory.getStack(i),
                            guiX + 165,
                            guiY + 20,
                            0
                    );
                    context.drawItemInSlot(
                            this.textRenderer,
                            playerInventory.getStack(i),
                            guiX + 165,
                            guiY + 20
                    );
                }

            }

        }
    }

    //  Click Item Tab
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int guiX = (this.width - GUI_WIDTH) / 2;
        int guiY = (this.height - GUI_HEIGHT) / 2;
        int gridX = guiX + 120;
        int gridY = guiY + 65;
        // 判断是否点击物品栏
        if (mouseX >= gridX && mouseX <= gridX + 190 &&
                mouseY >= gridY - 10 && mouseY <= gridY + 100) {
            int col = (int)((mouseX - gridX) / 20);
            int row = (int)((mouseY - gridY) / 20);
            int slotIndex = row * 9 + col - 1;
            if (0 <= slotIndex && slotIndex < 37) {
                System.out.println(slotIndex);
                if (FuelHelper.isFuel(playerInventory.getStack(slotIndex).getItem()))
                {
                    clean_fuel_slot_index = slotIndex;
                    add_fuel_slot_back = false;
                }
                return true;
            }
        }
        if (mouseX >= guiX + 165 && mouseX <= guiX + 195 &&
                mouseY >= guiY + 20 && mouseY <= guiY + 50)
        {
            if (clean_fuel_slot_index != -1)
            {
                add_fuel_slot_back = true;
                clean_fuel_slot_index = -1;
            }
            System.out.println("fuel");
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }


    @Override
    public void close() {
        assert this.client != null;
        this.client.setScreen(null);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    public void setValueAndSend(BlockPos newBlockPos, int newValue) {
        // 1. 装包裹：把坐标和新value装进去
        BCMDataPackage payload = new BCMDataPackage(newBlockPos, newValue);
        // 2. 寄包裹：发给服务端（就这一行，不用管其他）
        ClientPlayNetworking.send(payload);
    }
}
