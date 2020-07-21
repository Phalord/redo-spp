package com.spp.gui.controller;

import static com.spp.gui.Dialog.displayConnectionError;
import static com.spp.gui.Dialog.displayNotYetSupportedDialog;
import static com.spp.gui.Dialog.displaySomethingWentWrong;
import static com.spp.gui.controller.ControllerPractitionerSection.displayNoAvailableGroups;
import com.spp.model.dataaccess.dao.GroupDAO;
import com.spp.model.dataaccess.idao.IGroupDAO;
import com.spp.model.domain.Group;
import java.io.IOException;
import java.util.List;
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
        IGroupDAO iGroupDAO = new GroupDAO();
        List<Group> availableGroups = iGroupDAO.getAvailableGroups();
        if (availableGroups == null) {
            displayConnectionError();
        } else if (availableGroups.isEmpty()) {
            displayNoAvailableGroups();
        } else {
            displayAddProfessorScene(availableGroups);
        }
    }
    
    private void displayAddProfessorScene(List<Group> availableGroups) {
        Stage window = (Stage) borderPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_AddProfessor.fxml"));
        Parent viewFile;
        try {
            viewFile = loader.load();
            ControllerAddProfessor controllerAddProfessor = loader.getController();
            controllerAddProfessor.initialize(availableGroups);
            controllerAddProfessor.setTopMenuText(topMenu.getText());
            window.setScene(new Scene(viewFile));
        } catch (IOException ioException) {
            Logger.getLogger(ControllerProfessorSection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            displaySomethingWentWrong();
        }
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
