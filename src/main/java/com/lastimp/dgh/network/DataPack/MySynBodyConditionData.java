package com.lastimp.dgh.network.DataPack;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.api.enums.BodyCondition;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record MySynBodyConditionData(float value, String component, String condition) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MySynBodyConditionData> TYPE = new CustomPacketPayload.Type<>(new ResourceLocation(DontGetHurt.MODID, "my_syn_body_condition"));

    public static final StreamCodec<ByteBuf, MySynBodyConditionData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            MySynBodyConditionData::value,
            ByteBufCodecs.STRING_UTF8,
            MySynBodyConditionData::component,
            ByteBufCodecs.STRING_UTF8,
            MySynBodyConditionData::condition,
            MySynBodyConditionData::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static MySynBodyConditionData getInstance(PlayerHealthCapability health, BodyComponents component, BodyCondition condition) {
        return new MySynBodyConditionData(
                health.getComponent(component).getConditionValue(condition),
                component.name(),
                condition.name()
        );
    }

}
