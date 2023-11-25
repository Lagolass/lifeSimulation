package com.animal.lifesimulation.map;

import com.animal.lifesimulation.Organism;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LivingStatistic {

    int counterCycles;

    protected HashMap<String, Integer> totalPopulationList;
    protected HashMap<String, Integer> tickNewPopulationList;
    protected HashMap<String, Integer> tickDiedPopulationList;
    protected Integer counterPopulation = 0;
    protected Integer counterMigration = 0;
    protected Integer tickCounterMigration = 0;
    protected AtomicInteger counterDiedClasses;
    protected int maxClassUnitDied = MapConfig.MAX_CLASS_UNIT_DIED;
    protected boolean situationIsBad = false;

    public LivingStatistic() {
        totalPopulationList = new HashMap<>();
        tickNewPopulationList = new HashMap<>();
        tickDiedPopulationList = new HashMap<>();
        counterDiedClasses = new AtomicInteger();
    }

    public void setMaxClassUnitDied(int maxClassUnitDied) {
        this.maxClassUnitDied = maxClassUnitDied;
    }

    public boolean mustStopSimulation() {
        return situationIsBad;
    }

    public void calculateNewData(List<SectionCycleData> sectionCycleDataList) {
        if(situationIsBad)
            return;

        resetTickData();

        for (SectionCycleData sectionCycleData : sectionCycleDataList) {

            calculateTickNew(sectionCycleData.getAddedPopulation());

            calculateTickDied(sectionCycleData.getDiedPopulation());

            calculateTickMigration(sectionCycleData.getMigrationList());

        }

        updateTotalPopulation();

        detectSituation();
    }

    private void resetTickData() {
        tickNewPopulationList.clear();
        tickDiedPopulationList.clear();
        tickCounterMigration = 0;
    }

    private void calculateTickNew(HashMap<String, Integer> addedPopulation) {
        if (addedPopulation.size() > 0) {
            for (Map.Entry<String, Integer> v : addedPopulation.entrySet()) {
                int value = tickNewPopulationList.getOrDefault(v.getKey(), 0);
                tickNewPopulationList.put(v.getKey(), value + v.getValue());
            }
        }
    }

    private void calculateTickDied(HashMap<String, Integer> diedPopulation) {
        if (diedPopulation.size() > 0) {
            for (Map.Entry<String, Integer> v : diedPopulation.entrySet()) {
                int value = tickDiedPopulationList.getOrDefault(v.getKey(), 0);
                tickDiedPopulationList.put(v.getKey(), value + v.getValue());
            }
        }
    }

    private void calculateTickMigration(HashMap<String, ArrayList<Organism>> migrationList) {
        if (migrationList.size() > 0) {
            for (Map.Entry<String, ArrayList<Organism>> v : migrationList.entrySet()) {
                counterMigration += v.getValue().size();
                tickCounterMigration += v.getValue().size();
            }
        }
    }

    private void updateTotalPopulation() {
        if(tickNewPopulationList.size() > 0) {
            for (Map.Entry<String, Integer> v : tickNewPopulationList.entrySet()) {
                int value = totalPopulationList.getOrDefault(v.getKey(), 0);
                totalPopulationList.put(v.getKey(), value + v.getValue());
            }
        }
        if(tickDiedPopulationList.size() > 0) {
            for (Map.Entry<String, Integer> v : tickDiedPopulationList.entrySet()) {
                int value = totalPopulationList.getOrDefault(v.getKey(), 0);
                totalPopulationList.put(v.getKey(), value - v.getValue());
            }
        }
    }

    private void detectSituation() {
        counterDiedClasses.setRelease(0);
        if(totalPopulationList.size() > 0) {
            for(Map.Entry <String, Integer> data : totalPopulationList.entrySet()) {
                if(data.getValue() == 0) {
                    counterDiedClasses.getAndIncrement();
                }

                if(counterDiedClasses.get() >= maxClassUnitDied || counterDiedClasses.get() == totalPopulationList.size()) {
                    situationIsBad = true;
                }
            }
        }
    }

    public String toString() {
        StringBuilder outData = new StringBuilder();
        counterPopulation = 0;
        counterCycles++;
        if(totalPopulationList.size() > 0) {
            outData.append("========== Living statistic (cycle : " + counterCycles + ") ============\n\n");
            for(Map.Entry <String, Integer> data : totalPopulationList.entrySet()) {

                int tickNew = tickNewPopulationList.getOrDefault(data.getKey(), 0);
                int tickDied = tickDiedPopulationList.getOrDefault(data.getKey(), 0);

                int tickValue = tickNew - tickDied;
                outData.append(data.getKey() + " -> " + data.getValue() + " (" + tickValue + ")" + "\n");

                counterPopulation+= data.getValue();
            }
        }

        outData.append("Total Population: " + counterPopulation + "\n");
        outData.append("Value All Migration: " + counterMigration + " (" + tickCounterMigration + ")" + "\n");
        outData.append("Died classes of animal: " + counterDiedClasses.toString() + "\n");

        outData.append("========================================\n");

        return outData.toString();
    }
}
