package com.lastimp.dgh.common.item;

import com.lastimp.dgh.common.core.HealingSystem.AbstractDirectHealItems;
import com.lastimp.dgh.common.core.HealingSystem.HealingAbility;

import static com.lastimp.dgh.common.core.Enums.BodyCondition.*;

public class Morphine extends AbstractDirectHealItems {
    public Morphine(Properties properties) {
        super(properties);
    }

    @Override
    protected void init() {
        this.healingAbilities.add(HealingAbility.of(INTERNAL_INJURY, 0.2f, 1.0f, 1.0f));
    }
}
