package com.spp.gui.controller;

import static com.spp.gui.Dialog.displayNotYetSupportedDialog;
import static com.spp.gui.Dialog.displaySomethingWentWrong;
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

public class ControllerProfessorSection {
    @FXML private Menu topMenu;
    @FXML private BorderPane borderPane;
    
    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }
    
    @FXML
    private void back() {
        backScene();
    }

    @FXML
    private void logOut() {
        closeWindow();
        displayLogin();
    }
    
    @FXML
    private void registerProfessor() {
        
    }
    
    @FXML
    private void deteleProfessor() {
        
    }
    
    @FXML
    private void goHistorySection() {
        displayNotYetSupportedDialog();
    }

    private void backScene() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_AdministratorHome.fxml"));
        Parent viewFile;
        try {
            viewFile = loader.load();
            ControllerAdministratorHome controllerAdministratorHome = loader.getController();
            controllerAdministratorHome.setTopMenuText(topMenu.getText());
            window.setScene(new Scene(viewFile, 600, 400));
        } catch (IOException ioException) {
            Logger.getLogger(ControllerProfessorSection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
        }
    }

    private void closeWindow() {
        Stage stage1 = (Stage) borderPane.getScene().getWindow();
        stage1.close();
    }

    private void displayLogin() {
        try {
            new ControllerLogin().display();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerProfessorSection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
        }
    }
}
