package com.riceliker.ExplainMagic.GUI.BCMGUI;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class BCMScreen extends Screen
{
    public BCMScreen() {
        super(Text.literal("我的自定义 GUI"));
    }
    private int centerX;
    private int centerY;
    // 初始化 GUI 组件（按钮、输入框等）
    @Override
    protected void init() {
        super.init();

        // 获取屏幕中心坐标
        centerX = this.width / 2;
        centerY = this.height / 2;

        // 添加按钮组件
        this.addDrawableChild(ButtonWidget.builder(
                        Text.literal("关闭 GUI"),  // 按钮文字
                        button -> this.close()     // 按钮点击事件：关闭 GUI
                ).dimensions(centerX - 50, centerY + 20, 100, 20)  // 按钮位置和大小（x, y, 宽度, 高度）
                .build());
    }

    // 渲染 GUI 背景和内容
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // 渲染背景（使用 Minecraft 默认的半透明背景）
        this.renderBackground(context, mouseX, mouseY, delta);

        // 渲染标题文字（居中显示）
        context.drawCenteredTextWithShadow(
                this.textRenderer,  // 文字渲染器
                this.title,         // 标题文字
                this.width / 2,     // x 坐标（屏幕中心）
                40,                 // y 坐标
                0xFFFFFF            // 文字颜色（白色）
        );

        // 渲染额外文字
        context.drawText(
                this.textRenderer,
                Text.literal("你点击了自定义方块！"),
                centerX - 80,
                centerY - 20,
                0x00FF00,  // 绿色
                false      // 是否有阴影
        );

        // 必须调用父类方法，否则按钮等组件不会渲染
        super.render(context, mouseX, mouseY, delta);
    }

    // 关闭 GUI 时返回游戏界面
    @Override
    public void close() {
        assert this.client != null;
        this.client.setScreen(null); // 关闭当前 GUI
    }

    // 禁止点击 GUI 外的区域关闭（可选）
    @Override
    public boolean shouldPause() {
        return false; // false = 打开 GUI 时游戏不暂停
    }

}
