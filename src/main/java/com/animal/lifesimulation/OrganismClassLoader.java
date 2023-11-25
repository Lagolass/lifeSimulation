package com.animal.lifesimulation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class OrganismClassLoader {
    private final ArrayList<String> listPackages = new ArrayList<>(Arrays.asList(
            "com.animal.lifesimulation.organisms",
            "com.animal.lifesimulation.organisms.animal",
            "com.animal.lifesimulation.organisms.animal.herbivore",
            "com.animal.lifesimulation.organisms.animal.predator"
    ));

    private final HashMap<String, String> cachedClassesList = new HashMap<>();

    public Organism organismInstance(String className) {
        if(cachedClassesList.containsKey(className)) {
            return (Organism) createObject(cachedClassesList.get(className));
        } else {
            for (String packageName : listPackages) {
                String packageToClassName = packageName + "." + className;
                Organism instance = (Organism) createObject(packageToClassName);
                if (instance != null) {
                    cachedClassesList.put(className, packageToClassName);
                    return instance;
                }
            }
        }

        return null;
    }

    private Object createObject(String packageToClassName) {
        try {
            Class<?> clazz = Class.forName(packageToClassName);
            return clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + packageToClassName);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            System.err.println("Error creating an instance of a class: " + packageToClassName);
        }
        return null;
    }
}
