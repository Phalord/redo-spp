package com.spp.gui.controller;

import static com.spp.gui.Dialog.displayEmptyFields;
import static com.spp.gui.Dialog.displayRecordConfirmation;
import static com.spp.gui.Dialog.displayRecordSuccessDialog;
import static com.spp.gui.Dialog.displaySomethingWentWrong;
import com.spp.model.dataaccess.dao.ProjectResponsibleDAO;
import com.spp.model.dataaccess.dao.RelatedCompanyDAO;
import com.spp.model.dataaccess.idao.IProjectResponsibleDAO;
import com.spp.model.dataaccess.idao.IRelatedCompanyDAO;
import com.spp.model.domain.ProjectResponsible;
import com.spp.model.domain.RelatedCompany;
import static com.spp.utils.TextValidator.validateEmail;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerAddCompany {

    @FXML private TextField textFieldName;
    @FXML private TextField textFieldAddress;
    @FXML private TextField textFieldState;
    @FXML private TextField textFieldCity;
    @FXML private TextField textFieldSector;
    @FXML private TextField textFieldPhone;
    @FXML private TextField textFieldEmail;
    @FXML private TextField textFieldPhoneResponsible;
    @FXML private TextField textFieldEmailResponsible;
    @FXML private TextField textFieldNameResponsible;
    @FXML private TextField textFieldSurnameResponsible; 
    
    public final void initialize() {
        enterCorrectInformation();
    }  

    @FXML
    private void registerCompany() {
        if(!areFieldsEmpty()){
            String companyName = textFieldName.getText();
            String companyAddress = textFieldAddress.getText();
            String companyState = textFieldState.getText();
            String companyCity = textFieldCity.getText();
            String companySector = textFieldSector.getText();
            String companyEmail = textFieldEmail.getText();
            String companyPhone = textFieldPhone.getText();
            String responsibleName = textFieldNameResponsible.getText();
            String responsibleSurname = textFieldSurnameResponsible.getText();
            String responsibleEmail = textFieldEmailResponsible.getText();
            String responsiblePhone = textFieldPhoneResponsible.getText();
            if(validateEmail(companyEmail) && validateEmail(responsibleEmail)){
                RelatedCompany company = new RelatedCompany();
                company.setName(companyName);
                company.setAddress(companyAddress);
                company.setState(companyState);
                company.setCity(companyCity);
                company.setSector(companySector);
                company.setPhone(companyPhone);
                company.setEmail(companyEmail);
                ProjectResponsible responsible = new ProjectResponsible();
                responsible.setName(responsibleName);
                responsible.setSurname(responsibleSurname);
                responsible.setEmail(responsibleEmail);
                responsible.setPhone(responsiblePhone);
                if(displayRecordConfirmation()){
                    IRelatedCompanyDAO companyDAO = new RelatedCompanyDAO();
                    if(companyDAO.addElement(company)){
                        int companyID = companyDAO.getLastRelatedID();
                        responsible.setRelatedCompanyID(companyID);
                        IProjectResponsibleDAO responsibleDAO = new ProjectResponsibleDAO();
                        if(responsibleDAO.addElement(responsible)){
                            displayRecordSuccessDialog();
                            cleanFields();
                        } else {
                            displaySomethingWentWrong();
                        }
                    }
                } else {
                    displaySomethingWentWrong();
                }
            } else {
                displayInvalidField();
            }
        } else {
            displayEmptyFields();
        }
    }

    @FXML
    private void returnAddProject() {
        Stage window = (Stage) textFieldName.getScene().getWindow();
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/View_AddProject.fxml"));
            viewFile = loader.load();
            ControllerAddProject controllerAddProject = loader.getController();
            window.setScene(new Scene(viewFile));
        } catch (IOException exception){
            Logger.getLogger(ControllerAddCompany.class.getName()).log(Level.SEVERE,exception.getMessage(), exception);
        }
    }
    
    public final void enterCorrectInformation(){
        textFieldName.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\sa-zA-Z*")){
                    textFieldName.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
                }
            }
        });
        textFieldState.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\sa-zA-Z*")){
                    textFieldState.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
                }
            }
        });
        textFieldCity.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\sa-zA-Z*")){
                    textFieldCity.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
                }
            }
        });
        textFieldSector.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\sa-zA-Z*")){
                    textFieldSector.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
                }
            }
        });
        textFieldPhone.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")){
                    textFieldPhone.setText(newValue.replaceAll("[^0-9]", ""));
                }
            }
        });
        textFieldNameResponsible.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\sa-zA-Z*")){
                    textFieldNameResponsible.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
                }
            }
        });
        textFieldSurnameResponsible.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\sa-zA-Z*")){
                    textFieldSurnameResponsible.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
                }
            }
        });
        textFieldPhoneResponsible.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")){
                    textFieldPhoneResponsible.setText(newValue.replaceAll("[^0-9]", ""));
                }
            }
        });
    }
    
    private boolean areFieldsEmpty(){
        return (textFieldName.getText().isEmpty() || textFieldAddress.getText().isEmpty() || textFieldState.getText().isEmpty() ||
                textFieldCity.getText().isEmpty() || textFieldSector.getText().isEmpty() || textFieldPhone.getText().isEmpty() ||
                textFieldEmail.getText().isEmpty() || textFieldPhoneResponsible.getText().isEmpty() || 
                textFieldEmailResponsible.getText().isEmpty() || textFieldNameResponsible.getText().isEmpty() ||
                textFieldSurnameResponsible.getText().isEmpty());
    }
    
    private void cleanFields(){
        textFieldName.setText("");
        textFieldAddress.setText("");
        textFieldState.setText("");
        textFieldCity.setText("");
        textFieldSector.setText("");
        textFieldPhone.setText("");
        textFieldEmail.setText("");
        textFieldPhoneResponsible.setText("");
        textFieldEmailResponsible.setText("");
        textFieldNameResponsible.setText("");
        textFieldSurnameResponsible.setText(""); 
    }
    
    public static void displayInvalidField() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Información inválida");
        alert.setContentText("Algunos campos contienen información inválida. Corrijala e intente de nuevo");
        alert.showAndWait(); 
    }
}
