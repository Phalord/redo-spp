package com.spp.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControllerAddProject implements Initializable {

    @FXML
    private TextField textFieldTitle;
    @FXML
    private TextArea textAreaDescription;
    @FXML
    private ComboBox<?> comboBoxCompany;
    @FXML
    private TextArea textAreaResources;
    @FXML
    private TableView<?> tableViewProject;
    @FXML
    private TableColumn<?, ?> columnID;
    @FXML
    private TableColumn<?, ?> columnTitle;
    @FXML
    private TableColumn<?, ?> columnCompany;
    @FXML
    private TableColumn<?, ?> columnResponsible;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void addCompany(ActionEvent event) {
    }

    @FXML
    private void registerProject(ActionEvent event) {
    }

    @FXML
    private void returnProjectSection(ActionEvent event) {
    }

    @FXML
    private void showAllProjects(SortEvent<?> event) {
    }
    
}
