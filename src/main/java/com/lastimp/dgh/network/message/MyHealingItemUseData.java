/*
* MIT License

Copyright (c) 2023 NeoForged project

This license applies to the template files as supplied by github.com/NeoForged/MDK


Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.lastimp.dgh.network.message;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.network.ServerPayloadHandler;
import com.lastimp.dgh.source.core.healingSystem.HealingHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class MyHealingItemUseData {
    private long id_most;
    private long id_least;
    private int slotNum;
    private String component;

    public MyHealingItemUseData(FriendlyByteBuf buffer) {
        this.id_most = buffer.readLong();
        this.id_least = buffer.readLong();
        this.slotNum = buffer.readInt();
        this.component = buffer.readUtf();
    }

    public MyHealingItemUseData(UUID uuid, int slotNum, String components) {
        this.id_most = uuid.getMostSignificantBits();
        this.id_least = uuid.getLeastSignificantBits();
        this.slotNum = slotNum;
        this.component = components;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeLong(this.id_most);
        buf.writeLong(this.id_least);
        buf.writeInt(this.slotNum);
        buf.writeUtf(this.component);
    }

    public static void handlerServer(final MyHealingItemUseData data, Supplier<NetworkEvent.Context> ctx) {
        ServerPlayer sourcePlayer = ctx.get().getSender();
        UUID targetID = new UUID(data.id_most(), data.id_least());
        ServerPlayer target = (ServerPlayer) sourcePlayer.level().getPlayerByUUID(targetID);
        ItemStack stack = sourcePlayer.getInventory().getItem(data.slotNum());
        BodyComponents component = data.component().equals("NONE") ? null : BodyComponents.valueOf(data.component());

        DontGetHurt.LOGGER.info(sourcePlayer.getScoreboardName() + " using: " + stack.getItem().toString());
        HealingHandler.useItemOn(stack, sourcePlayer, target, component);
    }

    public static MyHealingItemUseData getInstance(UUID targetId, int slotNum, BodyComponents components) {
        if (components == null)
            return new MyHealingItemUseData(targetId, slotNum, "NONE");
        else
            return new MyHealingItemUseData(targetId, slotNum, components.name());
    }

    public String component() {
        return component;
    }

    public long id_least() {
        return id_least;
    }

    public long id_most() {
        return id_most;
    }

    public int slotNum() {
        return slotNum;
    }
}
