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

public class ControllerGenerateDocumentation {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;

    public void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    @FXML
    private void generatePartialReport() {
        notYetSupportedDialog();
    }

    @FXML
    private void generateMonthlyReport() {
        notYetSupportedDialog();
    }

    @FXML
    private void generateSelfAppraisal() {
        notYetSupportedDialog();
    }

    @FXML
    private void back() {
        backScene();
    }

    @FXML
    private void logOut() {
        closeWindow();
        displayLogin();
    }

    private void notYetSupportedDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Funcionamiento sin implementar");
        alert.setContentText("Lamentamos las molestias que esto pueda ocasionarle. Seguimos en desarrollo.");
        alert.showAndWait();
    }

    private void backScene() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/views/View_PractitionerHome.fxml"));
            viewFile = loader.load();
            ControllerPractitionerHome controllerPractitionerHome = loader.getController();
            controllerPractitionerHome.setTopMenuText(topMenu.getText());
            window.setScene(new Scene(viewFile, 600, 400));
        } catch (IOException ioException) {
            Logger.getLogger(ControllerGenerateDocumentation.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
        }
    }

    private void somethingWentWrong() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Algo ha salido mal");
        alert.setContentText("Lamentamos las molestias que esto pueda ocasionarle.");
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage1 = (Stage) borderPane.getScene().getWindow();
        stage1.close();
    }

    private void displayLogin() {
        try {
            new ControllerLogin().display();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerGenerateDocumentation.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            somethingWentWrong();
        }
    }
}
