package com.spp.gui.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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

    @FXML
    private TextField textFieldTitle;
    @FXML
    private TextField textFieldEstimatedHours;
    @FXML
    private TextArea textAreaDescription;
    @FXML
    private DatePicker datePickerDueDate;
    @FXML
    private TableView<?> tableViewActivity;
    @FXML
    private TableColumn<?, ?> columnID;
    @FXML
    private TableColumn<?, ?> columnTitle;
    @FXML
    private TableColumn<?, ?> columnDueDate;
    @FXML
    private TableColumn<?, ?> columnEstimatedHours;
    @FXML
    private TableColumn<?, ?> columnPracticing;
    @FXML
    private ComboBox<?> comboBoxPractitioner;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void generateActivity(ActionEvent event) {
    }

    @FXML
    private void returnActivitySection(ActionEvent event) {
    }

    @FXML
    private void showAllActivities(SortEvent<?> event) {
    }
}
