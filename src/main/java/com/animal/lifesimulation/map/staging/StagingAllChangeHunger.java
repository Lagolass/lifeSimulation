package com.animal.lifesimulation.map.staging;

import com.animal.lifesimulation.Organism;
import com.animal.lifesimulation.map.SectionCycleData;
import com.animal.lifesimulation.organisms.animal.Animal;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class StagingAllChangeHunger extends Staging {
    public StagingAllChangeHunger(List<Organism> units, SectionCycleData sectionCycleData, CountDownLatch latch) {
        super(units, sectionCycleData, latch);
    }

    @Override
    protected void processing() {
        for(Organism unit : units) {
            if(unit instanceof Animal) {
                ((Animal) unit).changeHunger();
            }
        }
    }
}
