package com.lastimp.dgh.common.Register;

import com.lastimp.dgh.DontGetHurt;
import com.lastimp.dgh.client.player.IPlayerHealthCapability;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.EntityCapability;

public class ModCapabilities {
    public static final EntityCapability<IPlayerHealthCapability, Void> PLAYER_HEALTH_HANDLER =
            EntityCapability.createVoid(new ResourceLocation(DontGetHurt.MODID, "player_health_handler"), IPlayerHealthCapability.class);
}
