package com.spp.gui.controller;

import static com.spp.gui.Dialog.displayEmptyFields;
import static com.spp.gui.Dialog.displayRecordConfirmation;
import static com.spp.gui.Dialog.displayRecordSuccessDialog;
import static com.spp.gui.Dialog.displaySomethingWentWrong;
import com.spp.model.dataaccess.dao.ActivityDAO;
import com.spp.model.dataaccess.idao.IActivityDAO;
import com.spp.model.domain.Activity;
import com.spp.model.domain.Practitioner;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControllerAddActivity{

    @FXML private TextField textFieldTitle;
    @FXML private TextField textFieldEstimatedHours;
    @FXML private TextArea textAreaDescription;
    @FXML private DatePicker datePickerDueDate;
    @FXML private TableView<Activity> tableViewActivity;
    @FXML private TableColumn<Activity, Integer> columnID;
    @FXML private TableColumn<Activity, String> columnTitle;
    @FXML private TableColumn<Activity, Timestamp> columnDueDate;
    @FXML private TableColumn<Activity, Integer> columnEstimatedHours;
    @FXML private TableColumn<Activity, Practitioner> columnPractitioner;
    @FXML private ComboBox<Practitioner> comboBoxPractitioner;
    private String professorUsername;
    private final IActivityDAO activityDAOFinal;
    
    public ControllerAddActivity(){
        this.activityDAOFinal = new ActivityDAO();
    }
            
    public final void initialize() {
        linkColumns();
        enterCorrectInformation();
    }    

    @FXML
    private void generateActivity() {
        if(!areTextFieldsEmpty()){
            String title = textFieldTitle.getText();
            int estimatedHoursParse = 
                    Integer.parseInt(textFieldEstimatedHours.getText());
            short estimatedHours = (short) estimatedHoursParse;
            String description = textAreaDescription.getText();
            Practitioner practitionerSelected = comboBoxPractitioner.getValue();
            Date auxiliarDueDate = 
                    new Date(java.util.Date.from(datePickerDueDate.getValue()
                            .atStartOfDay(ZoneId.systemDefault()).toInstant())
                            .getTime());
            
            Activity activity = new Activity();
            activity.setTitle(title);
            activity.setEstimatedCompletionHours(estimatedHours);
            activity.setDescription(description);
            activity.setDeliveredBy(practitionerSelected);
            activity.setDueDate(new Timestamp(auxiliarDueDate.getTime()));
            if(displayRecordConfirmation()){
                IActivityDAO activityDAO = new ActivityDAO();
                if(activityDAO.addElement(activity)){
                    displayRecordSuccessDialog();
                    cleanFields();
                } else {
                    displaySomethingWentWrong();
                }
            }
        } else {
            displayEmptyFields();
        }
    }

    @FXML
    private void returnActivitySection() {
        Stage window = (Stage) tableViewActivity.getScene().getWindow();
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/View_ActivitySection.fxml"));
            viewFile = loader.load();
            ControllerActivitySection controllerActivitySection = loader.getController();
            window.setScene(new Scene(viewFile));
        } catch (IOException exception) {
            Logger.getLogger(ControllerAddActivity.class.getName()).log(Level.SEVERE,exception.getMessage(), exception);
        }
    }

    @FXML
    private void showAllActivities() {
        linkColumns();
        IActivityDAO iActivity = new ActivityDAO();
        List<Activity> listActivity = iActivity.getProfessorActivities(professorUsername);
        if(listActivity == null){
            displaySomethingWentWrong();
        } else {
            ObservableList<Activity> activityObservableList = FXCollections.observableArrayList(listActivity);
            tableViewActivity.setItems(activityObservableList);
        }
    }
    
    private void linkColumns(){
        columnID.setCellValueFactory(new PropertyValueFactory<> ("activityID"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<> ("title"));
        columnDueDate.setCellValueFactory(new PropertyValueFactory<> ("dueDate"));
        columnEstimatedHours.setCellValueFactory(new PropertyValueFactory<> ("estimatedCompletionHours"));
        columnPractitioner.setCellValueFactory(new PropertyValueFactory<> ("deliveredBy"));
    }
    
    public void enterCorrectInformation(){
        textFieldTitle.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\sa-zA-Z*")){
                    textFieldTitle.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
                }
            }
        });
        textFieldEstimatedHours.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")){
                    textFieldTitle.setText(newValue.replaceAll("[^0-9]", ""));
                }
            }
        });
    }
    
    public final void initializePractitionerComboBox(List<Practitioner> availablePractitioners) {
        ObservableList<Practitioner> availablePractitionersOL = FXCollections.observableArrayList(availablePractitioners);
        comboBoxPractitioner.getItems().setAll(availablePractitionersOL);
    }
    
    private boolean areTextFieldsEmpty() {
        return (textFieldTitle.getText().isEmpty() || textFieldEstimatedHours.getText().isEmpty() ||
                textAreaDescription.getText().isEmpty() || datePickerDueDate.getValue() == null ||
                comboBoxPractitioner.getValue() == null);
    }
    
    private void cleanFields(){
        textFieldTitle.setText("");
        textFieldEstimatedHours.setText("");
        textAreaDescription.setText("");
        datePickerDueDate.setValue(null);
        comboBoxPractitioner.setValue(null);
    }
    
    public void setProfessorUsername(String username){
        professorUsername = username;
    }
}
