package com.animal.lifesimulation.map.staging;

import com.animal.lifesimulation.OrganismData;
import com.animal.lifesimulation.interfaces.Organism;
import com.animal.lifesimulation.map.SectionCycleData;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class StagingGenerateUnits extends Staging {

    public StagingGenerateUnits(List<Organism> units, SectionCycleData sectionCycleData, CountDownLatch latch) {
        super(units, sectionCycleData, latch);
    }

    @Override
    public void processing() {
        if(units.size() == 0) {
            ArrayList<String> organismList = organismCreator.getOrganismList();
            for (String organismName : organismList) {
                OrganismData organismData = organismCreator.getForOrganism(organismName);
                int countUnits = organismCreator.getRandomNumber(organismData.getMaxOrgInSection());
                for (int i = 0; i < countUnits; i++) {
                    addOrganism(organismCreator.create(organismName));
                }
            }

            Collections.shuffle(units);
        }
    }
}
