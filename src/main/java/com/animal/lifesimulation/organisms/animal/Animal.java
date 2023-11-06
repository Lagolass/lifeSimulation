package com.animal.lifesimulation.organisms.animal;

import com.animal.lifesimulation.interfaces.Organism;
import com.animal.lifesimulation.map.MapConfig;
import com.animal.lifesimulation.organisms.Plant;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;

public abstract class Animal implements Organism {

    protected Gender gender;
    protected boolean wasReproduced = false;
    protected float weight;
    protected float satisfaction;
    protected float wasEaten;
    public float maxMeal;
    public int maxMove;
    protected float hungerRatio = MapConfig.HUNGER_RATIO;
    protected float startingHungerRatio = MapConfig.STARTING_HUNGER_RATIO;

    public void eat(Organism unit) {
        float wasEaten = setSatisfaction(unit.getWeight());
        if(unit instanceof Plant) {
            ((Plant) unit).changeWeight(wasEaten);
        }
    }

    public Organism reproduction(Organism item) {
        if(canReproduce(item)) {
            try {
                Animal animal = (Animal) item.getClass().getDeclaredConstructor().newInstance();

                animal.setWeight(item.getWeight());
                animal.setMaxMeal(item.getMaxMeal());
                animal.setMaxMove(item.getMaxMove());
                animal.setGender(Gender.randomGender());
                animal.setWasReproduced(true);

                return animal;

            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                System.err.println("Error creating an instance of a class: " + item.getClass());
            }
        }

        return null;
    }

    public boolean canReproduce(Organism item) {
        return this.getGender() != item.getGender();
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

    public boolean isWasReproduced() {
        return wasReproduced;
    }

    public void setWasReproduced(boolean wasReproduced) {
        this.wasReproduced = wasReproduced;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getSatisfaction() {
        return satisfaction;
    }

    public float setSatisfaction(float satisfaction) {
        if(satisfaction > (maxMeal - this.satisfaction)) {
            wasEaten = maxMeal - this.satisfaction;
            this.satisfaction = maxMeal;
        } else {
            wasEaten = satisfaction;
            this.satisfaction += satisfaction;
        }

        return wasEaten;
    }

    public float getMaxMeal() {
        return maxMeal;
    }

    public void setMaxMeal(float maxMeal) {
        this.maxMeal = maxMeal;
        if(this.satisfaction == 0)
            this.satisfaction = floatFormat(this.maxMeal - this.maxMeal * this.startingHungerRatio);
    }

    public int getMaxMove() {
        return maxMove;
    }

    public void setMaxMove(int maxMove) {
        this.maxMove = maxMove;
    }

    public boolean isHungry() {
        return satisfaction < floatFormat(maxMeal - (maxMeal * hungerRatio));
    }

    public void changeHunger() {
        float hungerValue = maxMeal * hungerRatio;
        satisfaction = (satisfaction - hungerValue) <= 0.00f ? 0 : floatFormat(satisfaction - hungerValue);
    }

    public static float floatFormat (float value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.000");
        String roundedValue = decimalFormat.format(value);

        return Float.parseFloat(roundedValue.replace(',', '.'));
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{" +
                "gender=" + gender +
                ", wasReproduced=" + wasReproduced +
                ", weight=" + weight +
                ", satisfaction=" + satisfaction +
                ", maxMeal=" + maxMeal +
                ", maxMove=" + maxMove +
                ", hungerRatio=" + hungerRatio +
                ", startingHungerRatio=" + startingHungerRatio +
                '}';
    }
}
