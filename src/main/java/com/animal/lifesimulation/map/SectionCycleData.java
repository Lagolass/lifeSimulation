package com.animal.lifesimulation.map;

import com.animal.lifesimulation.Organism;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SectionCycleData {
    protected HashMap<String, Integer> newPopulation;
    protected HashMap<String, Integer> diedPopulation;
    protected HashMap<String, ArrayList<Organism>> migrationList;
    protected String keySection;

    public SectionCycleData(String keySection) {
        this.keySection = keySection;
        newPopulation = new HashMap<>();
        diedPopulation = new HashMap<>();
        migrationList = new HashMap<>();
    }

    public void newUnit(String unitName) {
        if(!Objects.equals(unitName, "Plant")) {
            int value = newPopulation.getOrDefault(unitName, 0);
            newPopulation.put(unitName, value + 1);
        }
    }
    public void diedUnit(String unitName) {
        int value = diedPopulation.getOrDefault(unitName, 0);
        diedPopulation.put(unitName, value + 1);
    }
    public void mustMigrate(String keySection,  Organism unit) {
        migrationList.computeIfAbsent(keySection, k -> new ArrayList<>()).add(unit);
    }

    public void reset() {
        newPopulation.clear();
        diedPopulation.clear();
        migrationList.clear();
    }

    public HashMap<String, Integer> getAddedPopulation() {
        return newPopulation;
    }
    public HashMap<String, Integer> getDiedPopulation() {
        return diedPopulation;
    }

    public HashMap<String, ArrayList<Organism>> getMigrationList() {
        return migrationList;
    }

    public String toString() {
        StringBuilder outData = new StringBuilder();
        if(newPopulation.size() > 0) {
            outData.append("New Population:\n");
            for (Map.Entry<String, Integer> data : newPopulation.entrySet()) {
                outData.append(data.getKey() + " -> " + data.getValue() + "\n");
            }
        }
        if(diedPopulation.size() > 0) {
            outData.append("Died Population:\n");
            for (Map.Entry<String, Integer> data : diedPopulation.entrySet()) {
                outData.append(data.getKey() + " -> " + data.getValue() + "\n");
            }
        }
        int counterMigration = 0;
        if(migrationList.size() > 0) {
            for (Map.Entry<String, ArrayList<Organism>> data : migrationList.entrySet()) {
                counterMigration+= data.getValue().size();
            }
            outData.append("Migrated Population: " + counterMigration + "\n");
        }

        return "keySection - " + keySection + "\n" + (outData.length() > 0 ? outData : "Nothing new");
    }
}
