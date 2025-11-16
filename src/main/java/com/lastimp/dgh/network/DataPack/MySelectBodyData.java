package com.lastimp.dgh.network.DataPack;

import com.lastimp.dgh.DontGetHurt;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record MySelectBodyData(int selectBodyPart) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MySelectBodyData> TYPE = new CustomPacketPayload.Type<>(new ResourceLocation(DontGetHurt.MODID, "my_select_body"));

    public static final StreamCodec<ByteBuf, MySelectBodyData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            MySelectBodyData::selectBodyPart,
            MySelectBodyData::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
