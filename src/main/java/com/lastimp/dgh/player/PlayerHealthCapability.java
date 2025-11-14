package com.lastimp.dgh.player;

import com.lastimp.dgh.core.AnyBodyCondition;
import com.lastimp.dgh.core.AnyPart;
import com.lastimp.dgh.core.BodyComponents;
import com.lastimp.dgh.core.Extremities;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;

public class PlayerHealthCapability implements IPlayerHealthCapability {

    private HashMap<BodyComponents, Extremities> components = new HashMap<>();

    private boolean onBed;

    public PlayerHealthCapability() {
        components.put(BodyComponents.LEFT_ARM, new Extremities());
        components.put(BodyComponents.RIGHT_ARM, new Extremities());
        components.put(BodyComponents.LEFT_LEG, new Extremities());
        components.put(BodyComponents.RIGHT_LEG, new Extremities());
    }

    @Override
    public boolean isOnBed() {
        return onBed;
    }

    @Override
    public AnyPart setComponent(BodyComponents component, AnyBodyCondition condition, float value) {
        components.get(component).setCondition(condition, value);
        return components.get(component);
    }

    @Override
    public AnyPart addComponent(BodyComponents component, AnyBodyCondition condition, float value) {
        components.get(component).addCondition(condition, value);
        return components.get(component);
    }

    @Override
    public AnyPart getComponent(BodyComponents component) {
        return components.get(component);
    }

    @Override
    public float getComponentCondition(BodyComponents component, AnyBodyCondition condition) {
        return components.get(component).getCondition(AnyBodyCondition.BLEED);
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
