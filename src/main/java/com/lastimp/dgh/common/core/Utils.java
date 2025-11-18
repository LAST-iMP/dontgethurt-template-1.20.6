package com.lastimp.dgh.common.core;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public abstract class Utils {
    private static final RandomSource randomSource = RandomSource.create(987654321);

    public static float[] getRandom(float ... weight) {
        float sum = 0;
        for (int i = 0; i < weight.length; i++) {
            weight[i] *= Mth.randomBetween(randomSource, 0.0f, 1.0f);
            sum += weight[i];
        }
        for (int i = 0; i < weight.length; i++) {
            weight[i] /= sum;
        }
        return weight;
    }
}
