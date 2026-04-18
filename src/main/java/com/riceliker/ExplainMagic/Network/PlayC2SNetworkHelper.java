package com.riceliker.ExplainMagic.Network;

import com.riceliker.ExplainMagic.BlockEntity.BCMBlockEntity;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;


public class PlayC2SNetworkHelper {
    public static void registerPlayC2SPackets() {
        PayloadTypeRegistry.playC2S().register(
                BCMBlockEntityUpdateC2SPayload.ID,
                BCMBlockEntityUpdateC2SPayload.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(
                BCMBlockEntityUpdateC2SPayload.ID,
                (payload, context) -> {
                    context.server().execute(() -> {
                        try {
                            BlockPos pos = payload.pos();
                            int create_bme_core_number = payload.create_bme_core_number();
                            int power_number = payload.power_number();
                            int enrichment_speed = payload.enrichment_speed();

                            BCMBlockEntity.handleData(pos, create_bme_core_number, power_number, enrichment_speed);

                        } catch (Exception e) {
                            context.player().sendMessage(Text.literal("ERROR:DATA_CAN_NOT_BE_CREATED"));
                        }
                    });
                }
        );
    }
}
