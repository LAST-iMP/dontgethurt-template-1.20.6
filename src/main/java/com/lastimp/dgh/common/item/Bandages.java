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
        this.healingAbilities.add(HealingAbility.of(BURN, 0.2f, 1.0f, 1.0f));
        this.healingAbilities.add(HealingAbility.of(BLEED, 0.2f, 1.0f, 1.0f));
        this.healingAbilities.add(HealingAbility.of(OPEN_WOUND, 0.2f, 1.0f, 1.0f));
    }
}
