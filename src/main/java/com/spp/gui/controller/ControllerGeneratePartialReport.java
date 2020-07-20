package com.spp.gui.controller;

import com.spp.model.domain.Activity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spp.gui.Dialog.displayConfirmationDialog;
import static com.spp.gui.Dialog.displayNoActivitiesAdded;
import static com.spp.gui.Dialog.displayNoActivitiesToReport;
import static com.spp.gui.Dialog.displayNotYetSupportedDialog;
import static com.spp.gui.Dialog.displaySomethingWentWrong;

public class ControllerGeneratePartialReport {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;
    @FXML private TableView<Activity> activitiesToReportTable;
    @FXML private TableColumn<Activity, String> titleColumn;
    @FXML private TableColumn<Activity, String> descriptionColumn;
    private List<Activity> openActivities = new ArrayList<>();

    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }

    public final void initializeScene(List<Activity> openActivities) {
        initializeActivitiesToReportTable();
        setOpenActivities(openActivities);
    }

    public final void setOpenActivities(List<Activity> openActivities) {
        this.openActivities = openActivities;
    }

    public final void initializeActivitiesToReportTable() {
        ObservableList<Activity> openActivitiesOL =
                FXCollections.observableArrayList(getOpenActivities());
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        activitiesToReportTable.setItems(openActivitiesOL);
    }

    @FXML
    private void addActivity() {
        Activity activity = displaySetActivityInfo();
        if (activity != null) {
            activitiesToReportTable.getItems().add(activity);
        }

    }

    private List<Activity> getOpenActivities() {
        return openActivities;
    }

    @FXML
    private void goBack() {
        setBackScene();
    }

    @FXML
    private void generatePartialReport() {
        if (activitiesToReportTable.getItems().isEmpty()) {
            displayNoActivitiesAdded();
        } else {
            if (displayConfirmationDialog("¿Desea generar el reporte parcial de actividades?")) {
                displayNotYetSupportedDialog();
            }
        }
    }

    @FXML
    private void logOut() {
        closeWindow();
        displayLogin();
    }

    private Activity displaySetActivityInfo() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/View_SetActivityInfo.fxml"));
        Parent viewFile;
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerGeneratePartialReport.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            return null;
        }
        ControllerSetActivityInfo controllerSetActivityInfo = loader.getController();
        controllerSetActivityInfo.setScene(new Scene(viewFile, 450, 250));
        controllerSetActivityInfo.initOwner(borderPane.getScene().getWindow());
        controllerSetActivityInfo.initModality(Modality.APPLICATION_MODAL);
        controllerSetActivityInfo.setResizable(false);
        return controllerSetActivityInfo.showAndReturn(openActivities);
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
