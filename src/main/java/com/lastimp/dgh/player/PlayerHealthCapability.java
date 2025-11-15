package com.lastimp.dgh.player;

import com.lastimp.dgh.core.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;

public class PlayerHealthCapability implements IPlayerHealthCapability {

    private final HashMap<BodyComponents, AbstractBody> components = new HashMap<>();

    private boolean onBed;

    public PlayerHealthCapability() {
        components.put(BodyComponents.LEFT_ARM, new Extremities());
        components.put(BodyComponents.RIGHT_ARM, new Extremities());
        components.put(BodyComponents.LEFT_LEG, new Extremities());
        components.put(BodyComponents.RIGHT_LEG, new Extremities());
        components.put(BodyComponents.HEAD, new Head());
        components.put(BodyComponents.TORSO, new Torso());
        components.put(BodyComponents.BLOOD, new PlayerBlood());
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
            tag.put(comp.name().toLowerCase(), components.get(comp).serializeNBT(provider));
        }
        tag.putBoolean("onBed", onBed);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        if (nbt == null) return;
        for (BodyComponents comp : BodyComponents.values()) {
            if (nbt.contains(comp.name().toLowerCase())) {
                Extremities extremity = new Extremities();
                extremity.deserializeNBT(provider, nbt.getCompound(comp.name().toLowerCase()));
                components.put(comp, extremity);
            } else {
                components.put(comp, new Extremities());
            }
        }
        onBed = nbt.getBoolean("onBed");
    }
}
