package com.riceliker.ExplainMagic.GUI.BCMGUI;

import com.riceliker.ExplainMagic.GUI.ScreenUnit;
import com.riceliker.ExplainMagic.HashMap.EnrichmentHelper;
import com.riceliker.ExplainMagic.HashMap.FuelHelper;
import com.riceliker.ExplainMagic.Item.ItemRegistry;
import com.riceliker.ExplainMagic.Network.NetworkManager;
import com.riceliker.ExplainMagic.ScreenHandler.BCMGUIHandler;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;

import static com.riceliker.ExplainMagic.ExplainMagic.MOD_ID;


public class BCMGUI extends ScreenUnit<BCMGUIHandler> {
    // Calculate value
    private int move_item_index_fuel = -1;
    private int not_in_bag_index_fuel = -1;
    private int move_item_index_enrichment = -1;
    private int not_in_bag_index_enrichment = -1;
    private int collection_stage = 0;
    // From block entity
    private int create_bme_core_number;
    private int power_number;
    private int enrichment_speed;
    private ArrayList<Integer> no_in_bag_index = new ArrayList<>();
    private PlayerInventory inventory;

    public BCMGUI(BCMGUIHandler handler, PlayerInventory inventory, Text text) {
        super(handler, inventory, Text.of(""), 350, 200);
        no_in_bag_index.add(-1);
        no_in_bag_index.add(-1);
    }


    @Override
    public void unitInit() {
        CloseButton(330, 0, 20, 20);
    }

    // <---All Render--->
    @Override
    public void unitRender(DrawContext context, int mouseX, int mouseY, float delta) {
        drawText(context);
        getBCMValue();
        drawInformationTable(context);
        whatItemPutSlot(context);
        drawWorkTable(context);
        //sendBlockEntityUpdateToServerFuc();
    }


    public void drawWorkTable(DrawContext context) {
        Box(context, 160, 40, 100, 40, 0xFFAAAAAA);
        Slot(context, getItemStackBySlot(not_in_bag_index_fuel), 170, 50);
        Slot(context, getItemStackBySlot(not_in_bag_index_enrichment), 200, 45);
        if (create_bme_core_number > 0)
            Slot(context, new ItemStack(ItemRegistry.compressed_bme_core, create_bme_core_number), 230, 50);
        else
            Slot(context, ItemStack.EMPTY, 230, 50);

        writeText(context, 245, 90, Text.of("->"), false);
    }

    public void drawText(DrawContext context) {
        writeText(context, 150, 40, Text.translatable("gui.bcm." + MOD_ID + ".bme_collection_machine"), false);
        Text text = Text.translatable("gui.bcm." + MOD_ID + ".collection_stage").append(Text.literal(String.valueOf(collection_stage) + "%"));
        writeText(context, 200, 110, text, false);
    }

    public void drawInformationTable(DrawContext context) {
        Box(context, 20, 20, 80, 160, 0xFFFFFFFF);
        writeText(context, 60, 45, Text.translatable("gui.bcm." + MOD_ID + ".information"), false);
        writeText(context, 60, 55, Text.translatable("gui.bcm." + MOD_ID + ".power_storage"), false);
        writeText(context, 60, 65, Text.literal(String.valueOf(power_number)), false);
        writeText(context, 60, 75, Text.translatable("gui.bcm." + MOD_ID + ".enrichment"), false);
        writeText(context, 60, 85, Text.literal(String.valueOf(enrichment_speed)), false);
    }

    public void whatItemPutSlot(DrawContext context) {
        if (move_item_index_fuel != -1)
            no_in_bag_index.set(0, not_in_bag_index_fuel);
        else
            no_in_bag_index.set(0, -1);
        if (move_item_index_enrichment != -1)
            no_in_bag_index.set(1, not_in_bag_index_enrichment);
        else
            no_in_bag_index.set(1, -1);
        ItemSlot(context, 120, 110, no_in_bag_index);
    }

    @Override
    public void unitOnClick(double mouseX, double mouseY, int button) {
        int move_item_index = ItemSlotClick(120, 110, mouseX, mouseY);
        ItemStack what_item = getItemStackBySlot(move_item_index);
        if (FuelHelper.isFuel(what_item.getItem())) {
            move_item_index_fuel = move_item_index;
            if (not_in_bag_index_fuel == -1)
                not_in_bag_index_fuel = move_item_index_fuel;

        }
        if (EnrichmentHelper.isEnrichment(what_item.getItem())) {
            move_item_index_enrichment = move_item_index;
            if (not_in_bag_index_enrichment == -1)
                not_in_bag_index_enrichment = move_item_index_enrichment;
        }
        if (isClickThisSlot(170, 50, mouseX, mouseY)) {
            not_in_bag_index_fuel = -1;
        }
        if (isClickThisSlot(200, 45, mouseX, mouseY)) {
            not_in_bag_index_enrichment = -1;
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

    }

    // Screen Handler ===> Here
    public void getBCMValue() {
        create_bme_core_number = this.handler.getSyncedValue("create_bme_core_count");
        power_number = this.handler.getSyncedValue("power_number");
        enrichment_speed = this.handler.getSyncedValue("enrichment_speed");
    }
    // Here ===> Network ===> BlockEntity
    public void sendBlockEntityUpdateToServerFuc()
    {
        BlockPos pos = BCMGUIHandler.getBlockPos();
        HashMap<String,Integer> map = new HashMap<>();
        map.put("create_bme_core_number",this.create_bme_core_number);
        map.put("enrichment_speed",this.enrichment_speed);
        map.put("power_storage",this.power_number);
        sendBlockEntityUpdateToServer(pos, map);
    }
    public void sendBlockEntityUpdateToServer(BlockPos pos, HashMap<String, Integer> map)
    {
        map.put("create_bme_core_number",this.create_bme_core_number);
        map.put("enrichment_speed",this.enrichment_speed);
        map.put("power_storage",this.power_number);
        try {
            // 创建数据包
            NetworkManager.BlockEntityUpdatePayload payload = new NetworkManager.BlockEntityUpdatePayload(pos, map);
            // 发送数据包（Fabric封装的简洁API）
            ClientPlayNetworking.send(payload);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
