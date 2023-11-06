module com.animal.lifesimulation {
    requires javafx.controls;
    requires javafx.fxml;
            
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens com.animal.lifesimulation to javafx.fxml;
    exports com.animal.lifesimulation;
    exports com.animal.lifesimulation.organisms;
    opens com.animal.lifesimulation.organisms to javafx.fxml;
}