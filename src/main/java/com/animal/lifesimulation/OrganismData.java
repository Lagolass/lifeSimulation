package com.animal.lifesimulation;

import java.util.HashMap;

public class OrganismData {
    public float weight;
    public int MAX_ORG_IN_SECTION;
    public int MAX_MOVE;
    public float MAX_MEAL;
    public HashMap<String, Integer> eatingProbability;

    public Integer getEatingProbability(String organismName) {
        return eatingProbability.get(organismName);
    }

    public void setEatingProbability(HashMap<String, Integer> eatingProbability) {
        this.eatingProbability = eatingProbability;
    }
    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getMaxOrgInSection() {
        return MAX_ORG_IN_SECTION;
    }

    public void setMaxOrgInSection(int MAX_ORG_IN_SECTION) {
        this.MAX_ORG_IN_SECTION = MAX_ORG_IN_SECTION;
    }

    public int getMaxMove() {
        return MAX_MOVE;
    }

    public void setMaxMove(int MAX_MOVE) {
        this.MAX_MOVE = MAX_MOVE;
    }

    public float getMaxMeal() {
        return MAX_MEAL;
    }

    public void setMaxMeal(float MAX_MEAL) {
        this.MAX_MEAL = MAX_MEAL;
    }
    @Override
    public String toString() {
        return "OrganismData [weight=" + weight + ", MAX_ORG_IN_SECTION=" + MAX_ORG_IN_SECTION
                +", MAX_MOVE=" + MAX_MOVE +", MAX_MEAL=" + MAX_MEAL
                + ", eatingProbability=" + eatingProbability.toString() + "]";
    }
}
