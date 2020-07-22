package com.spp.gui.controller;

import static com.spp.gui.Dialog.displayCancelConfirmation;
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

public class ControllerGenerateMonthlyReport {
    
    @FXML private Menu topMenu;
    @FXML private BorderPane monthlyReportBorderPane;
    
    public final void setTopMenuText(String username) {
        topMenu.setText(username);
    }
    
    public void initialize() {
        
    }
    
    @FXML
    private void cancel() {
        if (displayCancelConfirmation()) {
            setBackScene();        
        }
    }
    
    @FXML
    private void logOut() {
        closeWindow();
        displayLogin();
    }
    
    private void closeWindow() {
        Stage stage1 = (Stage) monthlyReportBorderPane.getScene().getWindow();
        stage1.close();
    }

    private void displayLogin() {
        try {
            new ControllerLogin().display();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerPractitionerHome.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
        }
    }

    private void setBackScene() {
        Parent viewFile;
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_GenerateDocumentation.fxml"));
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerGenerateMonthlyReport.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerGenerateDocumentation controllerGenerateDocumentation = loader.getController();
        controllerGenerateDocumentation.setTopMenuText(topMenu.getText());
        Stage window = (Stage) monthlyReportBorderPane.getScene().getWindow();
        window.setScene(new Scene(viewFile, 600, 400));
    }    
    
}
