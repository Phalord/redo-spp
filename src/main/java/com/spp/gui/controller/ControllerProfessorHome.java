package com.spp.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerProfessorHome {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;

    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    @FXML
    private void goActivitySection() {
        setActivitySectionScene();
    }

    @FXML
    private void gradeEvidence() {

    }

    private void setActivitySectionScene() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_ActivitySection.fxml"));
        try {
            viewFile = loader.load();
            ControllerActivitySection controllerActivitySection = loader.getController();
            controllerActivitySection.setTopMenuText(topMenu.getText());
            window.setScene(new Scene(viewFile, 600, 400));
        } catch (IOException ioException) {
            Logger.getLogger(ControllerProfessorHome.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            somethingWentWrong();
        }
    }

    @FXML
    private void logOut() {
        closeWindow();
        displayLogin();
    }

    private void closeWindow() {
        Stage stage1 = (Stage) borderPane.getScene().getWindow();
        stage1.close();
    }

    private void displayLogin() {
        try {
            new ControllerLogin().display();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerProfessorHome.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            somethingWentWrong();
        }
    }

    private void somethingWentWrong() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Algo ha salido mal");
        alert.setContentText("Lamentamos las molestias que esto pueda ocasionarle.");
        alert.showAndWait();
    }
}
