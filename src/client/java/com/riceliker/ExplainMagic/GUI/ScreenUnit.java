package com.riceliker.ExplainMagic.GUI;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

import java.util.ArrayList;

/*
    The abstract class will help you draw GUI easily.
    If you want to add you own GUI style, please add the code here.
    If your code about item logic and network, DO NOT put there.
 */
public abstract class ScreenUnit<T extends ScreenHandler> extends HandledScreen<T>
{
    private final int GUI_WEIGHT;
    private final int GUI_HEIGHT;
    private int X;
    private int Y;
    private PlayerInventory playerInventory;
    private float account_tick;
    public ScreenUnit(T handler, PlayerInventory inventory,Text title, int GUI_WEIGHT , int GUI_HEIGHT)
    {
        super(handler, inventory, Text.empty());
        this.GUI_WEIGHT = GUI_WEIGHT;
        this.GUI_HEIGHT = GUI_HEIGHT;
    }
    public ScreenUnit(T handler, PlayerInventory inventory, Text title)
    {
        super(handler, inventory, Text.empty());
        this.GUI_WEIGHT = 350;
        this.GUI_HEIGHT = 200;
    }
    @Override
    public final void init()
    {
        if (this.client != null && this.client.player != null) {
            this.playerInventory = this.client.player.getInventory();
        } else {
            this.playerInventory = null;
            return;
        }
        this.X = (this.width - GUI_WEIGHT) / 2;
        this.Y = (this.height - GUI_HEIGHT) / 2;
        super.init();
        unitInit();
    }
    public abstract void unitInit();
    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {}
    @Override
    public final void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        this.renderBackground(context, mouseX, mouseY, delta);
        RenderSystem.enableBlend();
        context.fill(
                X, Y,
                X + GUI_WEIGHT, Y + GUI_HEIGHT,
                0xFF808080
        );
        RenderSystem.disableBlend();
        unitRender(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }
    public abstract void unitRender(DrawContext context, int mouseX, int mouseY, float delta);
    public void CloseButton(int x, int y, int width, int height)
    {
        this.addDrawableChild(ButtonWidget.builder(
                        Text.literal("✕"),
                        button -> this.close()
                ).dimensions(X + x, Y + y, width, height)
                .build());
    }
    public void Button(int x, int y, int width, int height, Text text, ButtonWidget.PressAction action)
    {
        this.addDrawableChild(ButtonWidget.builder(
                        text,
                        action
                ).dimensions(X + x, Y + y, width, height)
                .build());
    }
    public void Box(DrawContext context, int x, int y, int width, int height, int Color)
    {
        context.fill(
                X + x, Y + y,
                X + x + width, Y + y + height,
                Color
        );
    }
    public void writeText(DrawContext context, int x, int y, Text text, Boolean shadow)
    {
        context.drawText(
                this.textRenderer,
                text,
                x, y,
                0xFF000000,
                shadow
        );
    }
    public void Slot(DrawContext context, ItemStack itemStack, int x, int y)
    {
        context.fill(
                X + x, Y + y,
                X + x + 20, Y + y + 20,
                0xFFCCCCCC
        );
        context.drawItem(
                itemStack,
                X + x,
                Y + y,
                0
        );
        context.drawItemInSlot(
                this.textRenderer,
                itemStack,
                X + x,
                Y + y
        );
    }

    public void ItemSlot(DrawContext context, int x, int y, ArrayList<Integer> no_in_bag_index)
    {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 9; col++) {
                int dx = X + x + col * 20 + 1;
                int dy = Y + y + row * 20 + 1;
                context.fill(
                        dx, dy,
                        dx + 20, dy + 20,
                        0xFFCCCCCC
                );
            }
        }
        for (int i = 0; i < 36; i++) {
            int row = i / 9;
            int col = i % 9;
            if (! no_in_bag_index.contains(i))
            {
                context.drawItem(
                        playerInventory.getStack(i),
                        X + x + (col * 20),
                        Y + y + (row * 20),
                        i
                );
                context.drawItemInSlot(
                        this.textRenderer,
                        playerInventory.getStack(i),
                        X + x + (col * 20),
                        Y + y + (row * 20)
                );
            }
        }
    }
    @Override
    public final boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        unitOnClick(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }
    public abstract void unitOnClick(double mouseX, double mouseY, int button);
    public boolean isClickThisSlot(int x, int y, double mouseX, double mouseY)
    {
        return mouseX >= X + x && mouseX <= X + x + 20 &&
                mouseY >= Y + y && mouseY <= Y + y + 20;
    }
    public int ItemSlotClick(int x, int y, double mouseX, double mouseY)
    {
        if (mouseX >= X + x && mouseX <= X + x + 20 * 9 &&
                mouseY >= Y + y && mouseY <= Y + y + 20 * 4)
        {
            int col = (int)((mouseX - (double) (X + x)) / 20);
            int row = (int)((mouseY - (double) (Y + y)) / 20);
            int slotIndex = row * 9 + col;
            if (0 <= slotIndex && slotIndex < 37) {
                return slotIndex;
            }

        }
        return -1;
    }
    public ItemStack getItemStackBySlot(int slotIndex)
    {
        if (slotIndex == -1)
            return ItemStack.EMPTY;
        else
            return playerInventory.getStack(slotIndex);
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
}
