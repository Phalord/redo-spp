package com.spp.model;

import com.spp.gui.controller.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spp.utils.MailSender.notifyDevelopers;

public class Main extends Application {
    public static final String PRACTITIONER = "Practitioner";
    public static final String PROFESSOR = "Professor";
    public static final String COORDINATOR = "Coordinator";
    public static final String ADMINISTRATOR = "Administrator";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/View_Login.fxml"));
        Parent viewFile;
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            notifyDevelopers(ioException);
            return;
        }
        ControllerLogin controllerLogin = loader.getController();
        controllerLogin.display();
        primaryStage.setScene(new Scene(viewFile, 300, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
