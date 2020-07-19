package com.spp.gui.controller;

import com.spp.model.domain.Activity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spp.gui.Dialog.displayNotYetSupportedDialog;
import static com.spp.gui.Dialog.displaySomethingWentWrong;

public class ControllerGeneratePartialReport {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;
    private List<Activity> openActivities = null;

    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    public final void setOpenActivities(List<Activity> openActivities) {
        this.openActivities = openActivities;
    }

    private List<Activity> getOpenActivities() {
        return openActivities;
    }

    @FXML
    private void back() {
        setBackScene();
    }

    @FXML
    private void generatePartialReport() {
        displayNotYetSupportedDialog();
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
            Logger.getLogger(ControllerPractitionerHome.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
        }
    }

    private void setBackScene() {
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_GenerateDocumentation.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerGeneratePartialReport.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerGenerateDocumentation controllerGenerateDocumentation = loader.getController();
        controllerGenerateDocumentation.setTopMenuText(topMenu.getText());
        Stage window = (Stage) borderPane.getScene().getWindow();
        window.setScene(new Scene(viewFile, 600, 400));
    }
}
