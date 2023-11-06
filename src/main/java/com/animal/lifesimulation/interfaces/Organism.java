package com.animal.lifesimulation.interfaces;

import com.animal.lifesimulation.organisms.animal.Gender;
import com.animal.lifesimulation.organisms.animal.MoveDirections;

public interface Organism {
    public void eat(Organism item);

    public Organism reproduction(Organism item);

    public MoveDirections move();

    public Gender getGender();

    public void setGender(Gender gender);

    public float getWeight();

    public void setWeight(float weight);

    public float getMaxMeal();

    public void setMaxMeal(float maxMeal);

    public void setMaxMove(int maxMove);

    public int getMaxMove();
}
