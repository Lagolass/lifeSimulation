package com.animal.lifesimulation;

import com.animal.lifesimulation.map.MapConfig;
import com.animal.lifesimulation.map.MapSimulation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class StartController implements Initializable {
    public Button startButton;
    public Button stopButton;
    public TextArea textOutArea;
    public Spinner spinnerRows;
    public Spinner spinnerCols;
    public Spinner spinnerNumForStop;

    protected ScheduledExecutorService schedulerMap;
    protected Future<?> future;

    int mapCols = MapConfig.DEFAULT_COLS;
    int mapRows = MapConfig.DEFAULT_ROWS;
    int maxClassUnitDied = MapConfig.MAX_CLASS_UNIT_DIED;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spinnerRows.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20)
        );
        spinnerRows.getValueFactory().setValue(mapRows);
        spinnerRows.valueProperty().addListener((obs, oldValue, newValue) -> mapRows = (int) newValue);

        spinnerCols.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20)
        );
        spinnerCols.getValueFactory().setValue(mapCols);
        spinnerCols.valueProperty().addListener((obs, oldValue, newValue) -> mapCols = (int) newValue);

        spinnerNumForStop.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15)
        );
        spinnerNumForStop.getValueFactory().setValue(maxClassUnitDied);
        spinnerNumForStop.valueProperty().addListener((obs, oldValue, newValue) -> maxClassUnitDied = (int) newValue);
    }

    @FXML
    protected void onStartButton() throws InterruptedException {
        MapSimulation map = new MapSimulation(mapCols, mapRows);
        map.getLivingStatistic().setMaxClassUnitDied(maxClassUnitDied);

        stopButton.setDisable(false);
        startButton.setDisable(true);
        spinnerRows.setDisable(true);
        spinnerCols.setDisable(true);
        spinnerNumForStop.setDisable(true);

        schedulerMap = Executors.newScheduledThreadPool(1);

        future = schedulerMap.scheduleAtFixedRate(() -> {
            String statistic = map.start();

            Platform.runLater(() -> {
                textOutArea.setText(statistic);
            });

            if(map.getLivingStatistic().mustStopSimulation()) {
                ActionEvent manualEvent = new ActionEvent();
                onStopButton(manualEvent);
            }

        }, 0, MapConfig.INTERVAL_CYCLE, MapConfig.INTERVAL_TIME_UNIT);

    }

    public void onStopButton(ActionEvent event) {
        future.cancel(true);
        schedulerMap.shutdown();

        stopButton.setDisable(true);
        startButton.setDisable(false);
        spinnerRows.setDisable(false);
        spinnerCols.setDisable(false);
        spinnerNumForStop.setDisable(false);

        Platform.runLater(() -> {
            textOutArea.appendText("\n\nSimulation Stopped!\n");
        });
    }
}