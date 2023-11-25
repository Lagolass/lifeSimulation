package com.animal.lifesimulation.map.staging;

import com.animal.lifesimulation.Organism;
import com.animal.lifesimulation.map.SectionCycleData;
import com.animal.lifesimulation.organisms.animal.Animal;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class StagingDetectDiedUnits extends Staging {
    public StagingDetectDiedUnits(List<Organism> units, SectionCycleData sectionCycleData, CountDownLatch latch) {
        super(units, sectionCycleData, latch);
    }

    @Override
    protected void processing() {
        List<Organism> diedUnits = units.stream().filter(unit -> unit instanceof Animal && ((Animal) unit).getSatisfaction() <= 0.00f).toList();
        diedUnits.forEach(unit -> dieOrganism(unit));
    }
}
