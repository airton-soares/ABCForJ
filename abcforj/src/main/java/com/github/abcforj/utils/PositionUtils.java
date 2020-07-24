package com.github.abcforj.utils;

import java.util.concurrent.ThreadLocalRandom;

public class PositionUtils {
    public static double[] moveInRadius(double[] position, double radius) {
        double[] newPosition = new double[position.length];

        for(int i = 0; i < position.length; i++) {
            newPosition[i] = position[i] + ThreadLocalRandom.current().nextDouble(-radius, radius);
        }

        return newPosition;
    }
}
