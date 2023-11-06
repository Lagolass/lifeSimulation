package com.animal.lifesimulation.map;

import com.animal.lifesimulation.interfaces.Organism;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class MapSimulation {

    protected int cols = MapConfig.DEFAULT_COLS;
    protected int rows = MapConfig.DEFAULT_ROWS;
    protected final HashMap<String, Section> sections;
    protected final LivingStatistic livingStatistic;

    public MapSimulation() {
        sections = new HashMap<>();
        livingStatistic = new LivingStatistic();

        build();
    }
    public MapSimulation(int cols, int rows) {

        this.cols = cols;
        this.rows = rows;

        sections = new HashMap<>();
        livingStatistic = new LivingStatistic();

        build();
    }

    public String start() {

        ExecutorService executor = Executors.newFixedThreadPool(sections.size());
        List<SectionCycleData> sectionCycleDataList = new ArrayList<>();
        try {
            List<Future<SectionCycleData>> future = executor.invokeAll(sections.values());
            executor.shutdown();

            for (Future<SectionCycleData> dataFuture : future) {
                SectionCycleData sectionCycleData = dataFuture.get();
                sectionCycleDataList.add(sectionCycleData);
            }

        } catch (InterruptedException e) {

        }
        catch (ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            if(sectionCycleDataList.size() > 0) {
                processMigration(sectionCycleDataList);
                livingStatistic.calculateNewData(sectionCycleDataList);
            }
        }

        return livingStatistic.toString();
    }

    public void build() {
        if(sections.size() == 0) {
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    sections.put(keyFormat(x, y), new Section(x, y, getCols() - 1, getRows() - 1));
                }
            }
        }
    }

    protected void processMigration(List<SectionCycleData> sectionCycleDataList) {
        synchronized (sections) {
            for (SectionCycleData sectionCycleData : sectionCycleDataList) {
                if (sectionCycleData.getMigrationList().size() > 0) {
                    for (Map.Entry<String, ArrayList<Organism>> entry : sectionCycleData.getMigrationList().entrySet()) {
                        if (entry.getValue().size() > 0 && sections.containsKey(entry.getKey())) {
                            Section section = sections.get(entry.getKey());
                            section.takeMigratedOrganism(entry.getValue());
                        }
                    }
                }
            }
        }
    }

    public LivingStatistic getLivingStatistic() {
        return livingStatistic;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public static String keyFormat(int x, int y) {
        return x + ":" + y;
    }
}
