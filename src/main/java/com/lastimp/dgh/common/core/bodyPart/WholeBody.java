package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.client.player.PlayerHealthCapability;
import com.lastimp.dgh.common.core.Enums.BodyComponents;
import com.lastimp.dgh.common.core.Enums.BodyCondition;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.List;

import static com.lastimp.dgh.common.core.Enums.BodyComponents.*;
import static com.lastimp.dgh.common.core.Enums.BodyComponents.BLOOD;
import static com.lastimp.dgh.common.core.Enums.BodyComponents.HEAD;
import static com.lastimp.dgh.common.core.Enums.BodyComponents.LEFT_ARM;
import static com.lastimp.dgh.common.core.Enums.BodyComponents.LEFT_LEG;
import static com.lastimp.dgh.common.core.Enums.BodyComponents.RIGHT_ARM;
import static com.lastimp.dgh.common.core.Enums.BodyComponents.RIGHT_LEG;
import static com.lastimp.dgh.common.core.Enums.BodyComponents.TORSO;
import static com.lastimp.dgh.common.core.bodyPart.Extremities.*;
import static com.lastimp.dgh.common.core.bodyPart.Extremities.updateLeftLeg;
import static com.lastimp.dgh.common.core.bodyPart.Head.updateHead;
import static com.lastimp.dgh.common.core.bodyPart.PlayerBlood.updateBlood;
import static com.lastimp.dgh.common.core.bodyPart.Torso.updateTorso;

public class WholeBody extends AbstractBody{
    private final HashMap<BodyComponents, AbstractBody> components = new HashMap<>();
    private static List<BodyCondition> WHOLE_BODY_CONDITIONS;

    public static PlayerHealthCapability update(PlayerHealthCapability health, PlayerHealthCapability nextTickHealth) {
        health = updateRightArm(health, nextTickHealth);
        health = updateLeftArm(health, nextTickHealth);
        health = updateRightLeg(health, nextTickHealth);
        health = updateLeftLeg(health, nextTickHealth);
        health = updateTorso(health, nextTickHealth);
        health = updateBlood(health, nextTickHealth);
        health = updateHead(health, nextTickHealth);
        return health;
    }

    public WholeBody() {
        components.put(LEFT_ARM, new Extremities());
        components.put(RIGHT_ARM, new Extremities());
        components.put(LEFT_LEG, new Extremities());
        components.put(RIGHT_LEG, new Extremities());
        components.put(HEAD, new Head());
        components.put(TORSO, new Torso());
        components.put(BLOOD, new PlayerBlood());
    }

    public IAbstractBody getComponent(BodyComponents component) {
        return component == WHOLE_BODY ? this : components.get(component);
    }

    @Override
    public List<BodyCondition> getBodyConditions() {
        if (WHOLE_BODY_CONDITIONS == null) {
            WHOLE_BODY_CONDITIONS = List.of();
        }
        return WHOLE_BODY_CONDITIONS;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag wholeBody = super.serializeNBT(provider);
        CompoundTag tag = new CompoundTag();
        for (BodyComponents comp : components.keySet()) {
            tag.put(comp.name(), components.get(comp).serializeNBT(provider));
        }
        tag.put(WHOLE_BODY.name(), wholeBody);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        if (nbt == null) return;
        components.put(LEFT_ARM, AbstractBody.buildFromNBT(provider, nbt.getCompound(LEFT_ARM.name()), Extremities::new));
        components.put(RIGHT_ARM, AbstractBody.buildFromNBT(provider, nbt.getCompound(RIGHT_ARM.name()), Extremities::new));
        components.put(LEFT_LEG, AbstractBody.buildFromNBT(provider, nbt.getCompound(LEFT_LEG.name()), Extremities::new));
        components.put(RIGHT_LEG, AbstractBody.buildFromNBT(provider, nbt.getCompound(RIGHT_LEG.name()), Extremities::new));
        components.put(HEAD, AbstractBody.buildFromNBT(provider, nbt.getCompound(HEAD.name()), Head::new));
        components.put(TORSO, AbstractBody.buildFromNBT(provider, nbt.getCompound(TORSO.name()), Torso::new));
        components.put(BLOOD, AbstractBody.buildFromNBT(provider, nbt.getCompound(BLOOD.name()), PlayerBlood::new));
        super.deserializeNBT(provider, nbt.getCompound(WHOLE_BODY.name()));
    }
}
