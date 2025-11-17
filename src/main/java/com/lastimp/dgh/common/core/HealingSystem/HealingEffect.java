package com.lastimp.dgh.common.core.HealingSystem;

import com.lastimp.dgh.common.core.Enums.BodyCondition;

public record HealingEffect(
        BodyCondition condition,
        float healingAmount,
        float threshold,
        float factorAfterThreshold
) {
    public static HealingEffect of(BodyCondition condition, float healingAmount, float threshold) {
        return HealingEffect.of(condition, healingAmount, threshold, 0.05f);
    }

    public static HealingEffect of(BodyCondition condition, float healingAmount, float threshold, float factorAfterThreshold) {
        return new HealingEffect(condition, healingAmount, threshold, factorAfterThreshold);
    }
}
