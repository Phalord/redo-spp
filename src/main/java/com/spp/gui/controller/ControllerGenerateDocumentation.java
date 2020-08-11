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
import static com.spp.utils.MailSender.notifyDevelopers;

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
        IActivityDAO iActivityDAO = new ActivityDAO();
        List<Activity> deliveredActivities = iActivityDAO
                .getPractitionerDeliveredActivities(topMenu.getText());
        if (deliveredActivities == null) {
            displayConnectionError();
        } else if (deliveredActivities.isEmpty()) {
            displayNoActivitiesToReport();
        } else {
            displayGenerateMonthlyReport(deliveredActivities);
        }
    }

    @FXML
    private void generateSelfAppraisal() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/View_SelfAppraisal.fxml"));
            viewFile = loader.load();
            ControllerSelfAppraisal controllerSelfAppraisal = loader.getController();
            controllerSelfAppraisal.setPractitionerEnrollment(topMenu.getText());
            window.setScene(new Scene(viewFile));
        } catch (IOException exception) {
            Logger.getLogger(ControllerGenerateDocumentation.class.getName())
                    .log(Level.SEVERE,exception.getMessage(), exception);
        }
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
    
    private void displayGenerateMonthlyReport(List<Activity> deliveredActivities) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_GenerateMonthlyReport.fxml"));
        Parent viewFile;
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerGenerateDocumentation.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerGenerateMonthlyReport controllerGenerateMonthlyReport = loader.getController();
        controllerGenerateMonthlyReport.setTopMenuText(topMenu.getText());
        controllerGenerateMonthlyReport.initialize(deliveredActivities);
        Stage window = (Stage) borderPane.getScene().getWindow();
        window.setScene(new Scene(viewFile));
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
        Stage window = (Stage) borderPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/View_Login.fxml"));
        Parent viewFile;
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerGenerateDocumentation.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            notifyDevelopers(ioException);
            return;
        }
        ControllerLogin controllerLogin = loader.getController();
        controllerLogin.display();
        window.setScene(new Scene(viewFile, 300, 600));
        window.setResizable(false);
        window.show();
    }
}
