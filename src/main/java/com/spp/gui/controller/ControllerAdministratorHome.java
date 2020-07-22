package com.spp.gui.controller;

import static com.spp.gui.Dialog.displayNotYetSupportedDialog;
import static com.spp.gui.Dialog.displaySomethingWentWrong;
import static com.spp.utils.MailSender.notifyDevelopers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ControllerAdministratorHome {

    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;
    
    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }
    
    @FXML
    private void goProfessorSection() {
        setProfessorSection();
    }
    
    @FXML
    private void goCoordinatorSection() {
        displayNotYetSupportedDialog();
    }
    
    @FXML
    private void logOut() {
        closeWindow();
        displayLogin();
    }
    
    private void setProfessorSection() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        Parent viewFile;
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/views/View_ProfessorSection.fxml"));
            viewFile = loader.load();
            ControllerProfessorSection controllerProfessorSection = loader.getController();
            controllerProfessorSection.setTopMenuText(topMenu.getText());
            window.setScene(new Scene(viewFile, 600, 400));
        } catch (IOException ioException) {
            Logger.getLogger(ControllerAdministratorHome.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
        }
    }

    private void closeWindow() {
        Stage stage1 = (Stage) borderPane.getScene().getWindow();
        stage1.close();
    }

    private void displayLogin() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/View_Login.fxml"));
        Parent viewFile;
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerAdministratorHome.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            notifyDevelopers(ioException);
            return;
        }
        ControllerLogin controllerLogin = loader.getController();
        controllerLogin.display();
        window.setScene(new Scene(viewFile, 300, 600));
        window.setResizable(false);
        window.show();
    } 
}
