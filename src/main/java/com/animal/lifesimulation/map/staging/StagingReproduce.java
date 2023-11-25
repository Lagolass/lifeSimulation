package com.animal.lifesimulation.map.staging;

import com.animal.lifesimulation.map.SectionCycleData;
import com.animal.lifesimulation.Organism;
import com.animal.lifesimulation.organisms.Plant;
import com.animal.lifesimulation.organisms.animal.Gender;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class StagingReproduce extends Staging {

    public StagingReproduce(List<Organism> units, SectionCycleData sectionCycleData, CountDownLatch latch) {
        super(units, sectionCycleData, latch);
    }

    @Override
    public void processing() {
        Map<String, ArrayList<Organism>> groupedUnits = new HashMap<>();

        for (Organism unit : units) {
            if(!(unit instanceof Plant)) {
                String classUnits = unit.getClass().getSimpleName();
                groupedUnits.computeIfAbsent(classUnits, k -> new ArrayList<>()).add(unit);
            }
        }

        HashMap<String, Integer> regulatoryList = organismCreator.getRegulatoryList();
        for (Map.Entry<String, ArrayList<Organism>> entry : groupedUnits.entrySet()) {
            String classUnits = entry.getKey();
            ArrayList<Organism> newUnits = reproducing(entry.getValue(), regulatoryList.get(classUnits));

            addNewUnits(newUnits);
        }

        Collections.shuffle(units);
    }

    public ArrayList<Organism> reproducing(ArrayList<Organism> organisms, Integer maxUnits) {

        ArrayList<Organism> futureUnits = new ArrayList<>();
        AtomicInteger counterFreePlaces = new AtomicInteger(maxUnits - organisms.size());

        if(counterFreePlaces.get() > 0) {
            List<Organism> maleGenders = organisms.stream().filter(organism -> organism.getGender() == Gender.MALE).toList();

            List<Organism> femaleGenders = organisms.stream().filter(organism -> organism.getGender() == Gender.FEMALE).toList();
            if(maleGenders.size() > 0 && femaleGenders.size() > 0) {
                femaleGenders.forEach(organism -> {
                    if(counterFreePlaces.get() > 0) {
                        Organism maleGender = maleGenders.stream().findFirst().get();
                        Organism newUnit = organism.reproduction(maleGender);
                        if (newUnit != null) {
                            futureUnits.add(newUnit);
                            counterFreePlaces.getAndDecrement();
                        }
                    }
                });

            }
        }

        return futureUnits;
    }

    protected void addNewUnits(ArrayList<Organism> list) {
        if (list.size() > 0) {
            for (Organism organism : list) {
                addOrganism(organism);
            }
        }
    }
}
