package com.animal.lifesimulation.map.staging;

import com.animal.lifesimulation.Organism;
import com.animal.lifesimulation.map.MapSimulation;
import com.animal.lifesimulation.map.SectionCoordinates;
import com.animal.lifesimulation.map.SectionCycleData;
import com.animal.lifesimulation.organisms.animal.MoveDirections;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class StagingMoveOn extends Staging {
    SectionCoordinates coordinates;
    public StagingMoveOn(List<Organism> units, SectionCycleData sectionCycleData, SectionCoordinates coordinates, CountDownLatch latch) {
        super(units, sectionCycleData, latch);
        this.coordinates = coordinates;
    }

    @Override
    protected void processing() {

        List<Organism> movableUnits = units.stream().filter(unit -> unit.getMaxMove() > 0).toList();
        movableUnits.forEach((Organism unit) -> {
            String newCoordinate = calculateMoveUnit(unit.move(), unit.getMaxMove());
            if(!newCoordinate.equals(MapSimulation.keyFormat(coordinates.getX(), coordinates.getY()))) {
                migrateOrganism(newCoordinate, unit);
            }
        });
    }


    private String calculateMoveUnit(MoveDirections direction, int step) {
        return switch (direction) {
            case UP -> {
                int y = coordinates.getY() + ((step < coordinates.getMaxMoveUp()) ?
                        coordinates.getMaxMoveUp() - step : coordinates.getMaxMoveUp());
                yield MapSimulation.keyFormat(coordinates.getX(), y);
            }
            case LEFT -> {
                int x = coordinates.getX() - ((step < coordinates.getMaxMoveLeft()) ?
                        coordinates.getMaxMoveLeft() - step : coordinates.getMaxMoveLeft());
                yield MapSimulation.keyFormat(x, coordinates.getY());
            }
            case RIGHT -> {
                int x = coordinates.getX() + ((step < coordinates.getMaxMoveRight()) ?
                        coordinates.getMaxMoveRight() - step : coordinates.getMaxMoveRight());
                yield MapSimulation.keyFormat(x, coordinates.getY());
            }
            case DOWN -> {
                int y = coordinates.getY() - ((step < coordinates.getMaxMoveDown()) ?
                        coordinates.getMaxMoveDown() - step : coordinates.getMaxMoveDown());
                yield MapSimulation.keyFormat(coordinates.getX(), y);
            }
        };
    }

    protected void migrateOrganism(String keySection, Organism unit) {
        sectionCycleData.mustMigrate(keySection, unit);
        units.remove(unit);
    }
}
