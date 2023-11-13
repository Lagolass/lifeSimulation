package com.animal.lifesimulation.interfaces;

import com.animal.lifesimulation.organisms.animal.MoveDirections;

public interface OrganismActions {
    public void eat(OrganismActions item);

    public OrganismActions reproduction(OrganismActions item);

    public MoveDirections move();
}
