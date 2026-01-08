package com.riceliker.ExplainMagic.Network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public class NetworkRegistry {
    public NetworkRegistry() {
        PayloadTypeRegistry.playS2C().register(
                SendBCMGUI.ID,
                net.minecraft.network.codec.PacketCodec.of(
                        SendBCMGUI::encode,
                        SendBCMGUI::decode
                )
        );
    }

    public static void sendMessage(String name, ServerPlayerEntity player) {
        switch (name)
        {
            case "BCMGUI":
                // HERE <= BCM Block
                ServerPlayNetworking.send(player,new SendBCMGUI());
                break;
            default:
                // Will be added in the future.
        }

    }
}
