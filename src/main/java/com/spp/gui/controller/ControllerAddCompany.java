package com.spp.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class ControllerAddCompany implements Initializable {

    @FXML private TextField textFieldName;
    @FXML private TextField textFieldAddress;
    @FXML private TextField textFieldState;
    @FXML private TextField textFieldCity;
    @FXML private TextField textFieldSector;
    @FXML private TextField textFieldPhone;
    @FXML private TextField textFieldEmail;
    @FXML private TextField textFieldSurnameResponisble;
    @FXML private TextField textFieldPhoneResponsible;
    @FXML private TextField textFieldEmailResponsible;
    @FXML private TextField textFieldNameResponsible;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void registerCompany() {
    }

    @FXML
    private void returnAddProject() {
    }
}
