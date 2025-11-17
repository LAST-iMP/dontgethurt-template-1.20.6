package com.lastimp.dgh.client.player;

import com.lastimp.dgh.common.core.Enums.BodyComponents;
import com.lastimp.dgh.common.core.Enums.BodyCondition;
import com.lastimp.dgh.common.core.bodyPart.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;

import static com.lastimp.dgh.common.core.Enums.BodyComponents.*;

public class PlayerHealthCapability implements IPlayerHealthCapability {

    private final HashMap<BodyComponents, AbstractBody> components = new HashMap<>();

    private boolean onBed;

    public PlayerHealthCapability() {
        components.put(LEFT_ARM, new Extremities());
        components.put(RIGHT_ARM, new Extremities());
        components.put(LEFT_LEG, new Extremities());
        components.put(RIGHT_LEG, new Extremities());
        components.put(HEAD, new Head());
        components.put(TORSO, new Torso());
        components.put(BLOOD, new PlayerBlood());
    }

    @Override
    public boolean isOnBed() {
        return onBed;
    }

    @Override
    public IAbstractBody setComponent(BodyComponents component, BodyCondition condition, float value) {
        components.get(component).setCondition(condition, value);
        return components.get(component);
    }

    @Override
    public IAbstractBody addComponent(BodyComponents component, BodyCondition condition, float value) {
        components.get(component).addCondition(condition, value);
        return components.get(component);
    }

    @Override
    public IAbstractBody getComponent(BodyComponents component) {
        return components.get(component);
    }

    @Override
    public float getComponentCondition(BodyComponents component, BodyCondition condition) {
        return components.get(component).getCondition(BodyCondition.BLEED);
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        for (BodyComponents comp : BodyComponents.values()) {
            tag.put(comp.name(), components.get(comp).serializeNBT(provider));
        }
        tag.putBoolean("onBed", onBed);
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

        onBed = nbt.getBoolean("onBed");
    }
}
