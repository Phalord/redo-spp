package com.spp.gui.controller;

import static com.spp.gui.Dialog.displayEmptyFields;
import static com.spp.gui.Dialog.displayRecordConfirmation;
import static com.spp.gui.Dialog.displayRecordSuccessDialog;
import static com.spp.gui.Dialog.displaySomethingWentWrong;
import com.spp.model.dataaccess.dao.ProjectDAO;
import com.spp.model.dataaccess.idao.IProjectDAO;
import com.spp.model.domain.Project;
import com.spp.model.domain.RelatedCompany;
import java.io.IOException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControllerAddProject {

    @FXML private TextField textFieldTitle;
    @FXML private TextArea textAreaDescription;
    @FXML private TextArea textAreaResources;
    @FXML private ComboBox<RelatedCompany> comboBoxCompany;
    @FXML private TableView<Project> tableViewProject;
    @FXML private TableColumn<Project, Integer> columnID;
    @FXML private TableColumn<Project, String> columnTitle;
    @FXML private TableColumn<Project, RelatedCompany> columnCompany;
    
    public final void initialize() {
        linkColumns();
        enterCorrectInformation();
    }    

    @FXML
    private void addCompany() {
        Stage window = (Stage) tableViewProject.getScene().getWindow();
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/View_AddCompany.fxml"));
            viewFile = loader.load();
            ControllerAddCompany controllerAddCompany = loader.getController();
            window.setScene(new Scene(viewFile));
        } catch (IOException exception){
            Logger.getLogger(ControllerAddProject.class.getName()).log(Level.SEVERE,exception.getMessage(), exception);
        }
    }

    @FXML
    private void registerProject() {
        if(!areFieldsEmpty()){
            String title = textFieldTitle.getText();
            String description = textAreaDescription.getText();
            String resources = textAreaResources.getText();
            RelatedCompany company = comboBoxCompany.getValue();
            
            Project project = new Project();
            project.setTitle(title);
            project.setDescription(description);
            project.setResources(resources);
            project.setRequestedBy(company);
            project.setStatus("Available");
            if(displayRecordConfirmation()){
                IProjectDAO projectDAO = new ProjectDAO();
                if(projectDAO.addElement(project)){
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
    private void returnProjectSection() {
        Stage window = (Stage) tableViewProject.getScene().getWindow();
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/View_ProjectSection.fxml"));
            viewFile = loader.load();
            ControllerProjectSection controllerProjectSection = loader.getController();
            window.setScene(new Scene(viewFile));
        } catch (IOException exception){
            Logger.getLogger(ControllerAddProject.class.getName()).log(Level.SEVERE,exception.getMessage(), exception);
        }
    }

    @FXML
    private void showAllProjects() {
        linkColumns();
        IProjectDAO iProject = new ProjectDAO();
        List<Project> listProject = iProject.getAvailableProjects();
        if(listProject == null){
            displaySomethingWentWrong();
        } else {
            ObservableList<Project> projectObservableList = FXCollections.observableArrayList(listProject);
            tableViewProject.setItems(projectObservableList);
        }
    }
    
    private boolean areFieldsEmpty(){
        return (textFieldTitle.getText().isEmpty() || textAreaDescription.getText().isEmpty() || 
                textAreaResources.getText().isEmpty() || comboBoxCompany.getValue() == null);
    }
    
    private void linkColumns(){
        columnID.setCellValueFactory(new PropertyValueFactory<> ("projectID"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<> ("title"));
        columnCompany.setCellValueFactory(new PropertyValueFactory<> ("requestedBy"));
    }
    private void cleanFields(){
        textFieldTitle.setText("");
        textAreaResources.setText("");
        textAreaDescription.setText("");
        comboBoxCompany.setValue(null);
    }
    
    public final void initializeRelatedCompanyComboBox(List<RelatedCompany> companiesRegistered){
        ObservableList<RelatedCompany> companiesRegisteredOL = FXCollections.observableArrayList(companiesRegistered);
        comboBoxCompany.getItems().setAll(companiesRegisteredOL);
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
    }
}
