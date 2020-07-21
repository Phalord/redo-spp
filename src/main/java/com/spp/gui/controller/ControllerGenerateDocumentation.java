package com.spp.gui.controller;

import com.spp.model.dataaccess.dao.ActivityDAO;
import com.spp.model.dataaccess.idao.IActivityDAO;
import com.spp.model.domain.Activity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spp.gui.Dialog.displayConnectionError;
import static com.spp.gui.Dialog.displayNoActivitiesToReport;
import static com.spp.gui.Dialog.displaySomethingWentWrong;

public class ControllerGenerateDocumentation {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;

    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    @FXML
    private void generatePartialReport() {
        IActivityDAO iActivityDAO = new ActivityDAO();
        List<Activity> openActivities = iActivityDAO.getOpenPractitionerActivities(
                topMenu.getText(), new Timestamp(System.currentTimeMillis()));
        if (openActivities == null) {
            displayConnectionError();
        } else if (openActivities.isEmpty()) {
            displayNoActivitiesToReport();
        } else {
            displayGeneratePartialReport(openActivities);
        }
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

    //TODO: Una vez implementado estos 3 casos de uso, se borra este método. POR FA NO LO DEJEN!!!!!
    private void notYetSupportedDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Funcionamiento sin implementar");
        alert.setContentText("Lamentamos las molestias que esto pueda ocasionarle. Seguimos en desarrollo.");
        alert.showAndWait();
    }

    private void displayGeneratePartialReport(List<Activity> openActivities) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_GeneratePartialReport.fxml"));
        Parent viewFile;
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerGenerateDocumentation.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerGeneratePartialReport controllerGeneratePartialReport = loader.getController();
        controllerGeneratePartialReport.setTopMenuText(topMenu.getText());
        controllerGeneratePartialReport.initializeScene(openActivities);
        Stage window = (Stage) borderPane.getScene().getWindow();
        window.setScene(new Scene(viewFile, 650, 400));
    }

    private void backScene() {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_PractitionerHome.fxml"));
        Parent viewFile;
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerGenerateDocumentation.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerPractitionerHome controllerPractitionerHome = loader.getController();
        controllerPractitionerHome.setTopMenuText(topMenu.getText());
        Stage window = (Stage) borderPane.getScene().getWindow();
        window.setScene(new Scene(viewFile, 600, 400));
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
            displaySomethingWentWrong();
        }
    }
}
