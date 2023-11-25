package com.animal.lifesimulation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 700);
        stage.setTitle("Life Simulation!");
        stage.setScene(scene);
        stage.show();

        stage.setOnHiding( event -> System.exit(0));
    }

    public static void main(String[] args) {
        launch();
    }
}