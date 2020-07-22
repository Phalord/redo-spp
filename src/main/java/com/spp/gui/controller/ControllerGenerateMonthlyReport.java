package com.spp.gui.controller;

import static com.spp.gui.Dialog.displayCancelConfirmation;
import static com.spp.gui.Dialog.displaySomethingWentWrong;
import static com.spp.gui.Dialog.displaySuccessDialog;
import com.spp.model.domain.Activity;
import com.spp.model.domain.MonthlyReport;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ControllerGenerateMonthlyReport {
    
    @FXML private Menu topMenu;
    @FXML private BorderPane monthlyReportBorderPane;
    @FXML private TableView<Wrapper> activitiesToReportTable;
    @FXML private TableColumn<Wrapper, String> titleColumn;
    @FXML private TableColumn<Wrapper, String> descriptionColumn;
    @FXML private ComboBox<Activity> deliveredActivitiesComboBox;
    @FXML private TextArea descriptionTextArea;
    private List<Activity> deliveredActivities = new ArrayList<>();
    
    public class Wrapper {
        private Activity activity;
        private String description;
        
        public final void setActivity(Activity activity) {
            this.activity = activity;
        }
        
        public final Activity getActivity() {
            return activity;
        }
        
        public final void setDescription(String description) {
            this.description = description;
        }
        
        public final String getDescription() {
            return description;
        }
    }

    
    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }
    
    public void initialize(List<Activity> deliveredActivities) {
        ObservableList<Activity> deliveredActivitiesOL =
                FXCollections.observableArrayList(deliveredActivities);
        deliveredActivitiesComboBox.getItems().addAll(deliveredActivitiesOL);
        initializeActivitiesToReportTable();
        setDeliveredActivities(deliveredActivities);
    }
    
    public final void setDeliveredActivities(List<Activity> deliveredActivities) {
        this.deliveredActivities = deliveredActivities;
    }
    
    private List<Activity> getDeliveredActivities() {
        return deliveredActivities;
    }
    
    public final void initializeActivitiesToReportTable() {
        ObservableList<Wrapper> openActivitiesOL =
                FXCollections.observableArrayList(new ArrayList());
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        activitiesToReportTable.setItems(openActivitiesOL);
    }
    
    @FXML
    private void addActivity() {
        Activity activity = deliveredActivitiesComboBox.getValue();
        if (!areFieldsEmpty()) {
            Wrapper wrapper = new Wrapper();
            wrapper.setActivity(activity);
            wrapper.setDescription(descriptionTextArea.getText());
            activitiesToReportTable.getItems().add(wrapper);
            deliveredActivitiesComboBox.getItems().remove(activity);           
        } else {
            displayNotActivitiesSelectedDialog();
        }
    }
    
    @FXML
    private void generateMonthlyReport() {
        if (!activitiesToReportTable.getItems().isEmpty()) {
            List<Activity> activities = retrieveActivitiesFromTable();
            List<String> descriptions = retrieveDescriptionsFromTable();
            MonthlyReport monthlyReport = new MonthlyReport();
            monthlyReport.setActivities(activities);
            monthlyReport.setActivitiesDescription(descriptions);
            monthlyReport.setReportType("Mensual");
            monthlyReport.generateFolio(new Timestamp(System.currentTimeMillis()), topMenu.getText());
            /*
            if (addMonthlyReport(monthlyReport)) {
                for (activities.size) {
                    addDescription(monthlyReport);
                }
            } 
            */
            
        }
    }
    
    private List<Activity> retrieveActivitiesFromTable() {
        List<Activity> activities = new ArrayList<>();
        for (Wrapper wrapper: activitiesToReportTable.getItems()) {
            activities.add(wrapper.getActivity());
        }
        return activities;
    }
    
    private List<String> retrieveDescriptionsFromTable() {
        List<String> descriptions = new ArrayList<>();
        for (Wrapper wrapper: activitiesToReportTable.getItems()) {
            descriptions.add(wrapper.getDescription());
        }
        return descriptions;
    }
    
    @FXML
    private void cancel() {
        if (displayCancelConfirmation()) {
            setBackScene();        
        }
    }
    
    @FXML
    private void logOut() {
        closeWindow();
        displayLogin();
    }
    
    private void closeWindow() {
        Stage stage1 = (Stage) monthlyReportBorderPane.getScene().getWindow();
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
        Stage window = (Stage) monthlyReportBorderPane.getScene().getWindow();
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_GenerateDocumentation.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerGenerateMonthlyReport.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerGenerateDocumentation controllerGenerateDocumentation = loader.getController();
        controllerGenerateDocumentation.setTopMenuText(topMenu.getText());
        window.setScene(new Scene(viewFile, 600, 400));
    }

    private boolean areFieldsEmpty() {
        return (deliveredActivitiesComboBox.getValue() == null) ||
                descriptionTextArea.getText().isEmpty();
    }
    
    private void displayNotActivitiesSelectedDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("No se seleccionó ninguna actividad");
        alert.setContentText("Debe seleccionar una actividad para añadirla");
        alert.showAndWait(); 
    }
}
