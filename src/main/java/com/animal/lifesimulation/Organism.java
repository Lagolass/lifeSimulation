package com.animal.lifesimulation;

import com.animal.lifesimulation.interfaces.OrganismActions;
import com.animal.lifesimulation.organisms.animal.Gender;
import com.animal.lifesimulation.organisms.animal.MoveDirections;

public abstract class Organism implements OrganismActions {

    protected Gender gender;
    protected float weight;
    public float maxMeal;
    public int maxMove;

    public void eat(OrganismActions item) {};

    public Organism reproduction(OrganismActions item) {
        return null;
    }

    public MoveDirections move() {
        return MoveDirections.random();
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getMaxMeal() {
        return maxMeal;
    }

    public void setMaxMeal(float maxMeal) {
        this.maxMeal = maxMeal;
    }

    public int getMaxMove() {
        return maxMove;
    }

    public void setMaxMove(int maxMove) {
        this.maxMove = maxMove;
    }
}
