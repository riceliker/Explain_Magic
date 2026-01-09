package com.riceliker.ExplainMagic.Network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static com.riceliker.ExplainMagic.ExplainMagic.MOD_ID;

public class OpenBCMGUIPackage implements CustomPayload
{
    public static final Id<OpenBCMGUIPackage> ID = new Id<>(new Identifier(MOD_ID, "send_bcm_gui"));

    public static OpenBCMGUIPackage decode(PacketByteBuf buf)
    {
        return new OpenBCMGUIPackage();
    }

    public static void encode(OpenBCMGUIPackage packet, PacketByteBuf buf)
    {
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void write(PacketByteBuf buf) {
        encode(this, buf);
    }

    public OpenBCMGUIPackage()
    {

    }


}
