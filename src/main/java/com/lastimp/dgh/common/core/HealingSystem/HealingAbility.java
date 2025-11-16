package com.lastimp.dgh.common.core.HealingSystem;

import com.lastimp.dgh.common.core.Enums.BodyCondition;

public record HealingAbility(
        BodyCondition condition,
        float healingAmount,
        float threshold,
        float factorAfterThreshold
) {
    public static HealingAbility of(BodyCondition condition, float healingAmount, float threshold) {
        return HealingAbility.of(condition, healingAmount, threshold, 0.05f);
    }

    public static HealingAbility of(BodyCondition condition, float healingAmount, float threshold, float factorAfterThreshold) {
        return new HealingAbility(condition, healingAmount, threshold, factorAfterThreshold);
    }
}
