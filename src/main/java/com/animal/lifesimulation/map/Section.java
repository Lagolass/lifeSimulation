package com.animal.lifesimulation.map;

import com.animal.lifesimulation.Organism;
import com.animal.lifesimulation.map.staging.*;

import java.util.*;
import java.util.concurrent.*;

public class Section implements Callable<SectionCycleData> {

    protected ArrayList<Organism> units;

    protected SectionCycleData sectionCycleData;

    private CountDownLatch latchStage;

    protected int coordinateX;
    protected int coordinateY;
    protected SectionCoordinates coordinates;

    private boolean firstRun = true;

    public Section(int coordinateX, int coordinateY, int maxX, int maxY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        calculateDirections(maxX, maxY);

        units = new ArrayList<>();
        sectionCycleData = new SectionCycleData(MapSimulation.keyFormat(coordinateX, coordinateY));
    }

    @Override
    public SectionCycleData call() throws Exception {
        sectionCycleData.reset();

        ExecutorService executor = Executors.newFixedThreadPool(1);
        if (firstRun) {
            latchStage = new CountDownLatch(1);
            Runnable stagingGenerateUnits = new StagingGenerateUnits(units, sectionCycleData, latchStage);
            executor.submit(stagingGenerateUnits);
            firstRun = false;
        } else {
            latchStage = new CountDownLatch(6);
            Runnable stagingNutritional = new StagingNutritional(units, sectionCycleData, latchStage);
            Runnable stagingReproduce = new StagingReproduce(units, sectionCycleData, latchStage);
            Runnable stagingMoveOn = new StagingMoveOn(units, sectionCycleData, coordinates, latchStage);
            Runnable stagingAllChangeHunger = new StagingAllChangeHunger(units, sectionCycleData, latchStage);
            Runnable stagingGrowingPlants = new StagingGrowingPlants(units, sectionCycleData, latchStage);
            Runnable stagingDetectDiedUnits = new StagingDetectDiedUnits(units, sectionCycleData, latchStage);

            executor.submit(stagingNutritional);
            executor.submit(stagingReproduce);
            executor.submit(stagingMoveOn);
            executor.submit(stagingAllChangeHunger);
            executor.submit(stagingGrowingPlants);
            executor.submit(stagingDetectDiedUnits);
        }

        latchStage.await();
        executor.shutdown();
        while (latchStage.getCount() != 0) {
            Thread.sleep(100);
        }

        return sectionCycleData;
    }

    public SectionCycleData getCycleData() {
        return sectionCycleData;
    }

    private void calculateDirections(int maxX, int maxY) {
        coordinates = new SectionCoordinates();
        int maxMoveRight = maxX - coordinateX;
        int maxMoveLeft = (coordinateX == 0) ? 0 : coordinateX - 1;
        int maxMoveUp = maxY - coordinateY;
        int maxMoveDown = (coordinateY == 0) ? 0 : coordinateY - 1;

        coordinates.setX(coordinateX);
        coordinates.setY(coordinateY);
        coordinates.setMaxMoveRight(maxMoveRight);
        coordinates.setMaxMoveLeft(maxMoveLeft);
        coordinates.setMaxMoveUp(maxMoveUp);
        coordinates.setMaxMoveDown(maxMoveDown);
    }

    protected void takeMigratedOrganism(ArrayList<Organism> migrationList) {
        units.addAll(migrationList);
    }
}
