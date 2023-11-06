package com.animal.lifesimulation.map.staging;

import com.animal.lifesimulation.interfaces.Organism;
import com.animal.lifesimulation.map.SectionCycleData;
import com.animal.lifesimulation.organisms.Plant;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class StagingGrowingPlants extends Staging {

    public StagingGrowingPlants(List<Organism> units, SectionCycleData sectionCycleData, CountDownLatch latch) {
        super(units, sectionCycleData, latch);
    }

    @Override
    public void processing() {
        List<Organism> plants = units.stream().filter(unit -> unit instanceof Plant).toList();
        plants.forEach((Organism unit) -> {
            ((Plant) unit).growing();
        });
    }
}
