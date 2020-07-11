package com.spp.gui.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spp.model.Main.*;

public class HomeController implements IWindowGUI {
    private Stage window;

    public HomeController(String accountType, String username) {
        switch (accountType) {
            case PRACTITIONER:
                buildStage(new Stage(), accountType);
                buildPractitionerHomeScene(username);
                break;
            case COORDINATOR:
                buildStage(new Stage(), accountType);
                buildCoordinatorHomeScene(username);
                break;
            case PROFESSOR:
                break;
            default:
                somethingWentWrong();
        }
    }

    @Override
    public void display() {
        window.show();
    }

    @Override
    public void buildStage(Stage stage, String title) {
        window = stage;
        window.setTitle(title);
    }

    private void somethingWentWrong() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Algo ha salido mal");
        alert.setContentText("Lamentamos las molestias que esto pueda ocasionarle.");
        alert.showAndWait();
    }

    private void buildPractitionerHomeScene(String username) {
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/views/View_PractitionerHome.fxml"));
            viewFile = loader.load();
            ControllerPractitionerHome controllerPractitionerHome = loader.getController();
            controllerPractitionerHome.setTopMenuText(username);
            window.setScene(new Scene(viewFile, 600, 400));
            window.setResizable(false);
        } catch (IOException ioException) {
            Logger.getLogger(HomeController.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
        }
    }

    private void buildCoordinatorHomeScene(String username) {
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/views/View_CoordinatorHome.fxml"));
            viewFile = loader.load();
            ControllerCoordinatorHome controllerCoordinatorHome = loader.getController();
            controllerCoordinatorHome.setTopMenuText(username);
            window.setScene(new Scene(viewFile, 600, 400));
            window.setResizable(false);
        } catch (IOException ioException) {
            Logger.getLogger(HomeController.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
        }
    }
}
