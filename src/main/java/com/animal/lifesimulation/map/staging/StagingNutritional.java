package com.animal.lifesimulation.map.staging;

import com.animal.lifesimulation.exceptions.AnimalNotHungryException;
import com.animal.lifesimulation.interfaces.Herbivore;
import com.animal.lifesimulation.interfaces.Organism;
import com.animal.lifesimulation.interfaces.Predator;
import com.animal.lifesimulation.map.SectionCycleData;
import com.animal.lifesimulation.organisms.Plant;
import com.animal.lifesimulation.organisms.animal.Animal;
import com.animal.lifesimulation.organisms.animal.herbivore.Caterpillar;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class StagingNutritional extends Staging {
    public StagingNutritional(List<Organism> units, SectionCycleData sectionCycleData, CountDownLatch latch) {
        super(units, sectionCycleData, latch);
    }
    @Override
    public void processing() {
        List<Organism> predators = units.stream().filter(unit -> unit instanceof Predator && ((Animal) unit).isHungry()).toList();

        predators.forEach((Organism predator) -> {
            try {
                List<Organism> herbivores = units.stream().filter(unit -> unit instanceof Herbivore).toList();
                herbivores.forEach((Organism herbivore) -> {
                    if (isSuccessfulHunting(predator, herbivore)) {
                        predator.eat(herbivore);
                        dieOrganism(herbivore);
                    } else {
                        ((Animal) predator).changeHunger();
                    }

                    if (!((Animal) predator).isHungry()) {
                        throw new AnimalNotHungryException();
                    }
                });
            } catch (AnimalNotHungryException ignore) {

            }
        });

        List<Organism> herbivores = units.stream().filter(unit -> unit instanceof Herbivore && ((Animal) unit).isHungry()).toList();
        herbivores.forEach((Organism herbivore) -> {
            try {
                List<Organism> food = units.stream().filter(unit -> unit instanceof Plant || unit instanceof Caterpillar).toList();
                food.forEach((Organism eat) -> {
                    if (isSuccessfulHunting(herbivore, eat)) {
                        herbivore.eat(eat);
                        if (eat instanceof Caterpillar)
                            dieOrganism(eat);
                    } else {
                        ((Animal) herbivore).changeHunger();
                    }

                    if (!((Animal) herbivore).isHungry()) {
                        throw new AnimalNotHungryException();
                    }
                });
            } catch (AnimalNotHungryException ignore) {

            }
        });
    }

    protected boolean isSuccessfulHunting(Organism hunter, Organism prey) {
        String hunterName = hunter.getClass().getSimpleName();
        String preyName = prey.getClass().getSimpleName();
        int probability = organismCreator.getForOrganism(hunterName).getEatingProbability(preyName);

        return calculateProbability(probability);
    }

    protected static boolean calculateProbability(int probability) {
        Random random = new Random();

        return random.nextInt(0, 100) <= probability && (probability > 0);
    }
}
