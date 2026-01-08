package com.riceliker.ExplainMagic.Network;

import com.riceliker.ExplainMagic.GUI.BCMGUI.BCMScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
public class GetBCMGUI
{
    public GetBCMGUI()
    {
        ClientPlayNetworking.registerGlobalReceiver(
                SendBCMGUI.ID,
                (payload, context) -> {
                    // 必须切换到客户端主线程处理GUI
                    context.client().execute(() -> {
                        MinecraftClient client = MinecraftClient.getInstance();
                        client.setScreen(new BCMScreen());
                    });
                }
        );
    }
}
