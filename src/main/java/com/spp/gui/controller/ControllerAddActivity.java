package com.spp.gui.controller;


import com.spp.model.domain.Activity;
import com.spp.model.domain.Practitioner;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControllerAddActivity implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void generateActivity() {
    }

    @FXML
    private void returnActivitySection() {
    }

    @FXML
    private void showAllActivities(SortEvent<Activity> event) {
    }
}
