package com.spp.gui.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @FXML
    private void registerCompany() {
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
    
    private boolean areFieldsEmpty(){
        return (textFieldName.getText().isEmpty() || textFieldAddress.getText().isEmpty() || textFieldState.getText().isEmpty() ||
                textFieldCity.getText().isEmpty() || textFieldSector.getText().isEmpty() || textFieldPhone.getText().isEmpty() ||
                textFieldEmail.getText().isEmpty() || textFieldPhoneResponsible.getText().isEmpty() || 
                textFieldEmailResponsible.getText().isEmpty() || textFieldNameResponsible.getText().isEmpty() ||
                textFieldSurnameResponsible.getText().isEmpty());
    }
}
