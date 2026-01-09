package com.riceliker.ExplainMagic.BlockEntity;

import com.riceliker.ExplainMagic.Network.BCMDataPackage;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import static com.riceliker.ExplainMagic.BlockEntity.BlockEntityRegistry.bme_block_entity_type;

public class BCMBlockEntity extends BlockEntity
{
    private int customData;
    private int value;
    private PlayerEntity player;
    private final BlockPos pos;
    public BCMBlockEntity(BlockPos pos, BlockState state)
    {
        super(bme_block_entity_type, pos, state);
        this.pos = pos;
    }
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    public void saveData()
    {
        this.markDirty();
        if (this.world != null) {
            this.world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), 3);
        }
    }
    public void setPlayer(PlayerEntity player)
    {
        this.player = player;
    }
    //<---NBT Create Here--->
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putInt("customData", this.customData); // 保存自定义数据
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.customData = nbt.getInt("customData"); // 读取自定义数据
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return this.createNbt(registryLookup);
    }
    //<---NBT Data Here--->
    public void setCustomData(int value) {
        this.customData = value;
        saveData();
    }
    public void addCustomData(int value) {
        this.customData += value;
        saveData();
    }
    public int getCustomData() {
        return this.customData;
    }
    // 收到A类的value后，保存起来
    public void setValue(int newValue) {
        this.value = newValue;
        this.markDirty(); // 保存到游戏里（固定写法）
        // 可选：把新value回传给客户端，刷新GUI
        syncValueToClient();
    }

    // 读取value（其他地方要用时调用）
    public int getValue() {
        return this.value;
    }

    // 可选：服务端把value回传给客户端（固定写法）
    private void syncValueToClient() {
        if (world == null || world.isClient()) return;
        BCMDataPackage payload = new BCMDataPackage(this.pos, this.value);
        if (this.player instanceof ServerPlayerEntity serverPlayer) {
            // HERE => Network
            ServerPlayNetworking.send(serverPlayer, payload);
        }

    }

}
