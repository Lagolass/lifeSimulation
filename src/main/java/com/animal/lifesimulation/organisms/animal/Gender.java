package com.animal.lifesimulation.organisms.animal;

import java.util.Random;

public enum Gender {
    MALE,
    FEMALE;

    private static final Random RNG = new Random();

    public static Gender randomGender()  {
        Gender[] list = values();
        return list[RNG.nextInt(list.length)];
    }
}
