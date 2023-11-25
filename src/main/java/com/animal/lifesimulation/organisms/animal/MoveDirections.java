package com.animal.lifesimulation.organisms.animal;

import java.util.Random;

public enum MoveDirections {
    UP,
    LEFT,
    RIGHT,
    DOWN;

    private static final Random RNG = new Random();

    public static MoveDirections random()  {
        MoveDirections[] list = values();
        return list[RNG.nextInt(list.length)];
    }
}
