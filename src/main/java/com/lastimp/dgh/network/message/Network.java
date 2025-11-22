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
import com.lastimp.dgh.network.ClientPayloadHandler;
import com.lastimp.dgh.network.ServerPayloadHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class Network {
    public static SimpleChannel SERVER_INSTANCE;
    public static SimpleChannel CLIENT_INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        SERVER_INSTANCE = NetworkRegistry.newSimpleChannel(
                ResourceLocation.fromNamespaceAndPath(DontGetHurt.MODID, "server_networking"),
                () -> "1.0",
                (s) -> true,
                (s) -> true
        );

        CLIENT_INSTANCE = NetworkRegistry.newSimpleChannel(
                ResourceLocation.fromNamespaceAndPath(DontGetHurt.MODID, "client_networking"),
                () -> "2.0",
                (s) -> true,
                (s) -> true
        );

        CLIENT_INSTANCE.registerMessage(
                nextID(),
                MyReadAllConditionData.class,
                MyReadAllConditionData::toBytes,
                MyReadAllConditionData::new,
                ClientPayloadHandler::handleReadAllConditionData,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
        SERVER_INSTANCE.registerMessage(
                nextID(),
                MyReadAllConditionData.class,
                MyReadAllConditionData::toBytes,
                MyReadAllConditionData::new,
                ServerPayloadHandler::handleReadAllConditionData,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
        SERVER_INSTANCE.registerMessage(
                nextID(),
                MyKeyPressedData.class,
                MyKeyPressedData::toBytes,
                MyKeyPressedData::new,
                ServerPayloadHandler::handleClientPress,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
        SERVER_INSTANCE.registerMessage(
                nextID(),
                MyHealingItemUseData.class,
                MyHealingItemUseData::toBytes,
                MyHealingItemUseData::new,
                ServerPayloadHandler::handleHealingItemUsageData,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
    }
}
