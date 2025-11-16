package com.lastimp.dgh.common.item;

import com.lastimp.dgh.common.core.HealingSystem.AbstractDirectHealItems;
import com.lastimp.dgh.common.core.HealingSystem.HealingAbility;

import static com.lastimp.dgh.common.core.Enums.BodyCondition.*;

public class BloodPacks extends AbstractDirectHealItems {
    public BloodPacks(Properties properties) {
        super(properties);
    }

    @Override
    protected void init() {
        this.healingAbilities.add(HealingAbility.of(BLOOD_VOLUME, 0.25f, 1.0f, 1.0f));
    }
}
