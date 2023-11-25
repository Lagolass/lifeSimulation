package com.animal.lifesimulation.map;

import java.util.concurrent.TimeUnit;

public class MapConfig {
    public static final int DEFAULT_ROWS = 2;
    public static final int DEFAULT_COLS = 2;
    public static final int INTERVAL_CYCLE = 2;
    public static final TimeUnit INTERVAL_TIME_UNIT = TimeUnit.SECONDS;
    public static final int MAX_CLASS_UNIT_DIED = 4;
    public static final float HUNGER_RATIO = 0.3f;
    public static final float STARTING_HUNGER_RATIO = 0.5f;
    public static final float COEFFICIENT_GROWING_PLANTS = 0.2f;
}
