package com.riceliker.ExplainMagic.Network;

import com.riceliker.ExplainMagic.ExplainMagic;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record BCMBlockEntityUpdateC2SPayload(BlockPos pos,int create_bme_core_number,int power_number,int enrichment_speed) implements CustomPayload
{
    public static final Id<BCMBlockEntityUpdateC2SPayload> ID =
            new Id<>(new Identifier(ExplainMagic.MOD_ID, "get_bcm_data"));

    private static final PacketCodec<RegistryByteBuf, BlockPos> BLOCK_POS_CODEC = PacketCodec.of(
            (pos, buf) -> buf.writeBlockPos(pos),
            buf -> buf.readBlockPos()
    );
    public static final PacketCodec<RegistryByteBuf, BCMBlockEntityUpdateC2SPayload> CODEC =
            PacketCodec.tuple(
                    BLOCK_POS_CODEC,
                    BCMBlockEntityUpdateC2SPayload::pos,
                    PacketCodecs.INTEGER,
                    BCMBlockEntityUpdateC2SPayload::create_bme_core_number,
                    PacketCodecs.INTEGER,
                    BCMBlockEntityUpdateC2SPayload::power_number,
                    PacketCodecs.INTEGER,
                    BCMBlockEntityUpdateC2SPayload::enrichment_speed,
                    BCMBlockEntityUpdateC2SPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
