package com.animal.lifesimulation.organisms;

import com.animal.lifesimulation.Organism;
import com.animal.lifesimulation.map.MapConfig;

public class Plant extends Organism {

    protected float maxWeight;
    protected float coefficientGrowing = MapConfig.COEFFICIENT_GROWING_PLANTS;

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
}
