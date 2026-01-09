package com.riceliker.ExplainMagic.Network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import static com.riceliker.ExplainMagic.ExplainMagic.MOD_ID;

public class BCMDataPackage implements CustomPayload
{
    private BlockPos blockPos;
    private int value;
    // 1. 给包裹贴个唯一标签（防止和其他模组的包裹搞混）
    // 把 "your_mod" 改成你自己的模组ID（和fabric.mod.json里的id一致）
    public static final Id<BCMDataPackage> ID = new Id<>(Identifier.of(MOD_ID, "bcm_data_package"));
    public BlockPos getBlockPos()
    {
        return blockPos;
    }

    public int getValue()
    {
        return value;
    }
    public void setBlockPos(BlockPos blockPos)
    {
        this.blockPos = blockPos;
    }
    public void setValue(int value)
    {
        this.value = value;
    }
    public BCMDataPackage(BlockPos blockPos, int value)
    {
        this.blockPos = blockPos;
        this.value = value;
    }
    // 2. 告诉游戏怎么打包/拆包（固定写法，不用改）
    public static final PacketCodec<RegistryByteBuf, BCMDataPackage> CODEC = PacketCodec.of(
            // 打包：把包裹里的东西装进箱子
            (payload, buf) -> {
                buf.writeBlockPos(payload.blockPos);
                buf.writeInt(payload.value);
            },
            // 拆包：从箱子里拿出东西，重新拼成包裹
            (buf) -> new BCMDataPackage(buf.readBlockPos(), buf.readInt())
    );

    // 3. 告诉游戏这个包裹的标签是什么（固定写法）
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    public static void handlePayload(BCMDataPackage payload, ServerPlayNetworking.Context context)
    {
        BlockPos pos = payload.getBlockPos();
        int value = payload.getValue();
    }

    private static void registerServerRule() {
        ServerPlayNetworking.registerGlobalReceiver(
                BCMDataPackage.ID, // 监听“value_sync”标签的包裹
                BCMDataPackage::handlePayload); // 知道怎么拆包
    }


}
