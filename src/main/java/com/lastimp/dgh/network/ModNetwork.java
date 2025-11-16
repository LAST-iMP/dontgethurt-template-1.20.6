package com.lastimp.dgh.network;

//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraftforge.network.NetworkDirection;
//import net.minecraftforge.network.NetworkEvent;
//import net.minecraftforge.network.NetworkRegistry;
//import net.minecraftforge.network.simple.SimpleChannel;


public class ModNetwork {
    private static final String PROTOCOL_VERSION = "1";
//    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
//            .named(new ResourceLocation(DontGetHurt.MODID, "main"))
//            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
//            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
//            .networkProtocolVersion(() -> PROTOCOL_VERSION)
//            .simpleChannel();
//
//    private static int packetId = 0;
//
//    public static void registerMessages() {
//        CHANNEL.registerMessage(packetId++, MySelectBodyData.class,
//                (msg, buf) -> MySelectBodyData.encode(msg, buf),
//                (buf) -> MySelectBodyData.decode(buf),
//                (msg, ctxSupplier) -> {
//                    NetworkEvent.Context ctx = ctxSupplier.get();
//                    ctx.enqueueWork(() -> {
//                        ServerPlayer player = ctx.getSender();
//                        if (player == null) return;
//                        System.out.println("[dgh] Player " + player.getName().getString() + " selected component: " + msg.getComponentId());
//                        // 示例：将选择存入玩家 capability（如果你有对应字段）
//                        IPlayerHealthCapability cap = player.getCapability(ModCapabilities.PLAYER_HEALTH_HANDLER);
//                        if (cap != null) {
//                            // 这里需要根据 capability 的接口进行实际更新
//                            // cap.setSelectedComponent(msg.getComponentId());
//                        }
//                    });
//                    ctx.setPacketHandled(true);
//                },
//                NetworkDirection.PLAY_TO_SERVER);
//    }
}
