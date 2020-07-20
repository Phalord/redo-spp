package com.spp.model;

import com.spp.gui.controller.ControllerLogin;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static final String PRACTITIONER = "Practitioner";
    public static final String PROFESSOR = "Professor";
    public static final String COORDINATOR = "Coordinator";
    public static final String ADMINISTRATOR = "Administrator";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new ControllerLogin().display();
    }    
}
