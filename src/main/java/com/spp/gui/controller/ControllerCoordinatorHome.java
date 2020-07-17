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

import static com.spp.gui.Dialog.displayNotYetSupportedDialog;
import static com.spp.gui.Dialog.displaySomethingWentWrong;

public class ControllerCoordinatorHome {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;

    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    @FXML
    private void goProjectSection() {
        setProjectSectionScene();
    }

    @FXML
    private void goPractitionerSection() {
        setPractitionerSection();
    }

    @FXML
    private void goIndicatorsSection() {
        displayNotYetSupportedDialog();
    }

    @FXML
    private void logOut() {
        closeWindow();
        displayLogin();
    }

    private void setProjectSectionScene() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/views/View_CoordinatorProjectSection.fxml"));
            viewFile = loader.load();
            ControllerCoordinatorProjectSection controllerCoordinatorProjectSection =
                    loader.getController();
            controllerCoordinatorProjectSection.setTopMenuText(topMenu.getText());
            window.setScene(new Scene(viewFile, 600, 400));
        } catch (IOException ioException) {
            Logger.getLogger(ControllerCoordinatorHome.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
        }
    }

    private void setPractitionerSection() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/views/View_PractitionerSection.fxml"));
            viewFile = loader.load();
            ControllerPractitionerSection controllerPractitionerSection = loader.getController();
            controllerPractitionerSection.setTopMenuText(topMenu.getText());
            window.setScene(new Scene(viewFile, 600, 400));
        } catch (IOException ioException) {
            Logger.getLogger(ControllerCoordinatorHome.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
        }
    }

    private void closeWindow() {
        Stage stage1 = (Stage) borderPane.getScene().getWindow();
        stage1.close();
    }

    private void displayLogin() {
        try {
            new ControllerLogin().display();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerPractitionerHome.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
        }
    }
}
