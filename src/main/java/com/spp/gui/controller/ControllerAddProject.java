package com.spp.gui.controller;

import com.spp.model.domain.Project;
import com.spp.model.domain.ProjectResponsible;
import com.spp.model.domain.RelatedCompany;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControllerAddProject implements Initializable {

    @FXML private TextField textFieldTitle;
    @FXML private TextArea textAreaDescription;
    @FXML private TextArea textAreaResources;
    @FXML private ComboBox<RelatedCompany> comboBoxCompany;
    @FXML private TableView<Project> tableViewProject;
    @FXML private TableColumn<Project, Integer> columnID;
    @FXML private TableColumn<Project, String> columnTitle;
    @FXML private TableColumn<Project, RelatedCompany> columnCompany;
    @FXML private TableColumn<Project, ProjectResponsible> columnResponsible;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void addCompany() {
    }

    @FXML
    private void registerProject() {
    }

    @FXML
    private void returnProjectSection() {
    }

    @FXML
    private void showAllProjects(SortEvent<Project> event) {
    }
}
