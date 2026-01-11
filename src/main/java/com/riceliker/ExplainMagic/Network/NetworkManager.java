package com.riceliker.ExplainMagic.Network;

import com.riceliker.ExplainMagic.BlockEntity.BCMBlockEntity;
import com.riceliker.ExplainMagic.ExplainMagic;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

/**
 * 网络操作封装类（统一管理所有C2S/S2C数据包）
 * 对外仅暴露简洁API，隐藏底层网络细节
 */
public class NetworkManager {
    // ========== 1. 数据包ID常量（统一管理，避免硬编码） ==========
    // 方块实体更新数据包ID（格式：模组ID + 数据包名）
    public static final Identifier BLOCK_ENTITY_UPDATE_ID = new Identifier(ExplainMagic.MOD_ID, "block_entity_update");
    public static final CustomPayload.Id<? extends CustomPayload> ID = new CustomPayload.Id<BlockEntityUpdatePayload>(BLOCK_ENTITY_UPDATE_ID);

    // ========== 2. 自定义数据包定义（内部类，对外隐藏） ==========
    /**
     * 方块实体更新数据包（C2S：客户端→服务端）
     */
    public static class BlockEntityUpdatePayload implements CustomPayload {
        // 业务数据（可根据需求扩展）
        private BlockPos block_pos;
        private HashMap<String, Integer> value_map = new HashMap<>();

        // 构造器（客户端构建数据包）
        public BlockEntityUpdatePayload(BlockPos pos, HashMap<String, Integer> map) {
            this.block_pos = pos;
            this.value_map = map;
        }


        // 序列化：数据写入字节流
        public void write(PacketByteBuf buf) {
            buf.writeBlockPos(block_pos);
            buf.writeInt(this.value_map.get("create_bme_core_number"));
            buf.writeInt(this.value_map.get("enrichment_speed"));
            buf.writeInt(this.value_map.get("power_storage"));
        }

        // 反序列化：服务端读取数据
        public static BlockEntityUpdatePayload read(PacketByteBuf buf) {
            BlockPos pos = buf.readBlockPos();
            int createBmeCoreNumber = buf.readInt();
            int enrichmentSpeed = buf.readInt();
            int powerStorage = buf.readInt();
            HashMap<String, Integer> map = new HashMap<>();
            map.put("create_bme_core_number", createBmeCoreNumber);
            map.put("enrichment_speed", enrichmentSpeed);
            map.put("power_storage", powerStorage);
            return new BlockEntityUpdatePayload(pos, map);
        }

        // Getter（对外暴露数据）
        public BlockPos getBlockEntityPos() {
            return block_pos;
        }

        public HashMap<String, Integer> getProgressValue() {
            return value_map;
        }

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    // ========== 3. 客户端API（对外暴露，简洁调用） ==========
    /**
     * 客户端发送方块实体更新数据到服务端
     * @param blockEntityId 方块实体ID
     * @param progressValue 进度值
     */


    // ========== 4. 服务端API（对外暴露，简洁调用） ==========
    /**
     * 服务端注册所有数据包接收器（模组初始化时调用）
     */
    public void registerServerPackets() {
        // 注册方块实体更新数据包接收器
        //registerBlockEntityUpdateReceiver();
    }

    // ========== 5. 私有工具方法（封装重复逻辑，对外隐藏） ==========
    /**
     * 注册方块实体更新数据包接收器
     */
//    private void registerBlockEntityUpdateReceiver() {
//        ServerPlayNetworking.registerGlobalReceiver(
//                BLOCK_ENTITY_UPDATE_ID,
//                (server, player, handler, buf, responseSender) -> {
//                    // 服务端主线程处理（强制规范）
//                    () -> handleBlockEntityUpdate(server, player, buf);
//                }
//        );
//    }

    /**
     * 处理方块实体更新数据包（服务端核心逻辑）
     */
    private void handleBlockEntityUpdate(MinecraftServer server, BlockPos pos, PacketByteBuf buf) {
        try {
            // 反序列化数据
            BlockEntityUpdatePayload payload = BlockEntityUpdatePayload.read(buf);
            HashMap<String, Integer> map = payload.getProgressValue();

            // 调用业务类处理数据（存储到私有变量）
            //BCMBlockEntity.handleData(pos, map);

        } catch (Exception e) {
            logServerError("解析方块实体更新数据包失败", e);
        }
    }


    /**
     * 客户端日志（统一格式）
     */
    private void logClient(String msg) {
        System.out.println("[ExplainMagic-Network-Client] " + msg);
    }

    /**
     * 客户端错误日志
     */
    private void logClientError(String msg, Exception e) {
        System.err.println("[ExplainMagic-Network-Client-Error] " + msg);
        e.printStackTrace();
    }

    /**
     * 服务端日志（统一格式）
     */
    private void logServer(String msg) {
        System.out.println("[ExplainMagic-Network-Server] " + msg);
    }

    /**
     * 服务端错误日志
     */
    private void logServerError(String msg, Exception e) {
        System.err.println("[ExplainMagic-Network-Server-Error] " + msg);
        e.printStackTrace();
    }

    // ========== 私有化构造器（禁止实例化，仅作为工具类） ==========
    private NetworkManager() {}
}
