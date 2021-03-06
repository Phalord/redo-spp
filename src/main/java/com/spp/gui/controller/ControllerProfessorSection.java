package com.spp.gui.controller;

import static com.spp.gui.Dialog.displayConnectionError;
import static com.spp.gui.Dialog.displayNoAvailableGroups;
import static com.spp.gui.Dialog.displayNotYetSupportedDialog;
import static com.spp.gui.Dialog.displaySomethingWentWrong;
import static com.spp.utils.MailSender.notifyDevelopers;
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
        List<Group> availableGroups = iGroupDAO.getProfessorAvailableGroups();
        if (availableGroups == null) {
            displayConnectionError();
        } else if (availableGroups.isEmpty()) {
            displayNoAvailableGroups("No se encontraron grupos con capacidad para otro Profesor.");
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
        } catch (IOException ioException) {
            Logger.getLogger(ControllerProfessorSection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            notifyDevelopers(ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerAddProfessor controllerAddProfessor = loader.getController();
        controllerAddProfessor.initialize(availableGroups);
        controllerAddProfessor.setTopMenuText(topMenu.getText());
        window.setScene(new Scene(viewFile));
    }
    
    @FXML
    private void deleteProfessor() {
        Stage window = (Stage) borderPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/views/View_DeleteProfessor.fxml"));
        Parent viewFile;
        try {
            viewFile = loader.load();
        } catch (IOException ioException) {
            Logger.getLogger(ControllerProfessorSection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            notifyDevelopers(ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerDeleteProfessor controllerDeleteProfessor = loader.getController();
        controllerDeleteProfessor.setTopMenuText(topMenu.getText());
        window.setScene(new Scene(viewFile));
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
        } catch (IOException ioException) {
            Logger.getLogger(ControllerProfessorSection.class.getName())
                    .log(Level.SEVERE, ioException.getMessage(), ioException);
            notifyDevelopers(ioException);
            displaySomethingWentWrong();
            return;
        }
        ControllerAdministratorHome controllerAdministratorHome = loader.getController();
        controllerAdministratorHome.setTopMenuText(topMenu.getText());
        window.setScene(new Scene(viewFile, 600, 400));
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
            Logger.getLogger(ControllerProfessorSection.class.getName())
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
