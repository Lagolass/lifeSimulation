package com.animal.lifesimulation.organisms;

import com.animal.lifesimulation.interfaces.Organism;
import com.animal.lifesimulation.map.MapConfig;
import com.animal.lifesimulation.organisms.animal.Gender;
import com.animal.lifesimulation.organisms.animal.MoveDirections;

public class Plant implements Organism {

    protected float weight;
    protected float maxWeight;
    protected float maxMeal;
    protected int maxMove;
    protected float coefficientGrowing = MapConfig.COEFFICIENT_GROWING_PLANTS;
    @Override
    public void eat(Organism item) {

    }
    @Override
    public Organism reproduction(Organism item) {
        return null;
    }

    @Override
    public MoveDirections move() {
        return null;
    }

    @Override
    public Gender getGender() {
        return null;
    }

    @Override
    public void setGender(Gender gender) {

    }

    @Override
    public float getWeight() {
        return weight;
    }

    @Override
    public void setWeight(float weight) {
        this.maxWeight = weight;
        this.weight = weight;
    }

    public void changeWeight(float weight) {
        this.weight-= (this.weight - weight < 0) ? 0 : weight;
    }

    public void growing() {
        float grow = maxWeight * coefficientGrowing;
        weight = Math.min((weight + grow), maxWeight);
    }

    @Override
    public float getMaxMeal() {
        return maxMeal;
    }

    @Override
    public void setMaxMeal(float maxMeal) {

    }

    @Override
    public int getMaxMove() {
        return maxMove;
    }

    @Override
    public void setMaxMove(int maxMove) {

    }
}
