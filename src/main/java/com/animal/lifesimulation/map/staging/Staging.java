package com.animal.lifesimulation.map.staging;

import com.animal.lifesimulation.Organism;
import com.animal.lifesimulation.OrganismCreator;
import com.animal.lifesimulation.map.SectionCycleData;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public abstract class Staging implements Runnable {

    protected CountDownLatch latch;
    protected List<Organism> units;
    protected SectionCycleData sectionCycleData;
    protected OrganismCreator organismCreator;

    protected final Object lock = new Object();

    public Staging(List<Organism> units, SectionCycleData sectionCycleData, CountDownLatch latch) {
        this.units = units;
        this.sectionCycleData = sectionCycleData;
        this.latch = latch;

        organismCreator = new OrganismCreator();
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                processing();
            }
        } finally {
            latch.countDown();
        }
    }

    abstract protected void processing();

    protected void addOrganism(Organism unit) {
        units.add(unit);

        sectionCycleData.newUnit(unit.getClass().getSimpleName());

    }

    protected void dieOrganism(Organism unit) {
        units.remove(unit);

        sectionCycleData.diedUnit(unit.getClass().getSimpleName());
    }
}
