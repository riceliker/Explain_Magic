package com.riceliker.ExplainMagic.BlockEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

import static com.riceliker.ExplainMagic.BlockEntity.BlockEntityRegistry.bme_block_entity_type;

public class BCMBlockEntity extends BlockEntity
{
    private int customData;
    public BCMBlockEntity(BlockPos pos, BlockState state)
    {
        super(bme_block_entity_type, pos, state);
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

}
