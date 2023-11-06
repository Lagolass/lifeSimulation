package com.animal.lifesimulation;

import com.animal.lifesimulation.interfaces.Organism;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import com.animal.lifesimulation.organisms.animal.Gender;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class OrganismCreator {
    protected Map<String, OrganismData> organismsData;
    protected OrganismClassLoader organismClassLoader;

    private String pathToJsonFile = "src/main/resources/com/animal/lifesimulation/organismsData.json";

    public OrganismCreator() {

        this.organismsData = importJsonData(pathToJsonFile);
        this.organismClassLoader = new OrganismClassLoader();
    }

    public ArrayList<String> getOrganismList() {
        return new ArrayList<String>(organismsData.keySet());
    }

    public HashMap<String, Integer> getRegulatoryList() {
        HashMap<String, Integer> list = new HashMap<>();

        organismsData.forEach((k, v) -> list.put(k, v.getMaxOrgInSection()));

        return list;
    }

    public Organism create(String organismName) {
        Organism organism = organismClassLoader.organismInstance(organismName);
        OrganismData organismData = getForOrganism(organismName);

        organism.setWeight(organismData.getWeight());
        organism.setWeight(organismData.getWeight());
        organism.setMaxMeal(organismData.getMaxMeal());
        organism.setMaxMove(organismData.getMaxMove());
        organism.setGender(Gender.randomGender());

        return organism;
    }

    public Map<String, OrganismData> getOrganismsData() {
        return organismsData;
    }
    public OrganismData getForOrganism(String organismName) {
        return getOrganismsData().get(organismName);
    }

    public int getRandomNumber(int max) {
        Random random = new Random();
        return random.nextInt(0, max + 1);
    }

    private static Map<String, OrganismData> importJsonData(String pathToJsonFile) {
        Map<String, OrganismData> map = new HashMap<>();

        try {
            String data = Files.readString(Path.of(pathToJsonFile));

            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, OrganismData>>(){}.getType();

            map = gson.fromJson(data, type);

        } catch (IOException e) {

        }
        return map;
    }
}
