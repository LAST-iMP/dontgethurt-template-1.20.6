package com.lastimp.dgh.common.Register;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.client.player.IPlayerHealthCapability;
import com.lastimp.dgh.client.player.PlayerHealthCapability;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ModCapabilities {
    public static final EntityCapability<IPlayerHealthCapability, Void> PLAYER_HEALTH_HANDLER =
            EntityCapability.createVoid(new ResourceLocation(DontGetHurt.MODID, "player_health_handler"), IPlayerHealthCapability.class);

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, DontGetHurt.MODID);

    public static final Supplier<AttachmentType<PlayerHealthCapability>> PLAYER_HEALTH =
            ATTACHMENT_TYPES.register("player_health", () -> AttachmentType.serializable(PlayerHealthCapability::new).build());

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
