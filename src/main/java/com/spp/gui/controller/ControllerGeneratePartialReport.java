package com.spp.gui.controller;

import com.spp.model.dataaccess.dao.ActivityDAO;
import com.spp.model.dataaccess.dao.PartialReportDAO;
import com.spp.model.dataaccess.idao.CRUD;
import com.spp.model.dataaccess.idao.IActivityDAO;
import com.spp.model.domain.Activity;
import com.spp.model.domain.PartialReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spp.gui.Dialog.displayConfirmationDialog;
import static com.spp.gui.Dialog.displayConnectionError;
import static com.spp.gui.Dialog.displayEmptyFields;
import static com.spp.gui.Dialog.displayNoActivitiesAdded;
import static com.spp.gui.Dialog.displayNoActivitiesToReport;
import static com.spp.gui.Dialog.displaySomethingWentWrong;
import static com.spp.gui.Dialog.displaySuccessDialog;
import static com.spp.utils.MailSender.notifyDevelopers;

public class ControllerGeneratePartialReport {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;
    @FXML private Spinner<Integer> hoursCompletionSpinner;
    @FXML private Spinner<Integer> reportNumberSpinner;
    @FXML private ComboBox<String> periodComboBox;
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
        if (openActivities.isEmpty()) {
            displayNoActivitiesToReport();
        } else {
            Activity activity = displaySetActivityInfo();
            if (activity != null) {
                activitiesToReportTable.getItems().add(activity);
                openActivities.remove(activity);
            }
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
        if (periodComboBox.getValue() != null) {
            if (activitiesToReportTable.getItems().isEmpty()) {
                displayNoActivitiesAdded();
            } else {
                if (displayConfirmationDialog("¿Desea generar el reporte parcial de actividades?")) {
                    PartialReport partialReport = new PartialReport();
                    partialReport.setReportType("Parcial");
                    partialReport.setPartialPeriod(periodComboBox.getValue());
                    final int hoursCompletion = hoursCompletionSpinner.getValue();
                    partialReport.setProjectHoursCovered((short) hoursCompletion);
                    final int reportNumber = reportNumberSpinner.getValue();
                    partialReport.setReportNumber((byte) reportNumber);
                    prepareActivities();
                    partialReport.setActivities(activitiesToReportTable.getItems());
                    partialReport.generateFolio(new Timestamp(System.currentTimeMillis()), topMenu.getText());
                    if (savePartialReport(partialReport)) {
                        displaySuccessDialog("El Reporte se ha guardado exitosamente.");
                        goBack();
                    } else {
                        displayConnectionError();
                    }
                }
            }
        } else {
            displayEmptyFields();
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
            notifyDevelopers(ioException);
            displaySomethingWentWrong();
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
            notifyDevelopers(ioException);
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
            notifyDevelopers(ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerGenerateDocumentation controllerGenerateDocumentation = loader.getController();
        controllerGenerateDocumentation.setTopMenuText(topMenu.getText());
        Stage window = (Stage) borderPane.getScene().getWindow();
        window.setScene(new Scene(viewFile, 600, 400));
    }



    private boolean savePartialReport(PartialReport partialReport) {
        CRUD<PartialReport> partialReportCRUD = new PartialReportDAO();
        boolean result = false;
        if (partialReportCRUD.addElement(partialReport)) {
            IActivityDAO activityDAO = new ActivityDAO();
            for (Activity activity: partialReport.getActivities()) {
                result = activityDAO.reportActivity(activity, partialReport);
            }
        }
        return result;
    }

    private void prepareActivities() {
        for (Activity activity: activitiesToReportTable.getItems()) {
            activity.setDeliveredAt(new Timestamp(System.currentTimeMillis()));
        }
    }
}
