package com.lastimp.dgh.common.item;

import com.lastimp.dgh.common.core.HealingSystem.AbstractPartlyHealItem;
import com.lastimp.dgh.common.core.HealingSystem.HealingAbility;

import static com.lastimp.dgh.common.core.Enums.BodyCondition.*;

public class Bandages extends AbstractPartlyHealItem {
    public Bandages(Properties properties) {
        super(properties);
    }

    @Override
    protected void init() {
        this.healingAbilities.add(HealingAbility.of(BANDAGED, -1.0f, 1.0f, 1.0f));

    }
}
