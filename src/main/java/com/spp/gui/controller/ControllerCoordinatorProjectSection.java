package com.spp.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerCoordinatorProjectSection {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;

    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    @FXML
    private void registerProject() {

    }

    @FXML
    private void updateProject() {

    }

    @FXML
    private void goProjectList() {

    }

    @FXML
    private void deleteProject() {

    }

    @FXML
    private void back() {
        backScene();
    }

    private void backScene() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/views/View_CoordinatorHome.fxml"));
            viewFile = loader.load();
            ControllerCoordinatorHome controllerCoordinatorHome = loader.getController();
            controllerCoordinatorHome.setTopMenuText(topMenu.getText());
            window.setScene(new Scene(viewFile, 600, 400));
        } catch (IOException ioException) {
            Logger.getLogger(ControllerCoordinatorProjectSection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
        }
    }
}
