package com.riceliker.ExplainMagic.BlockEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

import static com.riceliker.ExplainMagic.BlockEntity.BlockEntityRegistry.bme_block_entity_type;

public class BCMBlockEntity extends BlockEntity
{
    private static final Map<BlockPos, Map<String, Integer>> block_pos_get_value= new HashMap<>();

    public BCMBlockEntity(BlockPos pos, BlockState state)
    {
        super(bme_block_entity_type, pos, state);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("create_bme_core_count",0);
        map.put("power_number",0);
        map.put("enrichment_speed",0);
        block_pos_get_value.put(pos, map);
    }
    // Get information about BCM position.
    public static BCMBlockEntity getFromWorld(World world, BlockPos pos) {
        if (world == null || pos == null) return null;
        BlockEntity be = world.getBlockEntity(pos);
        return be instanceof BCMBlockEntity ? (BCMBlockEntity) be : null;
    }


    // Sever <---> Client
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return this.createNbt(registryLookup);
    }
    //<---Data Here--->
    public int getCustomValue(BlockPos pos, String key)
    {
        return block_pos_get_value.get(pos).get(key);
    }
    //<---Network--->
    public static void handleData(BlockPos pos, int cbcc, int pn, int es)
    {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("create_bme_core_count",cbcc);
        map.put("power_number",pn);
        map.put("enrichment_speed",es);
        block_pos_get_value.put(pos, map);
    }

}
